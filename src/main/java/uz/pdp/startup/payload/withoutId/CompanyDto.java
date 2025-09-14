package uz.pdp.startup.payload.withoutId;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyDto {

    @NotNull(message = "name null bo'lishi mumkin emas")
    private String name;

    @NotBlank(message = "email bush bulishi mumkun emas!")
    @Email(message = "Email format xato!")
    private String email;

    @NotBlank(message = "phoneNumber bo'sh bo'lishi mumkin emas")
    @Pattern(regexp = "\\+998\\d{9}", message = "tel nomer formati noto'g'ri Masalan: +998901234567")
    private String phone;

    @NotNull(message = "manzil null bo'lishi mumkin emas")
    private String address;

}
