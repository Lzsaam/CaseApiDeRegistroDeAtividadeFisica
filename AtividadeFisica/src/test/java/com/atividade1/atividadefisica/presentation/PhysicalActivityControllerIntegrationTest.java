package com.atividade1.atividadefisica.presentation;

import com.atividade1.atividadefisica.infrastructure.persistence.entity.PhysicalActivityEntity;
import com.atividade1.atividadefisica.infrastructure.persistence.repository.PhysicalActivityJpaRepository;
import com.atividade1.atividadefisica.presentation.dto.PhysicalActivityRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PhysicalActivityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PhysicalActivityJpaRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void shouldRegisterActivity() throws Exception {
        PhysicalActivityRequest request = buildRequest("123456", "RUN", "Corrida de 5km");
        String payload = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/atividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.funcional", is("123456")))
                .andExpect(jsonPath("$.codigoAtividade", is("RUN")));
    }

    @Test
    void shouldListAllActivities() throws Exception {
        repository.save(buildEntity("123456", "RUN"));
        repository.save(buildEntity("654321", "SWIM"));

        mockMvc.perform(get("/atividades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldListActivitiesByFunctional() throws Exception {
        repository.save(buildEntity("123456", "RUN"));
        repository.save(buildEntity("123456", "WALK"));
        repository.save(buildEntity("654321", "SWIM"));

        mockMvc.perform(get("/atividades/funcional/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].funcional", is("123456")));
    }

    @Test
    void shouldFindActivityById() throws Exception {
        PhysicalActivityEntity saved = repository.save(buildEntity("123456", "RUN"));

        mockMvc.perform(get("/atividades/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(saved.getId().intValue())));
    }

    @Test
    void shouldUpdateActivity() throws Exception {
        PhysicalActivityEntity saved = repository.save(buildEntity("123456", "RUN"));

        PhysicalActivityRequest request = buildRequest("654321", "WALK", "Caminhada leve");
        String payload = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/atividades/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.funcional", is("654321")))
                .andExpect(jsonPath("$.codigoAtividade", is("WALK")));

        PhysicalActivityEntity updated = repository.findById(saved.getId()).orElseThrow();
        assertThat(updated.getFunctional()).isEqualTo("654321");
        assertThat(updated.getActivityCode()).isEqualTo("WALK");
    }

    @Test
    void shouldDeleteActivity() throws Exception {
        PhysicalActivityEntity saved = repository.save(buildEntity("123456", "RUN"));

        mockMvc.perform(delete("/atividades/" + saved.getId()))
                .andExpect(status().isNoContent());

        assertThat(repository.existsById(saved.getId())).isFalse();
    }

    @Test
    void shouldReturnNotFoundWhenActivityDoesNotExist() throws Exception {
        mockMvc.perform(get("/atividades/999"))
                .andExpect(status().isNotFound());
    }

    private PhysicalActivityEntity buildEntity(String functional, String code) {
        PhysicalActivityEntity entity = new PhysicalActivityEntity();
        entity.setFunctional(functional);
        entity.setDateTime(LocalDateTime.now());
        entity.setActivityCode(code);
        entity.setActivityDescription("Descricao");
        return entity;
    }

    private PhysicalActivityRequest buildRequest(String functional, String code, String description) {
        PhysicalActivityRequest request = new PhysicalActivityRequest();
        request.setFuncional(functional);
        request.setDataHora(LocalDateTime.of(2025, 9, 24, 7, 30));
        request.setCodigoAtividade(code);
        request.setDescricaoAtividade(description);
        return request;
    }

}
