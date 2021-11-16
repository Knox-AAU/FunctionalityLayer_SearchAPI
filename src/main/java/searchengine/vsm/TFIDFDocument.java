package searchengine.vsm;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * Data class.
 * Stores TF IDF title and filepath for a document
 */
public class TFIDFDocument extends Document {

  /* <Title, <Term, Amount>> */
  @Getter @Setter
  private HashMap<String, Integer> TFmap;
  /* <Title, <Term, Score>> */
  @Getter @Setter
  private HashMap<String, Double> TFIDFmap;

  /** Constructor
   * @param title String containing the document title.
   * @param id ID for the file.
   */
  public TFIDFDocument(String title, int id) {
    super(title, id);
    TFIDFmap = new HashMap<>();
    TFmap = new HashMap<>();
  }


}