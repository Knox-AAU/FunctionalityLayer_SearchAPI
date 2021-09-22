package searchengine.vsm;

/**
 * Superclass for Document types
 */
public class Document {

    private String title;
    private String filepath;

    /** Constructor
     * @param title: String containing the document title.
     * @param filepath: Filepath to the storage location.
     */
    public Document(String title, String filepath){
        this.title=title;
        this.filepath=filepath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
