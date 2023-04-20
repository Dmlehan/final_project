package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.db.DBConnection;
import lk.ijse.oniessoftware.dto.CartDTO;
import lk.ijse.oniessoftware.dto.EmployeeDTO;
import lk.ijse.oniessoftware.dto.ItemDTO;
import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemsModel {
    public static ResultSet getAll() throws SQLException {
        String query = "SELECT * FROM items";
        ResultSet rs = CrudUtil.execute(query);
        return rs;
    }

    public static boolean save(String itemCode,Integer store,String type,Double unitPrice,Double profit) throws SQLException {
        String sql = "INSERT INTO items(item_Code,store,description,unit_price,profit) VALUES(?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,itemCode,store,type,unitPrice,profit);
    }

    public static boolean update(ItemDTO item) throws SQLException {
        String sql = "UPDATE items SET store = ?,description=?,unit_price=?,profit=? WHERE item_Code = ?";
        return CrudUtil.execute(sql,  item.getStore(),item.getDescription(),item.getUnit_price(),item.getProfit(),item.getItem_Code());
    }

    public static boolean delete(String itemCode) throws SQLException {
        String sql = "DELETE FROM items WHERE item_Code = ?";
        return CrudUtil.execute(sql, itemCode);
    }

    public static ItemDTO search(String itemCode) throws SQLException {

        String sql = "SELECT * FROM items WHERE  item_Code= ?";
        ResultSet resultSet = CrudUtil.execute(sql, itemCode);

        if (resultSet.next()) {
            String item_code = resultSet.getString(1);
            Integer store = resultSet.getInt(2);
            String type = resultSet.getString(3);
            Double unit_price = resultSet.getDouble(4);
            Double profit = resultSet.getDouble(5);

            return new ItemDTO(item_code,store,type,unit_price,profit);
        }
        return null;
    }




    public static List<String> getIds() throws SQLException {
        String sql = "SELECT item_Code FROM items";

        ResultSet resultSet = CrudUtil.execute(sql);
        List<String> ids = new ArrayList<>();

        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static ItemDTO serchById(String itemID) throws SQLException {

        String sql = "SELECT * FROM items WHERE item_Code = ? ";

        ResultSet resultSet = CrudUtil.execute(sql, itemID);

        if (resultSet.next()) {
            return new ItemDTO(resultSet.getString(1), resultSet.getInt(2),
                    resultSet.getString(3), resultSet.getDouble(4),resultSet.getDouble(5));
        }
        return null;
    }

    public static boolean updateQty(List<CartDTO> cartDTOList) throws SQLException {
        for (CartDTO dto : cartDTOList) {
            if(!updateQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(CartDTO dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE items SET store = (store - ?) WHERE item_Code = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, dto.getQty());
        pstm.setString(2, dto.getItem_id());

        return pstm.executeUpdate() > 0;
    }
}
