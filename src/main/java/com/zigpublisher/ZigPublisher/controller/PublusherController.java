package com.zigpublisher.ZigPublisher.controller;

import com.zigpublisher.ZigPublisher.model.dto.CategoryDTO;
import com.zigpublisher.ZigPublisher.model.dto.MessageDTO;
import com.zigpublisher.ZigPublisher.model.dto.PublisherDTO;
import com.zigpublisher.ZigPublisher.service.CategoryService;
import com.zigpublisher.ZigPublisher.service.PublisherService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/editoras")
@Slf4j
public class PublusherController {


    @Autowired
    private PublisherService publisherService;

    @GetMapping("")
    public ResponseEntity<?> listPublishers() {
        try {
            return ResponseEntity.ok(publisherService.getAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublisherById(@PathVariable("id") Long id) {
        if (id == null) return ResponseEntity.badRequest().body(new MessageDTO("Id não pode ser nulo"));

        try {
            return ResponseEntity.ok(publisherService.getById(id));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createPublisher(@RequestBody @Valid PublisherDTO publisherDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.create(publisherDTO));
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDTO("Já Existe uma editora com este nome"));
            } else {
                return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePublisher(@PathVariable Long id, @RequestBody @Valid PublisherDTO publisherDTO) {
        if (id == null) return ResponseEntity.badRequest().body(new MessageDTO("Id não pode ser nulo"));

        try {
            return ResponseEntity.ok(publisherService.update(publisherDTO, id));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable("id") Long id) {
        if (id == null) return ResponseEntity.badRequest().body(new MessageDTO("Id não pode ser nulo"));

        try {
            publisherService.delete(id);
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
