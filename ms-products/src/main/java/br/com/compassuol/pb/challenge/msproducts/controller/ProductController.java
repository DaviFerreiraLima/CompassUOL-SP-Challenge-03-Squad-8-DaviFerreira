package br.com.compassuol.pb.challenge.msproducts.controller;

import br.com.compassuol.pb.challenge.msproducts.payload.ProductDto;
import br.com.compassuol.pb.challenge.msproducts.payload.ProductResponse;
import br.com.compassuol.pb.challenge.msproducts.service.ProductService;
import br.com.compassuol.pb.challenge.msproducts.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('OPERATOR')")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto){
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable(value = "id") long productId){
        return new ResponseEntity<>(productService.getProductById(productId),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('OPERATOR')")
    @GetMapping
    public ResponseEntity<ProductResponse> getAllPosts(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER ,required = false) int page,
            @RequestParam(value = "linesPerPage", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int linesPerPage,
            @RequestParam(value = "orderBy",  defaultValue = AppConstants.DEFAULT_ORDER_BY,required = false) String orderBy,
            @RequestParam(value = "direction",  defaultValue = AppConstants.DEFAULT_DIRECTION,required = false) String direction
    ){
        return new ResponseEntity<>(productService.getAllProducts( page,linesPerPage,orderBy,direction),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('OPERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable(value = "id") long productId){
        productService.deleteProductById(productId);
        return new ResponseEntity<>("Product deleted Successfully",HttpStatus.OK);
    }

    @PreAuthorize("hasRole('OPERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(value = "id") long productId, @RequestBody @Valid ProductDto productDto){
        return new ResponseEntity<>(productService.updateProduct(productId,productDto),HttpStatus.OK);
    }

}
