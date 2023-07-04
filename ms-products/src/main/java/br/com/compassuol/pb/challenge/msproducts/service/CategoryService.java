package br.com.compassuol.pb.challenge.msproducts.service;

import br.com.compassuol.pb.challenge.msproducts.entity.Category;
import br.com.compassuol.pb.challenge.msproducts.payload.CategoryDto;
import br.com.compassuol.pb.challenge.msproducts.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public CategoryService(CategoryRepository categoryRepository,ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public CategoryDto createCategory(CategoryDto categoryDto){
        var category = mapper.map(categoryDto, Category.class);
        var newCategory = categoryRepository.save(category);
        return mapper.map(newCategory,CategoryDto.class);
    }

    public List<CategoryDto> findAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(category -> mapper.map(category,CategoryDto.class))
                .collect(Collectors.toList());
    }
}
