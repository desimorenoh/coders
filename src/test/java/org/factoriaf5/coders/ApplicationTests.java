package org.factoriaf5.coders;

import org.factoriaf5.coders.repositories.Coder;
import org.factoriaf5.coders.repositories.CoderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
    @WithMockUser
    void loadsTheHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Autowired
    CoderRepository coderRepository;

    @Test
    @WithMockUser
    void returnsTheExistingCoders() throws Exception {

        Coder coder = coderRepository.save(new Coder("Desirée", "Moreno", 42, "Venezuela", "Técnico Superior", "Calle Lletres 24, 1-2 Barcelona", "Femtech P2", "https://pbs.twimg.com/profile_images/1337757611698032641/DBFJ-khl_400x400.jpg"));

        mockMvc.perform(get("/coders"))
                .andExpect(status().isOk())
                .andExpect(view().name("coders/all"))
                .andExpect(model().attribute("coders", hasItem(coder)));
    }

    @Test
    @WithMockUser
    void returnsAFormToAddANewCoder() throws Exception {
        mockMvc.perform(get("/coders/new"))
                .andExpect(view().name("coders/edit"))
                .andExpect(model().attributeExists("coder"))
                .andExpect(model().attribute("title", "Crear nueva Coder"));
    }
    @Test
    @WithMockUser
    void allowsToCreateANewCoder() throws Exception {
        mockMvc.perform(post("/coders/new")
                        .with(csrf())
                        .param("nombre", "Desirée")
                        .param("apellidos", "Moreno")
                        .param("edad", String.valueOf(42))
                        .param("paisDeOrigen", "Venezuela")
                        .param("nivelDeEstudios", "Técnico Superior")
                        .param("direccion", "Calle Lletres 24, 1-2 Barcelona")
                        .param("promocion", "Femtech P2")
                        .param("photoImage", "https://pbs.twimg.com/profile_images/1337757611698032641/DBFJ-khl_400x400.jpg")
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
                hasProperty("direccion", equalTo("Calle Lletres 24, 1-2 Barcelona")),
                hasProperty("promocion", equalTo("Femtech P2")),
                hasProperty("photoImage", equalTo("https://pbs.twimg.com/profile_images/1337757611698032641/DBFJ-khl_400x400.jpg"))
        )));
    }
    @Test
    @WithMockUser
    void returnsAFormToEditCoders() throws Exception {
        Coder coder = coderRepository.save(new Coder("Desirée", "Moreno Hernández", 42, "Venezuela", "Técnico Superior", "Calle Lletres 24, 1-2 Barcelona", "Femtech P2", "https://pbs.twimg.com/profile_images/1337757611698032641/DBFJ-khl_400x400.jpg"));
        mockMvc.perform(get("/coders/edit/" + coder.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("coders/edit"))
                .andExpect(model().attribute("coder", coder))
                .andExpect(model().attribute("title", "Editar Coder"));
    }
    @Test
    @WithMockUser
    void allowsToDeleteACoder() throws Exception {
        Coder coder = coderRepository.save(new Coder("Desirée", "Moreno Hernández", 42, "Venezuela", "Técnico Superior", "Calle Lletres 24, 1-2 Barcelona", "Femtech P2", "https://pbs.twimg.com/profile_images/1337757611698032641/DBFJ-khl_400x400.jpg"));
        mockMvc.perform(get("/coders/delete/" + coder.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/coders"));

        assertThat(coderRepository.findById(coder.getId()), equalTo(Optional.empty()));
    }
    @Test
    @WithMockUser
    void allowsToSearchCodersbyName() throws Exception {

        Coder coderWithWord = coderRepository.save(new Coder("Marianna", "Moreno", 42, "Venezuela", "Técnico Superior", "Calle Lletres 24, 1-2 Barcelona", "Femtech P2", "https://pbs.twimg.com/profile_images/1337757611698032641/DBFJ-khl_400x400.jpg"));
        Coder coderWithoutWord = coderRepository.save(new Coder("Ana", "Casas", 35, "Venezuela", "Modelo", "Calle Catalunya 22, 2-D,Barcelona", "Femtech P1", "/img/coder5.png"));

        mockMvc.perform(get("/coders/search?word=Marianna"))
                .andExpect(status().isOk())
                .andExpect(view().name("coders/front"))
                .andExpect(model().attribute("nombre", equalTo("Coders containing \"Marianna\"")))
                .andExpect(model().attribute("coders", hasItem(coderWithWord)))
                .andExpect(model().attribute("coders", not(hasItem(coderWithoutWord))));
    }
}