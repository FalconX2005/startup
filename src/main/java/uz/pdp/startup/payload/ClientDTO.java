package uz.pdp.startup.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.startup.enums.RoleEnum;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClientDTO {

    @NotNull
    private  Long id ;
    @NotBlank
    private  String firstName ;

    @NotNull
    private  String lastName ;

    @NotBlank
    @Pattern(regexp = "\\+998\\d{9}", message = "Telefon raqam +998 bilan boshlanib, 9 raqamdan iborat bo‘lishi kerak")
    private String phoneNumber ;

    @NotNull
    private Long balance;

    @NotBlank
    private String username ;

    @NotBlank
    private String password ;

    @NotBlank
    private RoleEnum role ;




}
