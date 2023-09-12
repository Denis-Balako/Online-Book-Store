package com.balako.onlinebookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateCategoryRequestDto {
    @NotBlank
    @Length(min = 1, max = 255)
    private String name;
    @Length(min = 1, max = 255)
    private String description;
}
