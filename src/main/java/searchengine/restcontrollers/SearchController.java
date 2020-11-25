package searchengine.restcontrollers;

import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {

    @PostMapping(path = "/search", consumes = "application/json", produces = "application/json")
    public Search postSearch (@RequestBody String input) {
       return new Search(input);
    }

}
