package uz.pdp.startup.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import uz.pdp.startup.entity.tempAbs.AbsLongEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@SQLDelete(sql = "UPDATE client SET deleted = true WHERE id = ?")
public class Client extends AbsLongEntity {

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private User user;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private  String phone;

    @Column(nullable = false)
    private Long balance;

}
