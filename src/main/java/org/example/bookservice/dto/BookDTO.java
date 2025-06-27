package org.example.bookservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$", message = "Title must start with a capital letter")
    private String title;

    @NotBlank(message = "Author is required")
    @Pattern(
            regexp = "^[A-Z][a-z]+\\s[A-Z][a-z]+$",
            message = "Author must contain two words, each starting with a capital letter. Example: Paulo Coelho"
    )
    private String author;

    private int amount;
}
