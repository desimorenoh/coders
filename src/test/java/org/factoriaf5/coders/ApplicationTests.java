package org.factoriaf5.coders;

import org.factoriaf5.coders.repositories.Coder;
import org.factoriaf5.coders.repositories.CoderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void returnsAFormToAddANewCoder() throws Exception {
        mockMvc.perform(get("/coders/new"))
                .andExpect(view().name("coders/edit"))
                .andExpect(model().attributeExists("coder"))
                .andExpect(model().attribute("title", "Crear nueva Coder"));
    }
    @Test
    void allowsToCreateANewCoder() throws Exception {
        mockMvc.perform(post("/coders/new")
                        .param("nombre", "Desirée")
                        .param("apellidos", "Moreno")
                        .param("edad", String.valueOf(42))
                        .param("paisDeOrigen", "Venezuela")
                        .param("nivelDeEstudios", "Técnico Superior")
                        .param("dirección", "Calle Lletres 24, 1-2 Barcelona")
                        .param("promoción", "Femtech P2")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/coders"))
        ;

        List<Coder> existingCoders = (List<Coder>) coderRepository.findAll();
        assertThat(existingCoders, contains(allOf(
                hasProperty("nombre", equalTo("Desirée")),
                hasProperty("apellidos", equalTo("Moreno")),
                hasProperty("edad", equalTo(42)),
                hasProperty("paisDeOrigen", equalTo("Venezuela")),
                hasProperty("nivelDeEstudios", equalTo("Técnico Superior")),
                hasProperty("dirección", equalTo("Calle Lletres 24, 1-2 Barcelona")),
                hasProperty("promoción", equalTo("Femtech P2"))
        )));
    }
    @Test
    void returnsAFormToEditCoders() throws Exception {
        Coder coder = coderRepository.save(new Coder("Desirée", "Moreno Hernández", 42, "Venezuela", "Técnico Superior", "Calle Lletres 24, 1-2 Barcelona", "Femtech P2"));
        mockMvc.perform(get("/coders/edit/" + coder.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("coders/edit"))
                .andExpect(model().attribute("coder", coder))
                .andExpect(model().attribute("title", "Editar Coder"));
    }
    @Test
    void allowsToDeleteACoder() throws Exception {
        Coder coder = coderRepository.save(new Coder("Desirée", "Moreno Hernández", 42, "Venezuela", "Técnico Superior", "Calle Lletres 24, 1-2 Barcelona", "Femtech P2"));
        mockMvc.perform(get("/coders/delete/" + coder.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/coders"));

        assertThat(coderRepository.findById(coder.getId()), equalTo(Optional.empty()));
    }
}