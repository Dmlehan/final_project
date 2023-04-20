package lk.ijse.oniessoftware.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ItemDTO {
    private String item_Code;
    private Integer store;
    private String description;
    private Double unit_price;
    private Double profit;


}
