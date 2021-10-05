package searchengine.restcontrollers;

import org.json.JSONException;
import org.springframework.web.bind.annotation.*;
import searchengine.SqlConnection;
import java.io.IOException;
import java.util.List;
import java.sql.SQLException;

@RestController
public class SearchController {

    /**
     * @param input: The input received from the POST request
     * @param sources: The list of sources received from the POST request
     * @return a new instance of search. (Will be converted to JSON with each property as a key by Spring).
     */
    @PostMapping(path = "/search")
    public Search postSearch (@RequestParam(name="input") String input, @RequestParam(name="sources") List<String> sources) throws IOException, JSONException, SQLException, ClassNotFoundException {
       return new Search(input, sources,  new SqlConnection());
    }

}
