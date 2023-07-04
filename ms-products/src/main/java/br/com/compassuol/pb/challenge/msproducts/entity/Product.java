package br.com.compassuol.pb.challenge.msproducts.entity;

import jakarta.persistence.*;
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
@Entity
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
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


    @NotEmpty(message = "The product name should not be empty")
    private String price;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name="product_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name ="category_id", referencedColumnName = "id"))
    private List<Category> categories;
}
