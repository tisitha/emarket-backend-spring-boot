package com.tisitha.emarket.repo;

import com.tisitha.emarket.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findByParentIsNull();

    List<Category> findByParent(Long id);

}