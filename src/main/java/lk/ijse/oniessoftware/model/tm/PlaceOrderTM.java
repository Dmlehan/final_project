package lk.ijse.oniessoftware.model.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PlaceOrderTM {
    String item_id;
    String item_name;
    Double unit_price;
    Integer qty;
    Double total_price;
    Button btn;
}
