package searchengine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class VectorSpaceModel {

    /* <Id, <Term, Occurrences> */
    private HashMap<Integer, HashMap<String, Integer>> documentTF;

    /* <Term, Occurrences> */
    private HashMap<String, Integer> queryTF = new HashMap<>();

    /* <Id, <Term, Score>> */
    private HashMap<Integer, HashMap<String, Double>> documentTFIDF = new HashMap<>();

    /* <Term, Score> */
    private HashMap<String, Double> queryTFIDF = new HashMap<>();

    /*
     * @param query: The query
     * @param file: The file with the documents
     */

    public VectorSpaceModel(String query, String file) {
        documentTF = loadDocuments(file);

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
    private HashMap<String, Double> calculateIDF() {
        HashMap<String, Double> idf = new HashMap<>();
        HashMap<String, Integer> df = new HashMap<>();

        // fills the documentTF HashMap for all documents
        for (int id : documentTF.keySet()) {
            for (String term : documentTF.get(id).keySet()) {
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
            idf.put(term, Math.log10(documentTF.size() / (double)df.get(term)));
        }

        return idf;
    }

    /*
     * Calculates the TF-IDF value of all documents and the query
     */

    private void calculateTFIDF() {
        HashMap<String, Double> idf = calculateIDF();
        int maximumFrequency = 0;

        // assign tfidf for all documents
        for (int id : documentTF.keySet()) {
            HashMap<String, Double> innerMap = new HashMap<>();

            for (String term : documentTF.get(id).keySet()) {
                innerMap.put(term, (double) documentTF.get(id).getOrDefault(term, 0) * idf.get(term));
            }

            documentTFIDF.put(id, innerMap);
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
            queryTFIDF.put(term, (double) queryTF.getOrDefault(term, 0) / maximumFrequency * idf.getOrDefault(term, 0.0));
        }
    }

    /*
     * Calculates the cosine similarity score of a document and the query
     *
     * @param doc: the document to be scored
     * @return the cosine similarity scor e
     */
    private double cosineSimilarityScore(HashMap<String, Double> doc) {

        Set<String> uniqueTerms = new HashSet<>();
        double dotProduct = 0;

        uniqueTerms.addAll(doc.keySet());
        uniqueTerms.addAll(queryTFIDF.keySet());

        // The dot product of the document and the vector
        for (String term : uniqueTerms) {
            dotProduct += doc.getOrDefault(term, 0.0)
                    * queryTFIDF.getOrDefault(term, 0.0);
        }

        return dotProduct / (getLength(doc) * getLength(queryTFIDF));
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
    public List<Document> retrieve(int total) {
        List<Document> documents = new ArrayList<>();
        calculateTFIDF();

        // adds all documents to the list of documents
        for (int id : documentTF.keySet()) {
            documents.add(new Document(id, cosineSimilarityScore(documentTFIDF.get(id))));
        }

        return documents;
    }

    /*
     * Load the documents from a file
     *
     * @param file: the file from which the documents will be loaded from
     * @return the term frequency of the documents
     */
    private HashMap<Integer, HashMap<String, Integer>> loadDocuments(String file) {

        HashMap<Integer, HashMap<String, Integer>> termFrequency = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine;

            String[] remove = new String[] { "[", "]", "(", ")", "-", ".", "," };

            while ((currentLine = br.readLine()) != null) {

                currentLine = currentLine.toLowerCase();
                currentLine = currentLine.replace("\t", " ");

                for (String s : remove) {
                    currentLine = currentLine.replace(s, "");
                }

                StringTokenizer st = new StringTokenizer(currentLine, " ");

                int id = Integer.parseInt(st.nextToken());
                String term;

                while (st.hasMoreTokens()) {
                    term = st.nextToken();

                    if (termFrequency.containsKey(id)) {
                        HashMap<String, Integer> temp = termFrequency.get(id);

                        if (temp.containsKey(term)) {
                            temp.put(term, temp.get(term) + 1);
                        }
                        else {
                            temp.put(term, 1);
                        }

                        termFrequency.put(id, temp);
                    }
                    else {
                        final String test = term;
                        HashMap<String, Integer> temp = new HashMap<>() {{ put(test, 1); }};
                        termFrequency.put(id, temp);
                    }
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return termFrequency;
    }
}