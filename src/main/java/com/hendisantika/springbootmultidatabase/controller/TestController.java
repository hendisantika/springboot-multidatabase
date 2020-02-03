package com.hendisantika.springbootmultidatabase.controller;

import com.hendisantika.springbootmultidatabase.repository.CategoryRepository;
import com.hendisantika.springbootmultidatabase.repository.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
