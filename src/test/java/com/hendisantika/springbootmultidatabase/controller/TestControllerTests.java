package com.hendisantika.springbootmultidatabase.controller;

import com.hendisantika.springbootmultidatabase.repository.mysql.CategoryRepository;
import com.hendisantika.springbootmultidatabase.repository.sqlserver.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-multidatabase
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 26/11/26
 * Time: 16.48
 */
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    void shouldStoreDataInBothDatasources() throws Exception {
        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"category1\",\"description\":\"description1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("category1"));

        mockMvc.perform(post("/api/country")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"indonesia\",\"isoCode\":\"id\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isoCode").value("id"));

        mockMvc.perform(get("/api/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category[0].name").value("category1"))
                .andExpect(jsonPath("$.country[0].name").value("indonesia"));

        assertThat(categoryRepository.count()).isEqualTo(1);
        assertThat(countryRepository.count()).isEqualTo(1);
    }
}
