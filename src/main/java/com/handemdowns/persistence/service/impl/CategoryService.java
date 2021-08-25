package com.handemdowns.persistence.service.impl;

import com.handemdowns.persistence.dao.CategoryRepository;
import com.handemdowns.persistence.model.Category;
import com.handemdowns.persistence.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService implements ICategoryService {
	private CategoryRepository repository;

	@Autowired
	public CategoryService(CategoryRepository repository) {
		this.repository = repository;
	}

	//@Cacheable(CacheConfig.CATEGORY_CACHE)
	@Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
	public Category findByCode(String code) {
		return repository.findByCode(code);
	}
}