package com.balako.onlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotNull
    @Size(min = 1, max = 255)
    private String title;
    @NotNull
    @Size(min = 1, max = 255)
    private String author;
    @NotNull
    private String isbn;
    @NotNull
    @Min(value = 0)
    private BigDecimal price;
    private String description;
    private String coverImage;
}
