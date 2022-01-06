package org.factoriaf5.coders;

import org.factoriaf5.coders.repositories.Coder;
import org.factoriaf5.coders.repositories.CoderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Properties;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc

class ApplicationTests {
    @BeforeEach
    void setUp() {
        coderRepository.deleteAll();
    }
    @Autowired
    MockMvc mockMvc;

    @Test
    void loadsTheHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Autowired
    CoderRepository coderRepository;

    @Test
    void returnsTheExistingCoders() throws Exception {

        Coder coder = coderRepository.save(new Coder("Desirée", "Moreno Hernández", 42, "Venezuela", "Técnico Superior", "Calle Lletres 24, 1-2 Barcelona", "Femtech P2"));

        mockMvc.perform(get("/coders"))
                .andExpect(status().isOk())
                .andExpect(view().name("coders/all"))
                .andExpect(model().attribute("coders", hasItem(coder)));
    }

}
