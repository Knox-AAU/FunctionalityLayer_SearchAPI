package searchengine.restcontrollers;

import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class SearchController {

    @PostMapping(path = "/search")
    public Search postSearch (@RequestParam String input) throws IOException, JSONException {
       return new Search(input);
    }

}
