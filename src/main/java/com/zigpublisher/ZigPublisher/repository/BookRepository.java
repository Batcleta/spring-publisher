package com.zigpublisher.ZigPublisher.repository;

import com.zigpublisher.ZigPublisher.model.entity.BookEntity;
import com.zigpublisher.ZigPublisher.model.entity.CategoryEntity;
import com.zigpublisher.ZigPublisher.model.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByCategory(CategoryEntity category);

    List<BookEntity> findByPublisher(PublisherEntity publisher);

    @Query("SELECT b FROM BookEntity b WHERE b.name LIKE %:search% OR b.isbn LIKE %:search%")
    List<BookEntity> findByNameOrIssn(@Param("search") String search);
}
