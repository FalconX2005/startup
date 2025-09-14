package uz.pdp.startup.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import uz.pdp.startup.entity.tempAbs.AbsLongEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Company extends AbsLongEntity {

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    @Email(message = "email noto'g'ri kiritildi !!! ")
    private String email;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    @Pattern(regexp = "\\+998\\d{9}", message = "Telefon raqam +998 bilan boshlanib, 9 raqamdan iborat bo‘lishi kerak")
    private String phone;
}
