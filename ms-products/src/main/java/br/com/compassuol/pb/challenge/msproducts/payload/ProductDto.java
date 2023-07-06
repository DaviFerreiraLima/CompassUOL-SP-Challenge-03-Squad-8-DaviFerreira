package br.com.compassuol.pb.challenge.msproducts.payload;

import br.com.compassuol.pb.challenge.msproducts.entity.Category;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private long id;

    @NotBlank(message = "The product date should not be empty")
    private OffsetDateTime date;

    @NotBlank(message = "The product name should not be empty")
    @Size(min = 3, message = "Product description should have at least 3 characters")
    private String description;

    @NotBlank(message = "The product name should not be empty")
    @Size(min = 3, message = "Product name should have at least 3 characters")
    @Column(unique = true)
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    @NotBlank(message = "The product price should not be null")
    @DecimalMin(value = "1",message = "The product price cant be less than 1")
    private BigDecimal price;

    private Set<Category> categories;
}
