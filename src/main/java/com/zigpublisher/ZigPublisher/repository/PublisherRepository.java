package com.zigpublisher.ZigPublisher.repository;

import com.zigpublisher.ZigPublisher.model.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherEntity, Long> {
}
