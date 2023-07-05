package br.com.compassuol.pb.challenge.msproducts.payload;

import br.com.compassuol.pb.challenge.msproducts.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private long id;

    @NotNull(message = "The product date should not be empty")
    private OffsetDateTime date;

    @NotEmpty(message = "The product name should not be empty")
    @Size(min = 3, message = "Product description should have at least 3 characters")
    private String description;

    @NotEmpty(message = "The product name should not be empty")
    @Size(min = 3, message = "Product name should have at least 3 characters")
    @Column(unique = true)
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    @NotNull(message = "The product price should not be null")
    @DecimalMin(value = "1")
    private BigDecimal price;

    private Set<Category> categories;
}
