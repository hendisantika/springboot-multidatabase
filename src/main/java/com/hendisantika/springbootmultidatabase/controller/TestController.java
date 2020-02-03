package com.hendisantika.springbootmultidatabase.controller;

import com.hendisantika.springbootmultidatabase.domain.mysql.Category;
import com.hendisantika.springbootmultidatabase.domain.sqlserver.Country;
import com.hendisantika.springbootmultidatabase.repository.CategoryRepository;
import com.hendisantika.springbootmultidatabase.repository.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-multidatabase
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 04/02/20
 * Time: 06.52
 */
@RestController
@RequestMapping("api")
@Slf4j
public class TestController {
    private CategoryRepository categoryRepository;
    private CountryRepository countryRepository;

    @Autowired
    TestController(CategoryRepository categoryRepository, CountryRepository countryRepository) {
        this.categoryRepository = categoryRepository;
        this.countryRepository = countryRepository;
    }

    @PostMapping(value = "category")
    public ResponseEntity<Category> createCategory(@RequestBody Category domain) {
        categoryRepository.save(domain);
        return ResponseEntity.ok(domain);
    }

    @PostMapping(value = "country")
    public ResponseEntity<Country> createCountry(@RequestBody Country domain) {
        countryRepository.save(domain);
        return ResponseEntity.ok(domain);
    }

    @GetMapping(value = "all")
    public Map<String, Object> getAll() {
        final Map<String, Object> result = new HashMap<>();
        result.put("country", countryRepository.findAll());
        result.put("category", categoryRepository.findAll());

        return result;
    }
}
