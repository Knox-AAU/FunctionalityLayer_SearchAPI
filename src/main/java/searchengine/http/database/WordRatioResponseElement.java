package searchengine.http.database;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class WordRatioResponseElement {
    int articleId;
    String word;
    int count;
    String title;
    String filePath;
    String totalWords;
    String publisherName;
    float percent;
}
