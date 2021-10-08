package searchengine.vsm;
import lombok.Data;

/**
 * Superclass for Document types
 */
@Data
public abstract class Document {

  private String title;
  private String filepath;

  /** Constructor
   * @param title String containing the document title
   * @param filepath Filepath to the storage location
  */
  public Document(String title, String filepath) {
    this.title = title;
    this.filepath = filepath;
  }
}
