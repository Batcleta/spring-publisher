package com.zigpublisher.ZigPublisher.controller;

import com.zigpublisher.ZigPublisher.model.dto.BookCreationDTO;
import com.zigpublisher.ZigPublisher.model.dto.BookDTO;
import com.zigpublisher.ZigPublisher.model.dto.MessageDTO;
import com.zigpublisher.ZigPublisher.model.dto.PublisherDTO;
import com.zigpublisher.ZigPublisher.service.BookService;
import com.zigpublisher.ZigPublisher.service.PublisherService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
@Slf4j
public class BookController {


    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ResponseEntity<?> listBooks() {
        try {
            return ResponseEntity.ok(bookService.getAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") Long id) {
        if (id == null) return ResponseEntity.badRequest().body(new MessageDTO("Id não pode ser nulo"));

        try {
            return ResponseEntity.ok(bookService.getById(id));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<?> getByCategory(@PathVariable("idCategoria") Long categoryId) {
        try {
            return ResponseEntity.ok(bookService.getByCategoryId(categoryId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @GetMapping("/editora/{idEditora}")
    public ResponseEntity<?> getByPublisher(@PathVariable("idEditora") Long publisherId) {
        try {
            return ResponseEntity.ok(bookService.getByPublisherId(publisherId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @GetMapping("/search/{searchParam}")
    public ResponseEntity<?> findByNameOrIssn(@RequestParam("searchParam") String search) {
        try {
            return ResponseEntity.ok(bookService.findByNameOrIssn(search));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createBook(@RequestBody @Valid BookCreationDTO bookDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(bookDTO));
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDTO("Já Existe um livro com este nome"));
            } else {
                return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable("id") Long id, @RequestBody @Valid BookDTO bookDTO) {
        if (id == null) return ResponseEntity.badRequest().body(new MessageDTO("Id não pode ser nulo"));

        try {
            return ResponseEntity.ok(bookService.update(bookDTO, id));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePBook(@PathVariable("id") Long id) {
        if (id == null) return ResponseEntity.badRequest().body(new MessageDTO("Id não pode ser nulo"));

        try {
            bookService.delete(id);
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
