package lk.ijse.oniessoftware.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class AttendenceDTO {

    private String date;
    private String a_code;
    private String emp_id;
    private int     extra_hour;
    private double  dailySalary;


}
