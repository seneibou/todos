package sn.ept.git.seminaire.cicd.resources;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import sn.ept.git.seminaire.cicd.utils.UrlMapping;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class DefaultResourceTest extends BasicResourceTest {

    @Test
    void indexShouldReturnWelcomeMessage() throws Exception {
        mockMvc.perform(get(UrlMapping.Default.INDEX)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(DefaultResource.WELCOME_MSG)));
    }

    @Test
    void healthShouldReturnServiceStatus() throws Exception {
        mockMvc.perform(get(UrlMapping.Default.HEALTH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(DefaultResource.HEALTH_MSG)));
    }


}