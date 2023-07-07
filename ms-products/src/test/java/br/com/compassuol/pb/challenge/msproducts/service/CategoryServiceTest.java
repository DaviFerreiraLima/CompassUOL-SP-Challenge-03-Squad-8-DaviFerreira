package br.com.compassuol.pb.challenge.msproducts.service;

import br.com.compassuol.pb.challenge.msproducts.entity.Category;
import br.com.compassuol.pb.challenge.msproducts.payload.CategoryDto;
import br.com.compassuol.pb.challenge.msproducts.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void createCategorySuccess() {
        var category = new Category(1L,"Video Game");
        var expectedCategory = new CategoryDto(1L,"Video Game");

       when(categoryRepository.save(any(Category.class))).thenReturn(category);


       var response = categoryService.createCategory(expectedCategory);

        assertAll(
                () -> assertEquals(expectedCategory.getId(), response.getId()),
                () -> assertEquals(expectedCategory.getName(), response.getName())
        );
        verify(categoryRepository).save(any(Category.class));
    }


    @Test
    void findAllCategories() {
            var category1 = new Category(1L,"videogame");
            var categories = new ArrayList<Category>();
            categories.add(category1);

        when(categoryRepository.findAll()).thenReturn(categories);

        var categoriesDto = categoryService.findAllCategories();

        verify(categoryRepository).findAll();
        assertEquals(1,categoriesDto.size());
        assertEquals(categories.get(0).getName(),categoriesDto.get(0).getName());
    }
}