package org.example.elasticsearch.dto;

import lombok.Data;
import org.example.elasticsearch.metadata.PublicationYear;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class BookDto {

  @NotBlank private String title;

  @Positive @PublicationYear private Integer publicationYear;

  @NotBlank private String authorName;

  @NotBlank private String isbn;
}
