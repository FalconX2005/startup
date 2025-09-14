package uz.pdp.startup.payload;

import lombok.Data;
import uz.pdp.startup.payload.withoutId.UserDto;

/**
 * Created by: Umar
 * DateTime: 7/21/2025 5:14 PM
 */

@Data
public class RegisterDTO extends UserDto {

    private String username;

    private String password;

    private String email;
}
