package lk.ijse.oniessoftware.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class DeliverTM {
    private String deliverId;
    private String employeeId;
    private String orderId;
    private String vehicle;
    private Double price;
    private String date;
}
