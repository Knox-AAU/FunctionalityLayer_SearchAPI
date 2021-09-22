package searchengine.restcontrollers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/*
* Can be tested if vpn'ed to aau and tunnelled into port 8081 on the knox server node02
* Currently only test data is present on the database.
* This test wont work as soon as actual data is put into the database.
 */
//TODO SHOULD BE CHANGED TO USE A MOCK DATABASE OR DELETED SINCE IT WONT WORK WHEN ACTUAL DATA IS PUSHED TO DB
//COMMENTED OUT SO IT DOESNT FAIL WHEN NOT TUNNELLING IN
//@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Search search;

    @Test
    public void whenSearch_thenReturnJson() throws Exception {
        mvc.perform(post("/search").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("input", "used forms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].title").value("test1"))
                .andExpect(jsonPath("$.result[0].score").value("0.4871511714236122"))
                .andExpect(jsonPath("$.result[0].filepath").value("/test/test1"));
    }

}