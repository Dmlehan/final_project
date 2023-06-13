package lk.ijse.oniessoftware.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class DeliverDTO {
  private String deliverId;
  private Double price;
  private String vehicle;
  private String date;
  private String employeeId;
  private String orderId;


}
