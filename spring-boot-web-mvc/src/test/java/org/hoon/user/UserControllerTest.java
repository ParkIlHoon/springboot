package org.hoon.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() {
        try {
            mockMvc.perform(get("/hello"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(("hello")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createUser_JSON() {
        String userJson = "{\"username\" : \"1hoon\", \"password\" : \"123\"}";

        try {
            mockMvc.perform(post("/users/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(userJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username", is(equalTo("1hoon"))))
                    .andExpect(jsonPath("$.password", is(equalTo("123"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createUser_XML() {
        String userJson = "{\"username\" : \"1hoon\", \"password\" : \"123\"}";

        try {
            mockMvc.perform(post("/users/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_ATOM_XML)
                    .content(userJson))
                    .andExpect(status().isOk())
                    .andExpect(xpath("/User/username").string("1hoon"))
                    .andExpect(xpath("/User/password").string("123"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
