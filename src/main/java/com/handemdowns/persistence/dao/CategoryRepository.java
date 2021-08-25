package com.handemdowns.persistence.dao;

import com.handemdowns.persistence.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, String> {
	Category findByCode(String code);
}