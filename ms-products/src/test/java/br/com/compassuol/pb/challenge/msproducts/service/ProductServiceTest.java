package br.com.compassuol.pb.challenge.msproducts.service;

import br.com.compassuol.pb.challenge.msproducts.entity.Category;
import br.com.compassuol.pb.challenge.msproducts.entity.Product;
import br.com.compassuol.pb.challenge.msproducts.exception.ProductAPIException;
import br.com.compassuol.pb.challenge.msproducts.exception.ResourceNotFoundException;
import br.com.compassuol.pb.challenge.msproducts.payload.ProductDto;
import br.com.compassuol.pb.challenge.msproducts.repository.CategoryRepository;
import br.com.compassuol.pb.challenge.msproducts.repository.ProductRepository;
import br.com.compassuol.pb.challenge.msproducts.utils.ProductUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProductSuccess() {
        var category = new Category();
        category.setId(1);

        var newProduct = ProductUtils.createProduct();
        var productDto = ProductUtils.createProductDto();
        var categories = new HashSet<Category>();
        categories.add(category);
        newProduct.setCategories(categories);

        when(productRepository.findByName(productDto.getName())).thenReturn(null);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        var response = productService.createProduct(productDto);

        assertEquals(productDto.getName(), response.getName());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void CreateProductByIdProductAPIException(){
        var productDto = ProductUtils.createProductDto();
        var product = ProductUtils.createProduct();
        when(productRepository.findByName(product.getName())).thenReturn(product);

        assertThrows(ProductAPIException.class,() -> productService.createProduct(productDto));
        verify(productRepository).findByName(anyString());
    }

    @Test
    void getProductByIdSuccess() {
        var productDto = ProductUtils.createProductDto();
        var product = ProductUtils.createProduct();


        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        var response = productService.getProductById(1L);

        verify(productRepository).findById(1L);
    }

    @Test
    void getProductByIdResourceNotFoundException(){

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,() -> productService.getProductById(anyLong()));
        verify(productRepository).findById(anyLong());
    }

    @Test
    void getAllProducts() {
        var pageRequest = ProductUtils.createPageRequest();

        var productList = ProductUtils.createProductList();
        var productPag = new PageImpl<>(productList,pageRequest,productList.size());
        when(productRepository.findAll(pageRequest)).thenReturn(productPag);

        var response = productService.getAllProducts(0,10,"name","asc");

        assertAll("ProductResponse",
                () -> {
                    // Asserting the response
                    assertEquals(productList.size(), response.getContent().size());
                    assertEquals(0, response.getPageNo());
                    assertEquals(10, response.getPageSize());
                    assertEquals(productList.size(), response.getTotalElements());
                    assertEquals(1, response.getTotalPages());
                    assertEquals(true, response.isLast());
                });

        verify(productRepository).findAll(pageRequest);
    }

    @Test
    void deleteProductByIdSuccess() {
        var product = new Product();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(1L);
        productService.deleteProductById(1L);

        verify(productRepository).findById(1L);
        verify(productRepository).deleteById(1L);

    }

    @Test
    void updateProductSuccess() {
        long productId = 1L;
        var newProduct = ProductUtils.createProduct();
        var productDto = ProductUtils.createProductDto();
        var categories = new HashSet<Category>();
        var category = new Category();
        category.setId(1);
        category.setName("Test");
        categories.add(category);
        newProduct.setCategories(categories);

        when(productRepository.findById(productId)).thenReturn(Optional.of(newProduct));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        var response = productService.updateProduct(productId,productDto);

        assertEquals(productDto.getName(),response.getName());

    }

    @Test
    void updateProductResourceNotFoundException() {

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,() -> productService.updateProduct(1L,any(ProductDto.class)));
        verify(productRepository).findById(anyLong());
    }
}