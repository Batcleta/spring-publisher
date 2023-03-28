package com.zigpublisher.ZigPublisher.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {

    private Long id;

    @Size(max = 255, message = "Tamanho de name acima do permitido (255 caracteres)")
    @NotBlank(message = "o campo Name nao pode ser nulo")
    private String name;

    private String description;

    private List<BookDTO> books;
}
