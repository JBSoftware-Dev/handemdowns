package com.handemdowns.persistence.service;

import com.handemdowns.persistence.model.Category;

import java.util.List;

public interface ICategoryService {
	List<Category> findAll();
    Category findByCode(String code);
}