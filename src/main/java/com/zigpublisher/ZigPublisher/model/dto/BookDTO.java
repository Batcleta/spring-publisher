package com.zigpublisher.ZigPublisher.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    @Size(max = 255, message = "Tamanho de name acima do permitido (255 caracteres)")
    @NotBlank(message = "o campo Name nao pode ser nulo")
    private String name;

    private String description;

    @Size(max = 13, message = "Tamanho de name cima do permitido (13 caracteres)")
    @NotBlank(message = "o campo Name nao pode ser nulo")
    private String isbn;

    @NotNull(message = "o campo Name nao pode ser nulo")
    private PublisherDTO publisher;

    @NotNull(message = "o campo Name nao pode ser nulo")
    private CategoryDTO category;

}
