package uz.pdp.startup.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDTO {


    private Timestamp timestamp;

    private String error;

    private int status;

}