package uz.pdp.startup.payload.withoutId;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DebtChartDTO {
    private String name;
    private Long qarz;
}
