package uz.pdp.startup.payload.withoutId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.startup.enums.Gender;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDto implements Serializable {

    @NotBlank(message = "firstName bo'sh bo'lishi mumkin emas")
    private String firstName;

    @NotBlank(message = "lastName bo'sh bo'lishi mumkin emas")
    private String lastName;

    @NotBlank(message = "phoneNumber bo'sh bo'lishi mumkin emas")
    @Pattern(regexp = "\\+998\\d{9}", message = "tel nomer formati noto'g'ri Masalan: +998901234567")
    private String phoneNumber;

    @NotNull(message = "gender bo'sh bo'lishi mumkin emas")
    private Gender gender;

    private UserDto user;

    @NotNull(message = "attachmentId bo'sh bo'lishi mumkin emas")
    private Long attachmentId;

    @NotNull(message = "companyId bo'sh bo'lishi mumkin emas")
    private Long companyId;


}