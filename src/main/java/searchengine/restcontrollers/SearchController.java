package searchengine.restcontrollers;

import org.springframework.web.bind.annotation.*;
import searchengine.ScoredDocument;

import java.util.List;

@RestController
public class SearchController {

    @PostMapping(path = "/search")
    public Search postSearch (@RequestParam String input)  {
       return new Search(input);
    }

}
