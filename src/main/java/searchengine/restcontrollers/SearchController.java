package searchengine.restcontrollers;

import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class SearchController {

    /*
     * @param input: The input received from UI layer
     * @return a new instance of search. Intuitively what is returned is a list of ScoredDocument.
     */
    @PostMapping(path = "/search")
    public Search postSearch (@RequestParam String input, @RequestParam String[] sources) throws IOException, JSONException {
       return new Search(input, sources);
    }

}
