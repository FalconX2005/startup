package uz.pdp.startup.payload.withoutId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDTO {
    private String name;
    private Long value;
    private Double percent;
}
