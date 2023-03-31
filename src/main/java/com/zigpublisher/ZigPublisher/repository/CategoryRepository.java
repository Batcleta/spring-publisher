package com.zigpublisher.ZigPublisher.repository;

import com.zigpublisher.ZigPublisher.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @Query("SELECT c FROM CategoryEntity c LEFT JOIN FETCH c.books")
    List<CategoryEntity> findAllWithBooks();

    @Query("SELECT c FROM CategoryEntity c LEFT JOIN FETCH c.books WHERE c.id = :categoryId")
    Optional<CategoryEntity> findByIdWithBooks(@Param("categoryId") Long categoryId);
}
