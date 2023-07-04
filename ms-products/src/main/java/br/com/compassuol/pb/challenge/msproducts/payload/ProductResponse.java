package br.com.compassuol.pb.challenge.msproducts.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private List<ProductDto> content;
    private int PageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
