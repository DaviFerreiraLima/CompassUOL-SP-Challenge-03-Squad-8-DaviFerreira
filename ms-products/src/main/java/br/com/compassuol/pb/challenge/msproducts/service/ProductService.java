package br.com.compassuol.pb.challenge.msproducts.service;


import br.com.compassuol.pb.challenge.msproducts.entity.Category;
import br.com.compassuol.pb.challenge.msproducts.entity.Product;
import br.com.compassuol.pb.challenge.msproducts.exception.ProductAPIException;
import br.com.compassuol.pb.challenge.msproducts.exception.ResourceNotFoundException;
import br.com.compassuol.pb.challenge.msproducts.payload.ProductDto;
import br.com.compassuol.pb.challenge.msproducts.payload.ProductResponse;
import br.com.compassuol.pb.challenge.msproducts.repository.CategoryRepository;
import br.com.compassuol.pb.challenge.msproducts.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ModelMapper mapper;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,ModelMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }
    public ProductDto createProduct(ProductDto productDto) {
        var categories = new ArrayList<>();

        for (Category category : productDto.getCategories().stream().toList()) {
            categories.add(
                    categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", category.getId()))
            );
        }
        var newProduct = mapper.map(productDto, Product.class);
        newProduct.setCategories(categories.stream().map(c -> (Category) c).collect(Collectors.toList()));

        newProduct = productRepository.save(newProduct);
        return mapper.map(newProduct, ProductDto.class);
    }

    public ProductDto getProductById(long productId){
        var product = productRepository.findById(productId);
        if (product==null){
            throw  new ProductAPIException(HttpStatus.BAD_REQUEST,"The product does not exist");
        }
        return mapper.map(product,ProductDto.class);
    }

    public ProductResponse getAllProducts(int pageNo,int pageSize,String orderBy, String direction){
        var pageable = PageRequest.of(pageNo,pageSize,
                Sort.by(Sort.Direction.fromString(direction.toLowerCase()),
                        orderBy));
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductDto> content = productPage
                .stream().map(product -> mapper.map(product,ProductDto.class))
                .collect(Collectors.toList());
        return new ProductResponse(content,productPage.getNumber(),productPage.getSize(),
                productPage.getTotalElements(),productPage.getTotalPages(),productPage.isLast());
    }

    public void deleteProductById(long productId){
        productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product","id",productId));
        productRepository.deleteById(productId);
    }

    public ProductDto updateProduct(long productId,ProductDto productDto) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        var categories = new ArrayList<>();

        for (Category category : productDto.getCategories().stream().toList()) {
            categories.add(
                    categoryRepository.findById(category.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", category.getId()))
            );
        }
        product = mapper.map(productDto, Product.class);
        product.setCategories(categories.stream().map(c -> (Category) c).collect(Collectors.toList()));

        var newProduct = productRepository.save(product);
        return mapper.map(newProduct, ProductDto.class);
    }
}

