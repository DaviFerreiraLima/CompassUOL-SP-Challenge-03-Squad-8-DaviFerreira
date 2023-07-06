package br.com.compassuol.pb.challenge.msproducts.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private long id;
    @NotBlank(message = "Category name should not be blank")
    @Size(min = 2, message = "Category name should have at least 2 characters")
    private String name;

}
