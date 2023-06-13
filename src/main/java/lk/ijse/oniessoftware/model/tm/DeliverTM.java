package lk.ijse.oniessoftware.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class DeliverTM {
    private String deliverId;
    private Double price;
    private String vehicle;
    private String date;
    private String employeeId;
    private String orderId;


}
