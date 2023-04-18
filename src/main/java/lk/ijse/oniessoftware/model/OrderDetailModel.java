package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.db.DBConnection;
import lk.ijse.oniessoftware.dto.CartDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
    public static boolean save(String oId, List<CartDTO> cartDTOList) throws SQLException {
        for(CartDTO dto :  cartDTOList) {
            if(!save(oId, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String oId, CartDTO dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO orders_items(orders_Id, item_Code, price , qty) VALUES (?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oId);
        pstm.setString(2, dto.getItem_id());
        pstm.setInt(3, dto.getQty());
        pstm.setDouble(4, dto.getUnit_price());

        return pstm.executeUpdate() > 0;

    }
}
