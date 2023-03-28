package com.zigpublisher.ZigPublisher.repository;

import com.zigpublisher.ZigPublisher.model.entity.BookEntity;
import com.zigpublisher.ZigPublisher.model.entity.CategoryEntity;
import com.zigpublisher.ZigPublisher.model.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByCategory(CategoryEntity category);
    List<BookEntity> findByPublisher(PublisherEntity publisher);
}
