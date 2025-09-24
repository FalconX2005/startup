package uz.pdp.startup.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import uz.pdp.startup.entity.tempAbs.AbsLongEntity;
import uz.pdp.startup.enums.Priority;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@SQLDelete(sql = "UPDATE debts SET deleted = true WHERE id = ?")
public class Debts extends AbsLongEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Client  client;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Company company;

    @Column(nullable = false)
    private Long debtAmount;

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

}
