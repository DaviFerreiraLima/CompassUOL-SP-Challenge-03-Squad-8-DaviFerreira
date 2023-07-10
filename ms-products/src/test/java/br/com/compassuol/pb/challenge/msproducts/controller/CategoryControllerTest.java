package br.com.compassuol.pb.challenge.msproducts.controller;

import br.com.compassuol.pb.challenge.msproducts.entity.Category;
import br.com.compassuol.pb.challenge.msproducts.payload.CategoryDto;
import br.com.compassuol.pb.challenge.msproducts.service.CategoryService;
import br.com.compassuol.pb.challenge.msproducts.utils.ControllerUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {CategoryController.class})
class CategoryControllerTest {

    public static final String BASE_URL = "/categories";
    public static final String ID_URL = "/categories/1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;
    @Test
    void createCategory() throws Exception {
        var category = new Category(1L,"Video Game");
        var categoryDto = new CategoryDto(1L,"Video Game");

        when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(categoryDto);

        String requestBody = ControllerUtils.mapToString(category);
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
    void getAllCategories() throws Exception {
        List<CategoryDto> categoriesDtos = new ArrayList<>();
        categoriesDtos.add(new CategoryDto(1L,"Video Game"));

        when(categoryService.findAllCategories()).thenReturn(categoriesDtos);

        var result =
                mockMvc.perform(get(BASE_URL)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        var response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}