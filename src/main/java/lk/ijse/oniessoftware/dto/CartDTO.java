package lk.ijse.oniessoftware.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CartDTO {
    private String item_id;
    private Integer qty;
    private Double unit_price;


}
