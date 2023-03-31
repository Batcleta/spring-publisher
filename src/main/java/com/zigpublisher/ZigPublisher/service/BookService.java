package com.zigpublisher.ZigPublisher.service;

import com.zigpublisher.ZigPublisher.model.dto.BookCreationDTO;
import com.zigpublisher.ZigPublisher.model.dto.BookDTO;
import com.zigpublisher.ZigPublisher.model.dto.BookUpdateDTO;
import com.zigpublisher.ZigPublisher.model.dto.CategoryDTO;
import com.zigpublisher.ZigPublisher.model.entity.BookEntity;
import com.zigpublisher.ZigPublisher.model.entity.CategoryEntity;
import com.zigpublisher.ZigPublisher.model.entity.PublisherEntity;
import com.zigpublisher.ZigPublisher.model.mapper.BookMapper;
import com.zigpublisher.ZigPublisher.repository.BookRepository;
import com.zigpublisher.ZigPublisher.repository.CategoryRepository;
import com.zigpublisher.ZigPublisher.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

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

    public List<BookDTO> findByNameOrIssn(String search) {
        List<BookEntity> books = repository.findByNameOrIssn(search);
        return mapper.updateListDTO(books);
    }

    public BookDTO create(BookCreationDTO bookDTO) {
        PublisherEntity publisher = publisherRepository.findById(bookDTO.getPublisher_id())
                .stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        CategoryEntity category = categoryRepository.findById(bookDTO.getCategory_id())
                .stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        BookEntity newBook = new BookEntity();
        newBook.setName(bookDTO.getName());
        newBook.setDescription(bookDTO.getDescription());
        newBook.setIsbn(bookDTO.getIsbn());
        newBook.setPublisher(publisher);
        newBook.setCategory(category);

        newBook = repository.save(newBook);
        return mapper.update(newBook);
    }

    public BookDTO update(BookUpdateDTO bookDTO, Long id) {

        Optional<BookEntity> optionalBook = repository.findById(id);

        if (optionalBook.isPresent()) {
            BookEntity updatedBook = optionalBook.get();
            updatedBook.setId(id);
            updatedBook.setName(bookDTO.getName());
            updatedBook.setDescription(bookDTO.getDescription());
            updatedBook.setIsbn(bookDTO.getIsbn());
            updatedBook = repository.save(updatedBook);

            if (bookDTO.getPublisher_id() != null) {
                PublisherEntity publisher = publisherRepository.findById(bookDTO.getPublisher_id())
                        .stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
                updatedBook.setPublisher(publisher);
            }

            if (bookDTO.getCategory_id() != null) {
                CategoryEntity category = categoryRepository.findById(bookDTO.getCategory_id())
                        .stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
                updatedBook.setCategory(category);
            }

            return mapper.update(updatedBook);
        }

        throw new EntityNotFoundException("Categoria não encontrada");
    }

    public void delete(Long id) {
        BookEntity bookEntity = mapper.update(getById(id));
        repository.delete(bookEntity);
    }

}
