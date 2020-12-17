package searchengine.vsm;

import java.util.*;

public class VectorSpaceModel {

    /* <Term, Frequency> */
    private HashMap<String, Integer> queryTF = new HashMap<>();

    /* <Term, Score> */
    private HashMap<String, Double> queryTFIDF = new HashMap<>();

    /*
     * @param query: The query
     */
    public VectorSpaceModel(String query) {
        createQueryTF(query);
    }

    /*
    *Splits the input from UI into individual terms
     */
    private void createQueryTF(String query){
        // Split the query into terms and places them in an array
        String[] sArray = query.split("\\s+");

        // fills the queryTF HashMap with term in query
        for (String s : sArray) {

            if (queryTF.containsKey(s)) {
                queryTF.put(s, queryTF.get(s) + 1);
            }
            else {
                queryTF.put(s, 1);
            }
        }
    }

    /*
     * Calculates the IDF score for all documents
     *
     * @return A HashMap<String, Double> of idf values
     */
    private HashMap<String, Double> calculateIDF(List<Document> documents) {
        HashMap<String, Double> idf = new HashMap<>();
        HashMap<String, Integer> df = new HashMap<>();

        // fills the documentTF HashMap for all documents
        for (Document document : documents) {
            for (String term : document.getTF().keySet()) {
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
            idf.put(term, Math.log10(documents.size() / (double)df.get(term)));
        }

        return idf;
    }

    /*
     * Calculates the TF-IDF value of all documents and the query
     */

    private void calculateTFIDF(List<Document> documents) {
        HashMap<String, Double> idf = calculateIDF(documents);
        int maximumFrequency = 0;

        // assign tfidf for all documents
        for (Document document : documents) {
            HashMap<String, Double> innerMap = new HashMap<>();

            for (String term : document.getTF().keySet()) {
                innerMap.put(term, (double) document.getTF().getOrDefault(term, 0) * idf.get(term));
            }

            document.setTFIDF(innerMap);
        }

        // Iterates through all the terms in the query to find the highest frequency for a term
        for (String term : queryTF.keySet()) {
            int termFrequency = queryTF.get(term);
            if (termFrequency > maximumFrequency) {
                maximumFrequency = termFrequency;
            }
        }

        // Assign tfidf for the query
        for (String term : queryTF.keySet()) {
            queryTFIDF.put(term, 0.5 + (0.5 * queryTF.getOrDefault(term, 0) / (maximumFrequency * queryTF.getOrDefault(term, 0))) * idf.getOrDefault(term, 0.0));
        }
    }

    /*
     * Calculates the cosine similarity score of a document and the query
     *
     * @param doc: the document to be scored
     * @return the cosine similarity score
     */
    private Double cosineSimilarityScore(Document doc) {

        Set<String> uniqueTerms = new HashSet<>();
        double dotProduct = 0;

        uniqueTerms.addAll(doc.getTF().keySet());
        uniqueTerms.addAll(queryTFIDF.keySet());

        // The dot product of the document and the vector
        for (String term : uniqueTerms) {
            dotProduct += doc.getTF().getOrDefault(term, 0)
                    * queryTFIDF.getOrDefault(term, 0.0);
        }

        return dotProduct / (getLength(doc.getTFIDF()) * getLength(queryTFIDF));
    }

    /*
     * Calculates the length of a document represented as a vector
     *
     * @param tfidf: the tfidf hashmap of a document
     * @return the length of the document
     */
    private double getLength(HashMap<String, Double> tfidf) {

        double length = 0.0;

        for (String term : tfidf.keySet()) {
            length += Math.pow(tfidf.get(term), 2);
        }

        return Math.sqrt(length);
    }

    /*
     * retrieve a list of scored documents
     *
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
}