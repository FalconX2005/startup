package uz.pdp.startup.payload.withoutId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import uz.pdp.startup.enums.RoleEnum;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto implements Serializable {

    @NotBlank(message = "userName bush bulishi mumkun emas!")
    private String username;

    @NotBlank(message = "password bush bulishi mumkun emas!")
    @Size(min = 4,message = "password kamida 4 ta belgi bulishi kerak")
    private String password;

    private RoleEnum role;
}