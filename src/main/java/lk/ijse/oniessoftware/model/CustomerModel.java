package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.dto.CustomerDTO;
import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {


    public static List<String> getIds() throws SQLException {
        String sql = "SELECT cust_id FROM customer";

        ResultSet resultSet = CrudUtil.execute(sql);
        List<String> ids = new ArrayList<>();

        while (resultSet.next()){
            ids.add(resultSet.getString(1));
        }
        return  ids;
    }

    public static CustomerDTO serchById(String custId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE cust_Id = ?";

        ResultSet resultSet = CrudUtil.execute(sql,custId);

        if (resultSet.next()){
            return new CustomerDTO(resultSet.getString(1),resultSet.getString(2),
                    resultSet.getString(3),resultSet.getString(4));
        }

        return null;
    }





}
