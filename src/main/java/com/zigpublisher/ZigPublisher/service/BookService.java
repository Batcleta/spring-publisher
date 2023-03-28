package com.zigpublisher.ZigPublisher.service;

import com.zigpublisher.ZigPublisher.model.dto.BookDTO;
import com.zigpublisher.ZigPublisher.model.dto.CategoryDTO;
import com.zigpublisher.ZigPublisher.model.entity.BookEntity;
import com.zigpublisher.ZigPublisher.model.entity.CategoryEntity;
import com.zigpublisher.ZigPublisher.model.entity.PublisherEntity;
import com.zigpublisher.ZigPublisher.model.mapper.BookMapper;
import com.zigpublisher.ZigPublisher.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookMapper mapper;

    public BookDTO getById(Long id) {
        BookEntity bookEntity = repository.findById(id)
                .stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        return mapper.update(bookEntity);
    }

    public List<BookDTO> getAll() {
        List<BookEntity> books = repository.findAll();
        return mapper.updateListDTO(books);
    }

    public List<BookDTO> getByCategoryId(Long categoryId) {
        CategoryEntity categoria = new CategoryEntity();
        categoria.setId(categoryId);
        List<BookEntity> books = repository.findByCategory(categoria);
        return mapper.updateListDTO(books);
    }

    public List<BookDTO> getByPublisherId(Long publisherId) {
        PublisherEntity publisher = new PublisherEntity();
        publisher.setId(publisherId);
        List<BookEntity> books = repository.findByPublisher(publisher);
        return mapper.updateListDTO(books);
    }


    public BookDTO create(BookDTO bookDTO) {
        BookEntity book = mapper.update(bookDTO);
        book = repository.save(book);
        return mapper.update(book);
    }

    public BookDTO update(BookDTO bookDTO, Long id) {
        if (repository.existsById(id)) {
            BookEntity book = mapper.update(bookDTO);
            book.setId(id);
            book = repository.save(book);
            return mapper.update(book);
        }

        throw new EntityNotFoundException("Categoria não encontrada");
    }

    public void delete(Long id) {
        BookEntity bookEntity = mapper.update(getById(id));
        repository.delete(bookEntity);
    }

}
