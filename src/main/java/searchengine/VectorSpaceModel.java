package searchengine;

import java.util.*;
import java.lang.Math;

public class VectorSpaceModel {

    private Vector query;

    public VectorSpaceModel(Vector query) {
        this.query = query;
    }

    /*
     * Calculates the cosine similarity score between a document and the query
     *
     * @param doc: The document to score
     * @return: The cosine similarity score
     */
    private double cosineSimilarityScore(Document doc) {

        Set<String> uniqueTerms = new HashSet<>();
        double ret = 0.0;

        for (String term : doc.getTfidf().keySet()) {
            uniqueTerms.add(term);
        }

        for (String term : query.getTfidf().keySet()) {
            uniqueTerms.add(term);
        }

        // The dotproduct of the document and the vector
        for (String term : uniqueTerms) {
            ret += doc.getTfidf().getOrDefault(term, 0.0) * query.getTfidf().getOrDefault(term, 0.0);
        }

        double doclen = getLength(doc);
        double qlen = getLength(query);

        ret = ret / (doclen*qlen);

        return ret;
    }

    /*
     * Calculates the idf of a list of documents
     *
     * @param docs: the list of documents
     * @return: idf of every term in the documents
     */
    public HashMap<String, Double> getIdf(List<Document> docs) {
        HashMap<String, Double> idf = new HashMap<>();
        HashMap<String, Integer> df = new HashMap<>();

        //Get the document frequency of each term
        for (Document doc : docs) {
            for (String term : doc.getTermFrequency().keySet()){
                if (df.containsKey(term)) {
                    df.put(term, df.get(term)+1);
                }
                else {
                    df.put(term, 1);
                }
            }
        }

        //Get the inverse document frequency of each term
        for (String term : df.keySet()) {
            idf.put(term, Math.log10(docs.size() / (double)df.get(term)));
        }

        return idf;
    }

    /*
     * Calculates the tfidf of the documents in a list of documents and the query
     *
     * @param doc: The list of documents
     */
    public void calculateTfidf(List<Document> docs) {
        HashMap<String, Double> idf = getIdf(docs);
        HashMap<String, Double> tfidf = new HashMap<>();
        int maximumFrequency = 0;

        //Assign tfidf for each document
        for (Document doc : docs) {
            for (String term : idf.keySet()) {
                tfidf.put(term, (double)doc.getTermFrequency().getOrDefault(term, 0) * idf.get(term));
            }
            doc.setTfidf(tfidf);
            tfidf = new HashMap<>();
        }

        //Assign tfidf for the query
        for (String term : query.getTermFrequency().keySet()) {
            int termFrequency = query.getTermFrequency().get(term);
            if (termFrequency > maximumFrequency) {
                maximumFrequency = termFrequency;
            }
        }

        for (String term : idf.keySet()) {
            tfidf.put(term, (double)query.getTermFrequency().getOrDefault(term, 0) / maximumFrequency * idf.getOrDefault(term, 0.0));
        }
        query.setTfidf(tfidf);
    }

    /*
     * Calculates the length of a document represented as a vector
     *
     * @param doc: The document
     * @return: The length
     */
    private double getLength(Vector vec) {

        double temp = 0.0;
        for (String term : vec.getTfidf().keySet()) {
            temp += Math.pow(vec.getTfidf().get(term), 2);
        }
        temp = Math.sqrt(temp);

        return temp;
    }

    /*
     * Assigns a score to every document in a list
     *
     * @param doc: The list of documents
     */
    public void assignScore (List<Document> docs) {
        for (Document doc : docs) {
            doc.setScore(cosineSimilarityScore(doc));
        }
    }

    /*
     * Calculates the cosine similarity score between a document and the query
     *
     * @param doc: The document to score
     * @return: The cosine similarity score
     */
    public Vector getQuery() {
        return query;
    }

    /*
     * Calculates log base 2 of a value
     *
     * @param doc: The value
     * @return: log base 2 of the value
     */
    private double logBase2(double input) {
        return Math.log10(input) / Math.log10(2);
    }
}
