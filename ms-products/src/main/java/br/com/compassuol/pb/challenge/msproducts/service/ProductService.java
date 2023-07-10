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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        var existingProduct = productRepository.findByName(productDto.getName());
        if (existingProduct != null){
            throw new ProductAPIException(HttpStatus.BAD_REQUEST,"This product name is already registered");
        }

        var categories = getCategoriesByIds(productDto.getCategories());

        var newProduct = mapper.map(productDto, Product.class);
        newProduct.setCategories(categories.stream().map(category -> (Category) category).collect(Collectors.toSet()));

        newProduct = productRepository.save(newProduct);
        return mapper.map(newProduct, ProductDto.class);
    }

    public ProductDto getProductById(long productId){
        var product = productRepository.findById(productId).orElseThrow(
                ()->  new ResourceNotFoundException("Product","id",productId));
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

        var existingProductName = productRepository.findByName(productDto.getName());
        if (existingProductName !=null) {
            throw new ProductAPIException(HttpStatus.BAD_REQUEST,"This Product name is already registered");
        }
        var categories = getCategoriesByIds(productDto.getCategories());

        product = mapper.map(productDto, Product.class);
        product.setId(productId);
        product.setCategories(categories.stream().map(category -> (Category) category).collect(Collectors.toSet()));

        var newProduct = productRepository.save(product);
        return mapper.map(newProduct, ProductDto.class);
    }



    private Set<Category> getCategoriesByIds(Set<Category> categories) {
        Set<Category> result = new HashSet<>();

        for (Category category : categories) {
            Category foundCategory = categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", category.getId()));
            result.add(foundCategory);
        }
        return result;
    }

}

