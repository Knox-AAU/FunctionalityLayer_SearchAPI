package searchengine.http.database;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class WordRatioResponseElement {
    int id;
    String wordName;
    int amount;
    String articleTitle;
    String filePath;
    int totalWordsInArticle;
    int sourceId;
    String sourceName;
    float percent;
    int fileId;
}
