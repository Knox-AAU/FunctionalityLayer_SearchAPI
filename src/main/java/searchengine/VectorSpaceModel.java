package searchengine;

import searchengine.booleanretrieval.DataSelection;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class VectorSpaceModel {

    /* <Term, Occurrences> */
    private HashMap<String, Integer> queryTF = new HashMap<>();

    /* <Term, Score> */
    private HashMap<String, Double> queryTFIDF = new HashMap<>();

    private String query;

    /*
     * @param query: The query
     * @param file: The file with the documents
     */

    public VectorSpaceModel(String query) {
        this.query = query;
        String[] sArray = query.split("\\s+");

        // fills the queryTF HashMap with the stemmed terms from the query
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
            for (String term : document.TF.keySet()) {
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

            for (String term : document.TF.keySet()) {
                innerMap.put(term, (double) document.TF.getOrDefault(term, 0) * idf.get(term));
            }

            document.TFIDF = innerMap;
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
            queryTFIDF.put(term, (double)(0.5 + (0.5 * queryTF.getOrDefault(term, 0) / maximumFrequency) * idf.getOrDefault(term, 0.0)));
        }
    }

    /*
     * Calculates the cosine similarity score of a document and the query
     *
     * @param doc: the document to be scored
     * @return the cosine similarity scor e
     */
    private ScoredDocument cosineSimilarityScore(Document doc) {

        Set<String> uniqueTerms = new HashSet<>();
        double dotProduct = 0;

        uniqueTerms.addAll(doc.TF.keySet());
        uniqueTerms.addAll(queryTFIDF.keySet());

        // The dot product of the document and the vector
        for (String term : uniqueTerms) {
            dotProduct += doc.TF.getOrDefault(term, 0)
                    * queryTFIDF.getOrDefault(term, 0.0);
        }

        double score = dotProduct / (getLength(doc.TFIDF) * getLength(queryTFIDF));

        return new ScoredDocument(doc.getTitle(), score, doc.getFilepath());
    }

    /*
     * Calculates the length of a document represented as a vector
     *
     * @param doc: the document
     * @return the length of the document
     */
    private double getLength(HashMap<String, Double> tfidf) {

        double temp = 0.0;

        for (String term : tfidf.keySet()) {
            temp += Math.pow(tfidf.get(term), 2);
        }
        temp = Math.sqrt(temp);

        return temp;
    }

    /*
     * retrieve a list of scored documents
     *
     * @param total: the amount of documents to be retrieved
     */
    public List<ScoredDocument> retrieve() {
        DataSelection data = new DataSelection();
        List<Document> documents = data.sendQuery(this.query);
        calculateTFIDF(documents);

        List<ScoredDocument> scoredDocuments = new ArrayList<>();
        for (Document doc : documents) {
            scoredDocuments.add(cosineSimilarityScore(doc));
        }

        return scoredDocuments;
    }
}