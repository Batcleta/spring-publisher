package com.zigpublisher.ZigPublisher.model.mapper;

import com.zigpublisher.ZigPublisher.model.dto.BookDTO;
import com.zigpublisher.ZigPublisher.model.entity.BookEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {
    private CategoryMapper categoryMapper = new CategoryMapper();
    private PublisherMapper publisherMapper = new PublisherMapper();

    public BookDTO update(BookEntity book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setCategory(categoryMapper.update(book.getCategory()));
        bookDTO.setPublisher(publisherMapper.update(book.getPublisher()));
        return bookDTO;
    }

    public BookEntity update(BookDTO bookDTO) {
        BookEntity book = new BookEntity();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setDescription(bookDTO.getDescription());
        book.setIsbn(bookDTO.getIsbn());
        book.setCategory(categoryMapper.update(bookDTO.getCategory()));
        book.setPublisher(publisherMapper.update(bookDTO.getPublisher()));
        return book;
    }

    public List<BookEntity> updateListEntity(List<BookDTO> bookDTOList) {
        return bookDTOList.stream()
                .map(bookDTO ->
                        this.update(bookDTO))
                .toList();
    }

    public List<BookDTO> updateListDTO(List<BookEntity> bookEntityList) {
        return bookEntityList.stream()
                .map(bookEntity ->
                        this.update(bookEntity))
                .toList();
    }


}
