package org.hoon.thymeleaf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class ThymeleafTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void sample() throws Exception {
        mockMvc.perform(get("/sample"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("sample"))
                .andExpect(model().attribute("name", is("1hoon")));
    }
}
