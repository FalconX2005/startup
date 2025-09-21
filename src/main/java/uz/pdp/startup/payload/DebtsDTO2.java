package uz.pdp.startup.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.startup.enums.Priority;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DebtsDTO2 {

    private Long id ;

    private Long companyId;

    private Long clientId ;

    private Long debtAmount ;

    private LocalDate fromDate;

    private LocalDate toDate;

    private Priority priority;

}
