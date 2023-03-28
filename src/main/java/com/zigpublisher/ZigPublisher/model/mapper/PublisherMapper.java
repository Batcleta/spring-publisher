package com.zigpublisher.ZigPublisher.model.mapper;

import com.zigpublisher.ZigPublisher.model.dto.PublisherDTO;
import com.zigpublisher.ZigPublisher.model.entity.PublisherEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublisherMapper {

    public PublisherDTO update(PublisherEntity publisher) {
        PublisherDTO publisherDTO = new PublisherDTO();

        publisherDTO.setId(publisher.getId());
        publisherDTO.setName(publisher.getName());
        publisherDTO.setDescription(publisher.getDescription());
        return publisherDTO;
    }

    public PublisherEntity update(PublisherDTO publisherDTO) {
        PublisherEntity publisher = new PublisherEntity();
        publisher.setId(publisherDTO.getId());
        publisher.setName(publisherDTO.getName());
        publisher.setDescription(publisherDTO.getDescription());
        return publisher;
    }

    public List<PublisherEntity> updateListEntity(List<PublisherDTO> publisherDTOList) {
        return publisherDTOList.stream()
                .map(publisherDTO ->
                        this.update(publisherDTO))
                .toList();
    }

    public List<PublisherDTO> updateListDTO(List<PublisherEntity> publisherEntityList) {
        return publisherEntityList.stream()
                .map(publisherEntity ->
                        this.update(publisherEntity))
                .toList();
    }
}
