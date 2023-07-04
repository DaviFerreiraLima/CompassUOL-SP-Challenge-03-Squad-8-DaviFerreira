package br.com.compassuol.pb.challenge.msproducts.payload;

import br.com.compassuol.pb.challenge.msproducts.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private long id;

    private OffsetDateTime date;

    @NotEmpty(message = "The product name should not be empty")
    @Size(min = 3, message = "Product description should have at least 3 characters")
    private String description;

    @NotEmpty(message = "The product name should not be empty")
    @Size(min = 3, message = "Product name should have at least 3 characters")
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    private String price;

    private List<Category> categories;
}
