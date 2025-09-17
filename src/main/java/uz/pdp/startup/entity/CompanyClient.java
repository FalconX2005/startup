package uz.pdp.startup.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
@SQLDelete(sql = "UPDATE company_client SET deleted = true WHERE id = ?")
public class CompanyClient extends AbsLongEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Company company;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Client client;

}
