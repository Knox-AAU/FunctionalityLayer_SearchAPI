package searchengine;

import com.github.xjavathehutt.porterstemmer.PorterStemmer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class VectorSpaceModel {

    /* <Id, <Term, Occurrences> */
    private HashMap<Integer, HashMap<String, Integer>> documentTF = new HashMap<>();

    /* <Term, Occurrences> */
    private HashMap<String, Integer> queryTF = new HashMap<>();

    /* <Id, <Term, Score>> */
    private HashMap<Integer, HashMap<String, Double>> documentTFIDF = new HashMap<>();

    /* <Term, Score> */
    private HashMap<String, Double> queryTFIDF = new HashMap<>();

    private HashMap<String, Double> calculateIDF() {
        HashMap<String, Double> idf = new HashMap<>();
        HashMap<String, Integer> df = new HashMap<>();

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

    private void calculateTFIDF() {
        HashMap<String, Double> idf = calculateIDF();
        int maximumFrequency = 0;

        for (int id : documentTF.keySet()) {
            HashMap<String, Double> innerMap = new HashMap<>();

            for (String term : documentTF.get(id).keySet()) {
                innerMap.put(term, (double) documentTF.get(id).getOrDefault(term, 0) * idf.get(term));
            }

            documentTFIDF.put(id, innerMap);
        }

        // Assign tfidf for the query
        for (String term : queryTF.keySet()) {
            int termFrequency = queryTF.get(term);
            if (termFrequency > maximumFrequency) {
                maximumFrequency = termFrequency;
            }
        }

        for (String term : queryTF.keySet()) {
            queryTFIDF.put(term, (double) queryTF.getOrDefault(term, 0) / maximumFrequency * idf.getOrDefault(term, 0.0));
        }
    }

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

    private double getLength(HashMap<String, Double> tfidf) {

        double temp = 0.0;
        for (String term : tfidf.keySet()) {
            temp += Math.pow(tfidf.get(term), 2);
        }
        temp = Math.sqrt(temp);

        return temp;
    }

    public List<Document> retrieve(String query, int total) {
        List<Document> documents = new ArrayList<>();
        documentTF = loadDocuments();

        String[] sArray = query.split("\\s+");

        for (String s : sArray) {

            s = PorterStemmer.stem(s);
            s = s.toLowerCase();

            if (queryTF.containsKey(s)) {
                queryTF.put(s, queryTF.get(s) + 1);
            }
            else {
                queryTF.put(s, 1);
            }
        }

        calculateTFIDF();

        for (int id : documentTF.keySet()) {
            documents.add(new Document(id, cosineSimilarityScore(documentTFIDF.get(id))));
        }

        return documents;
    }

    private HashMap<Integer, HashMap<String, Integer>> loadDocuments() {

        HashMap<Integer, HashMap<String, Integer>> termFrequency = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("documents.txt"));
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
                    term = PorterStemmer.stem(st.nextToken());

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