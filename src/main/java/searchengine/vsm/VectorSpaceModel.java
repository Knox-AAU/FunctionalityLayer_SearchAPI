package searchengine.vsm;

import java.util.*;

/**
 * Contains all methods related to the vector space model.
 */
public class VectorSpaceModel {

    /**
     * Creates a vector space model from the query.
     * @param query: input query
     */
    public VectorSpaceModel(String query) {
        createQueryTF(query);
    }

    /* <Term, Frequency> */ //TODO: Add getter and setter?
    private HashMap<String, Integer> queryTFMap = new HashMap<>();
    /* <Term, Score> */
    private HashMap<String, Double> queryTFIDFMap = new HashMap<>();

    /**
     * Splits the query string into individual terms.
     * @param query: input query
     */
    private void createQueryTF(String query){
        // Split the query into terms and places them into an array. Query is split on all whitespaces.
        String[] termArray = query.split("\\s+");

        //Counts term frequency in the query//
        for (String term : termArray) {

            if (queryTFMap.containsKey(term)) { //Term exists in map, instance is counted up
                queryTFMap.put(term, queryTFMap.get(term) + 1);
            }
            else { // Term doesn't exit in map
                queryTFMap.put(term, 1);
            }
        }
    }

    /**
     * Retrieves a list of scored documents from a list of documents
     * @param documents: The list of documents retrieved from the database
     * @return the list of ScoredDocuments
     */
    public List<ScoredDocument> getScoredDocuments(List<Document> documents) {

        List<ScoredDocument> scoredDocuments = new ArrayList<>();

        calculateTFIDF(documents);

        for (Document doc : documents) {
            Double score = cosineSimilarityScore(doc);
            scoredDocuments.add(new ScoredDocument(doc.getTitle(), score, doc.getFilepath()));
        }

        scoredDocuments.sort(Collections.reverseOrder());
        return scoredDocuments;
    }

    /**
     * Calculates the TF-IDF value of all documents and the query.
     * @param documents: list of input documents.
     */
    private void calculateTFIDF(List<Document> documents) {
        HashMap<String, Double> idf = calculateIDF(documents);
        int maximumFrequency = 0;

        // Assign TF-IDF for all documents
        for (Document document : documents) {
            HashMap<String, Double> innerMap = new HashMap<>();

            for (String term : document.getTF().keySet()) {
                // TFIDF_td = TF_td * IDF_t
                // td: term in document, t: term
                innerMap.put(term, (double) document.getTF().getOrDefault(term, 0) * idf.get(term));
            }
            document.setTFIDF(innerMap);
        }

        // Iterates through all the terms in the query to find the highest frequency for a term
        for (String term : queryTFMap.keySet()) {
            int termFrequency = queryTFMap.get(term);
            if (termFrequency > maximumFrequency) {
                maximumFrequency = termFrequency;
            }
        }

        // Assign TF-IDF for the query
        for (String term : queryTFMap.keySet()) {
            queryTFIDFMap.put(term, 0.5 + (0.5 * queryTFMap.getOrDefault(term, 0) / (maximumFrequency * queryTFMap.getOrDefault(term, 0))) * idf.getOrDefault(term, 0.0));
        }
    }

    /**
     * Calculates the IDF (inverse document frequency) score for all input documents.
     * @param documents: list of documents
     * @return A HashMap<String, Double> of inverse document frequency values.
     */
    private HashMap<String, Double> calculateIDF(List<Document> documents) {
        HashMap<String, Double> idf = new HashMap<>();
        HashMap<String, Integer> df = new HashMap<>();

        // Fills the document term frequency HashMap
        // This means that all terms from all documents are added, along with their frequency across all documents
        for (Document document : documents) {
            for (String term : document.getTF().keySet()) { // Find all terms in document
                if (df.containsKey(term)) {
                    df.put(term, df.get(term)+1);
                }
                else {
                    df.put(term, 1);
                }
            }
        }

        // Get the inverse document frequency of each term
        for (String term : df.keySet()) {
            idf.put(term, Math.log10(documents.size() / (double)df.get(term))); // IDF_t = log(N/DF_t)
        }

        return idf;
    }

    /**
     * Calculates the cosine similarity score of a document and the query.
     * @param doc: the document to be scored
     * @return the cosine similarity score
     */
    private Double cosineSimilarityScore(Document doc) {

        Set<String> uniqueTerms = new HashSet<>();
        double dotProduct = 0;

        uniqueTerms.addAll(doc.getTF().keySet());
        uniqueTerms.addAll(queryTFIDFMap.keySet());

        // The dot product of the document and the vector
        for (String term : uniqueTerms) {
            dotProduct += doc.getTF().getOrDefault(term, 0)
                    * queryTFIDFMap.getOrDefault(term, 0.0);
        }

        return dotProduct / (getLength(doc.getTFIDF()) * getLength(queryTFIDFMap));
    }

    /**
     * Calculates the length of a document represented as a vector
     * @param tfidf: the TF-IDF hashmap of a document
     * @return the length of the document
     */
    private double getLength(HashMap<String, Double> tfidf) {

        double length = 0.0;

        for (String term : tfidf.keySet()) {
            length += Math.pow(tfidf.get(term), 2);
        }

        return Math.sqrt(length);
    }


}