package uz.pdp.startup.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyClientDTO {

    @NotNull
    private  Long id;

    @NotNull
    private Long clientId;

    @NotNull
    private Long companyId;
}
