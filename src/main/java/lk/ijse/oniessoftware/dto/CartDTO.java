package lk.ijse.oniessoftware.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class CartDTO {
    private String item_id;
    private Double unit_price;
    private Integer qty;


}
