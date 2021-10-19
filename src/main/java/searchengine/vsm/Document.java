package searchengine.vsm;
import lombok.Data;

/**
 * Superclass for Document types
 */
@Data
public abstract class Document {

  private String title;
  private int id;

  /** Constructor
   * @param title String containing the document title
   * @param id ID of the file
  */
  public Document(String title, int id) {
    this.title = title;
    this.id = id;
  }
}
