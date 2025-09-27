package uz.pdp.startup.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import uz.pdp.startup.entity.tempAbs.AbsLongEntity;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@SQLDelete(sql = "UPDATE transactions SET deleted = true WHERE id = ?")
public class Transactions extends AbsLongEntity {

    private Long id;

    private LocalDate transactionDate;

    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;


    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;


}
