package com.zigpublisher.ZigPublisher.model.mapper;

import com.zigpublisher.ZigPublisher.model.dto.CategoryDTO;
import com.zigpublisher.ZigPublisher.model.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {
    public CategoryDTO update(CategoryEntity category) {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public CategoryEntity update(CategoryDTO categoryDTO) {
        CategoryEntity category = new CategoryEntity();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        return category;
    }

    public List<CategoryEntity> updateListEntity(List<CategoryDTO> categoryDTOList) {
        return categoryDTOList.stream()
                .map(categoryDTO ->
                        this.update(categoryDTO))
                .toList();
    }

    public List<CategoryDTO> updateListDTO(List<CategoryEntity> categoryEntityList) {
        return categoryEntityList.stream()
                .map(categoryEntity ->
                        this.update(categoryEntity))
                .toList();
    }

}
