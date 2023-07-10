package br.com.compassuol.pb.challenge.msproducts.controller;

import br.com.compassuol.pb.challenge.msproducts.entity.Category;
import br.com.compassuol.pb.challenge.msproducts.exception.ProductAPIException;
import br.com.compassuol.pb.challenge.msproducts.exception.ResourceNotFoundException;
import br.com.compassuol.pb.challenge.msproducts.payload.CategoryDto;
import br.com.compassuol.pb.challenge.msproducts.payload.ProductDto;
import br.com.compassuol.pb.challenge.msproducts.payload.ProductResponse;
import br.com.compassuol.pb.challenge.msproducts.service.CategoryService;
import br.com.compassuol.pb.challenge.msproducts.service.ProductService;
import br.com.compassuol.pb.challenge.msproducts.utils.ControllerUtils;
import br.com.compassuol.pb.challenge.msproducts.utils.ProductUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.yaml.snakeyaml.events.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {ProductController.class})
class ProductControllerTest {


    public static final String BASE_URL = "/products";
    public static final String ID_URL = "/products/1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void createProductSuccess() throws Exception{
        var productDto = ProductUtil.createProductDto();

        when(productService.createProduct(any(ProductDto.class))).thenReturn(productDto);

        String requestBody = ControllerUtils.mapToString(productDto);
        var result =
                mockMvc.perform(post(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void createProductProductAPIException() throws Exception {

        when(productService.createProduct(any(ProductDto.class))).thenThrow(new ProductAPIException(HttpStatus.BAD_REQUEST,"This product name is already registered"));

        var result =
                mockMvc.perform(post(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void getProductByIdSuccess() throws Exception {
        var productDto = ProductUtil.createProductDto();

        when(productService.getProductById(anyLong())).thenReturn(productDto);

        String requestBody = ControllerUtils.mapToString(productDto);
        var result =
                mockMvc.perform(get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getProductByIdResourceNotFoundException() throws Exception {

        when(productService.getProductById(anyLong())).thenThrow( new ResourceNotFoundException("Product","id",anyLong()));

        var result =
                mockMvc.perform(get(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void getAllPosts() throws Exception {

        var productPag = new ProductResponse();

        when(productService.getAllProducts(0,10,"name","asc")).thenReturn(productPag);

        String requestBody = ControllerUtils.mapToString(productPag);
        var result =
                mockMvc.perform(get(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))

                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteProductByIdSuccess() throws Exception {
        long productId = 1;
     doNothing().when(productService).deleteProductById(productId);

        var result =
                mockMvc.perform(delete(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteProductByIdResourceNotFoundException() throws Exception {

        doThrow(new ResourceNotFoundException("Product", "id", 1L))
                .when(productService).deleteProductById(1L);

        var result = mockMvc.perform(delete(ID_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        var response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void updateProductSuccess() throws Exception {

        var productDto = ProductUtil.createProductDto();

        when(productService.updateProduct(anyLong(),any(ProductDto.class))).thenReturn(productDto);

        String requestBody = ControllerUtils.mapToString(productDto);
        var result =
                mockMvc.perform(put(ID_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test

    void updateProductProductAPIException() throws Exception {
        var productDto = ProductUtil.createProductDto();
        when(productService.updateProduct(1L,productDto)).thenThrow(new ResourceNotFoundException("Product", "id", 1L));

        var result = mockMvc.perform(put(ID_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}