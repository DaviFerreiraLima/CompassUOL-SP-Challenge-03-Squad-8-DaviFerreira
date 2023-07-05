package br.com.compassuol.pb.challenge.msproducts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Entity
@Table(name = "product" , uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @NotNull(message = "The product date should not be empty")
    private OffsetDateTime date;

    @NotEmpty(message = "The product name should not be empty")
    @Size(min = 3, message = "Product description should have at least 3 characters")
    private String description;

    @NotEmpty(message = "The product name should not be empty")
    @Size(min = 3, message = "Product name should have at least 3 characters")
    private String name;

    @Column(name = "img_url")
    private String imgUrl;


    @NotNull(message = "The product price should not be null")
    @DecimalMin(value = "1",message = "The product price cant be less than 1")
    private BigDecimal price;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name="product_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name ="category_id", referencedColumnName = "id"))
    private Set<Category> categories;
}
