package searchengine.restcontrollers;

import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.SqlConnection;
import java.io.IOException;
import java.util.List;
import java.sql.SQLException;

@RestController
public class SearchController {
    /**
     * @param input The input received from the POST request
     * @param sources The list of sources received from the POST request
     * @return a ResponseEntity containing a new instance of search. (Will be converted to JSON with each property as a key by Spring).
     */
  @PostMapping(path = "/search")
  public ResponseEntity postSearch ( @RequestParam(name = "input") String input, @RequestParam(name = "sources", required = false) List<String> sources) throws IOException, JSONException, SQLException, ClassNotFoundException {
    if (input.isBlank()/*TODO INSERT NULL CHECK ON SOURCES*/) {return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)};
    return ResponseEntity.status(HttpStatus.OK).body(new Search(input, sources, new SqlConnection()));
  }
}
