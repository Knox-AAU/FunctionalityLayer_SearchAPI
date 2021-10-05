package searchengine.restcontrollers;

import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class SearchController {

    /*
     * @param input: The input received from UI layer
     * @return a new instance of search. Intuitively what is returned is a list of ScoredDocument.
     */
    @PostMapping(path = "/search")
    public Search postSearch (@RequestParam(name="input") String input, @RequestParam(name="sources") List<String> source) throws IOException, JSONException {
       return new Search(input);
    }

}
