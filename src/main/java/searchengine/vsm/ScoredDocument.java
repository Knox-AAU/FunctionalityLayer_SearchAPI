package searchengine.vsm;

import lombok.Getter;

/**
 * Data class.
 * Stores title filepath and score for a document.
 */
public class ScoredDocument extends Document implements Comparable<ScoredDocument> {
  @Getter
  private final Double score;

  /** Constructor
   * @param title String containing the document title.
   * @param score Cosinus similarity score.
   * @param id ID for the file.
   */
  public ScoredDocument(String title, double score, int id) {
    super(title, id);
    this.score = score;
  }


  @Override
  public int compareTo(ScoredDocument o) {
    return this.getScore().compareTo(o.getScore());
  }
}