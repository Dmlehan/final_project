package lk.ijse.oniessoftware.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeDTO {
    private String employeeId;
    private String name;
    private String nic;
    private Double payment_hour;
    private String contact;
}
