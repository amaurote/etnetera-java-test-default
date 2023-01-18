package com.etnetera.hr;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class used for Spring Boot/MVC based tests.
 *
 * @author Etnetera
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JavaScriptFrameworkTests {

    @Autowired
    private JavaScriptFrameworkRepository repository;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private final JavaScriptFramework TEST_FW_1 = new JavaScriptFramework(1L, "Sample JS Framework", "SNAPSHOT-1.0", null, null);
    private final JavaScriptFramework TEST_FW_2 = new JavaScriptFramework(2L, "Sample JS Framework 2", "1.4", new Date(), 3);

    @Before
    public void setup() {
        repository.saveAll(List.of(TEST_FW_1, TEST_FW_2));
    }

    @Test
    public void getAll_success() throws Exception {
        var mvcResult = mockMvc.perform(get("/framework"))
                .andExpect(status().isOk())
                .andReturn();

        List<JavaScriptFramework> parsedResponse = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<List<JavaScriptFramework>>() {
        });

        assertEquals(2, parsedResponse.size());
        assertTrue(parsedResponse.contains(TEST_FW_1));
        assertTrue(parsedResponse.contains(TEST_FW_2));
    }

    @Test
    public void addNew_success() throws Exception {
        var name = "Test JS FW";
        var version = "1.0";
        var testFw = JavaScriptFramework.builder().name(name).version(version).build();

        var mvcResult = mockMvc.perform(post("/framework")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(testFw)))
                .andExpect(status().isOk())
                .andReturn();

        JavaScriptFramework parsedResponse = mapper.readValue(mvcResult.getResponse().getContentAsByteArray(), JavaScriptFramework.class);

        assertEquals(name, parsedResponse.getName());
        assertEquals(version, parsedResponse.getVersion());
    }

    @Test
    public void update_success() throws Exception {
        TEST_FW_1.setHypeLevel(3);

        mockMvc.perform(put("/framework/" + TEST_FW_1.getId())
                        .content(mapper.writeValueAsString(TEST_FW_1))
                        .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        var updated = repository.findById(TEST_FW_1.getId()).orElse(null);
        assertNotNull(updated);
        assertEquals(3, (int) updated.getHypeLevel());
    }

    @Test
    public void delete_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/framework/" + TEST_FW_1.getId()))
                .andExpect(status().isNoContent())
                .andReturn();

        assertEquals(1, repository.count());
        assertFalse(repository.existsById(1L));
    }

}
