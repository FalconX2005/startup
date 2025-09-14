package uz.pdp.startup.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.startup.enums.RoleEnum;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO implements Serializable {

    private Long id;


    @NotBlank(message = "userName bush bulishi mumkun emas!")
    private String username;

    @NotBlank(message = "password bush bulishi mumkun emas!")
//    @Size(min = 4, message = "password kamida 4 ta belgi bulishi kerak")
    private String password;


    private String email;

    private RoleEnum role;
}