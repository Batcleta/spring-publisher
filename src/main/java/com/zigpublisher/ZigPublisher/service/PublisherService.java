package com.zigpublisher.ZigPublisher.service;

import com.zigpublisher.ZigPublisher.model.dto.PublisherDTO;
import com.zigpublisher.ZigPublisher.model.entity.PublisherEntity;
import com.zigpublisher.ZigPublisher.model.mapper.PublisherMapper;
import com.zigpublisher.ZigPublisher.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    @Autowired
    private PublisherRepository repository;

    @Autowired
    private PublisherMapper mapper;

    public PublisherDTO getById(Long id) {
        PublisherEntity publisherEntity = repository.findById(id)
                .stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Editora não encontrada"));
        return mapper.update(publisherEntity);
    }

    public List<PublisherDTO> getAll() {
        List<PublisherEntity> publisherEntities = repository.findAll();
        return mapper.updateListDTO(publisherEntities);
    }

    public PublisherDTO create(PublisherDTO publisherDTO) {
        PublisherEntity publisher = mapper.update(publisherDTO);
        publisher = repository.save(publisher);
        return mapper.update(publisher);
    }

    public PublisherDTO update(PublisherDTO publisherDTO, Long id) {
        if (repository.existsById(id)) {
            PublisherEntity publisher = mapper.update(publisherDTO);
            publisher.setId(id);
            publisher = repository.save(publisher);
            return mapper.update(publisher);
        }

        throw new EntityNotFoundException("Editora não encontrada");
    }

    public void delete(Long id) {
        PublisherEntity publisher = mapper.update(getById(id));
        repository.delete(publisher);
    }
}
