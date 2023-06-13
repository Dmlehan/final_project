package lk.ijse.oniessoftware.model.tm;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AttendeceTM {
    private String date;
    private String a_code;
    private String emp_id;
    private Integer     extra_hour;
}
