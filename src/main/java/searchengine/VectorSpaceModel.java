package searchengine;

import java.util.Arrays;
import java.util.List;
import java.lang.Math;

public class VectorSpaceModel {

    public VectorSpaceModel() {

    }

    /*
     *
     *
     */
    private double termFrequency(int rawTF) {
        if (rawTF == 0){
            return 0;
        }

        return 1 + Math.log(rawTF);
    }

    /*
     *
     *
     */
    private double inverseDocumentFrequency(String term) {

        return 0;
    }

    /*
     *
     *
     */
    private double[] getVector(String document) {

        return null;
    }

    /*
     * Calculates the cosine similarity score between two given vectors
     *
     * @param a: First vector
     * @param b: Second vector
     * @return: The cosine similarity score
     */
    public double cosineSimilarityScore(double[] a, double[] b) {

        /* The dimension of the vector */
        int n = a.length;

        /* The nominator of the formula for cosine similarity */
        double sum = 0;

        /* The denominator of the formula for cosine similarity */
        double squaredA = 0;
        double squaredB = 0;

        for (int i = 1; i < n; i++) {
            squaredA += Math.pow(a[i], 2);
            squaredB += Math.pow(b[i], 2);

            sum += a[i] * b[i];
        }

        return sum / (Math.sqrt(squaredA) * Math.sqrt(squaredB));
    }

    /*
     *
     *
     */
    public void retrieve(String query) {

    }

}
