package com.zigpublisher.ZigPublisher.service;

import com.zigpublisher.ZigPublisher.model.dto.CategoryDTO;
import com.zigpublisher.ZigPublisher.model.entity.CategoryEntity;
import com.zigpublisher.ZigPublisher.model.mapper.CategoryMapper;
import com.zigpublisher.ZigPublisher.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryMapper mapper;

    public CategoryDTO getById(Long id) {
        CategoryEntity categoryEntity = repository.findById(id)
                .stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        return mapper.update(categoryEntity);
    }

    public List<CategoryDTO> getAll() {
        List<CategoryEntity> categoryEntities = repository.findAll();
        return mapper.updateListDTO(categoryEntities);
    }

    public CategoryDTO create(CategoryDTO categoryDTO) {
        CategoryEntity category = mapper.update(categoryDTO);
        category = repository.save(category);
        return mapper.update(category);
    }

    public CategoryDTO update(CategoryDTO categoryDTO, Long id) {
        if (repository.existsById(id)) {
            CategoryEntity categoryEntity = mapper.update(categoryDTO);
            categoryEntity.setId(id);
            categoryEntity = repository.save(categoryEntity);
            return mapper.update(categoryEntity);
        }

        throw new EntityNotFoundException("Categoria não encontrada");
    }

    public void delete(Long id) {
        CategoryEntity categoryEntity = mapper.update(getById(id));
        repository.delete(categoryEntity);
    }

}
