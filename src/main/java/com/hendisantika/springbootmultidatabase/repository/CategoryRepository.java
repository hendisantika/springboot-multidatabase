package com.hendisantika.springbootmultidatabase.repository;

import com.hendisantika.springbootmultidatabase.domain.mysql.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-multidatabase
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 04/02/20
 * Time: 06.44
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
