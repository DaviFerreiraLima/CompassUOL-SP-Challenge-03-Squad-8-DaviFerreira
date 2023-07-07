package br.com.compassuol.pb.challenge.msproducts.utils;

import br.com.compassuol.pb.challenge.msproducts.entity.Category;
import br.com.compassuol.pb.challenge.msproducts.entity.Product;
import br.com.compassuol.pb.challenge.msproducts.payload.ProductDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class productUtil {

    public  static Product createProduct(){
        var category = new Category();
        category.setId(1);
        category.setName("TEST");
        var categories = new HashSet<Category>();
        categories.add(category);

        return new Product(1L, OffsetDateTime.now(),"This is a product description",
                "ProductName","image.url",new BigDecimal(20),categories);
    }

    public  static ProductDto createProductDto(){
        var category = new Category();
        category.setId(1);
        category.setName("TEST");
        var categoriesDto = new HashSet<Category>();
        categoriesDto.add(category);

        return new ProductDto(1L, OffsetDateTime.now(),"This is a product description",
                "ProductName","image.url",new BigDecimal(20),categoriesDto);
    }

    public  static PageRequest createPageRequest(){
        int pageNo = 0;
        int pageSize = 10;
        String orderBy = "name";
        String direction = "asc";
        return PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(direction.toLowerCase()), orderBy));
    }

    public static List<Product> createProductList(){
        List<Product> productList = new ArrayList<>();
        productList.add(productUtil.createProduct());
        return productList;
    }
}
