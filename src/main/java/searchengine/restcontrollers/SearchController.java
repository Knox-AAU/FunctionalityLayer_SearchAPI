package searchengine.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @GetMapping("/search")
    public Search search(@RequestParam(value = "q", defaultValue = "World") String input) {
        return new Search(input);
    }
}
