package com.merc.tech.churchadminservice.controller;

import com.merc.tech.churchadminservice.service.ChurchService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@WebMvcTest
@AutoConfigureMockMvc
class ChurchControllerIntegrationTest {

    @MockBean
    private ChurchService churchService;

    @Autowired
    ChurchController churchController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenPostRequestToChurchAndValidChurch_thenCorrectResponse() throws Exception {

        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);

        String request = """
                {
                    "name" : "Dallas Spanish Oak Cliff",
                    "phone" : "817-456-7878"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/church")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(textPlainUtf8));

    }

    @Test
    void whenPostRequestToChurchAndInvalidChurch_thenErrorResponse() throws Exception {
        String request = """
                {
                 "name" : "",                  
                    "phone" : "8174567878"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/church")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Name is mandatory")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenPostRequestToChurchAndInvalidPhone_thenErrorResponse() throws Exception {

        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);

        String request = """
                {
                    "name" : "Dallas Spanish Oak Cliff",
                    "phone" : "8173-456-7878"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/church")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", Is.is("Incorrect format for phone")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }


}
