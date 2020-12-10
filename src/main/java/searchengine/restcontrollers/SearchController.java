package searchengine.restcontrollers;

import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {

    @PostMapping(path = "/search")
    public Search postSearch (@RequestParam String input)  {
        System.out.println(input);
       return new Search(input);
    }

}
