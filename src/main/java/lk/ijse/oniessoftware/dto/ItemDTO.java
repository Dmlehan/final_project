package lk.ijse.oniessoftware.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ItemDTO {
    private String item_Code;
    private Double getPrice;
    private Double salePrice;
    private Double profit;
    private String store;
    private String description;


}
