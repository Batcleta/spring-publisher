package com.zigpublisher.ZigPublisher.controller;

import com.zigpublisher.ZigPublisher.model.dto.CategoryDTO;
import com.zigpublisher.ZigPublisher.model.dto.MessageDTO;
import com.zigpublisher.ZigPublisher.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/categorias")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> listCategories() {
        try {
            return ResponseEntity.ok(categoryService.getAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGategoryById(@PathVariable("id") Long id) {
        if (id == null) return ResponseEntity.badRequest().body(new MessageDTO("Id não pode ser nulo"));

        try {
            return ResponseEntity.ok(categoryService.getById(id));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(categoryDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDTO categoryDTO) {
        if (id == null) return ResponseEntity.badRequest().body(new MessageDTO("Id não pode ser nulo"));

        try {
            return ResponseEntity.ok(categoryService.update(categoryDTO, id));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        if (id == null) return ResponseEntity.badRequest().body(new MessageDTO("Id não pode ser nulo"));

        try {
            categoryService.delete(id);
            return ResponseEntity.ok(new MessageDTO("Usuário: " + id + ", deletado com sucesso"));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }

    }


}
