package lk.ijse.oniessoftware.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class OrdersDTO {
    private String orderId;
    private String custId;
    private String date;

}
