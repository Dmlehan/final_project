package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.dto.ItemDTO;
import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemsModel {
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
            return new ItemDTO(resultSet.getString(1), resultSet.getDouble(2),
                    resultSet.getDouble(3), resultSet.getDouble(4), resultSet.getString(5),
                    resultSet.getString(6));
        }
        return null;
    }
}
