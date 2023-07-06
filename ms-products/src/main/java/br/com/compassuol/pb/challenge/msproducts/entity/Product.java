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

    private OffsetDateTime date;

    private String description;

    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    private BigDecimal price;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name="product_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name ="category_id", referencedColumnName = "id"))
    private Set<Category> categories;
}
