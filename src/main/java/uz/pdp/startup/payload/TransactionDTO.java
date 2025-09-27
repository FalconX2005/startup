package uz.pdp.startup.payload;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.startup.entity.Company;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionDTO {

    private Long id;

    private LocalDate transactionDate;

    private Long amount;

    private Long companyId;

    private Long clientId;


}
