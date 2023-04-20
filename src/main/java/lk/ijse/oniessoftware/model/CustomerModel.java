package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.dto.CustomerDTO;
import lk.ijse.oniessoftware.dto.EmployeeDTO;
import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {


    public static ResultSet getAll() throws SQLException {
        String query = "SELECT * FROM customer";
        ResultSet rs = CrudUtil.execute(query);
        return rs;
    }

    public static boolean save(String custId, String cName,String contact,String usersId) throws SQLException {
        String sql = "INSERT INTO customer (cust_Id,name,c_Tp,users_Id) VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(sql,custId,cName,contact,usersId);
    }

    public static boolean update(CustomerDTO customer) throws SQLException {
        String sql = "UPDATE customer SET name = ?, c_Tp = ?,users_Id WHERE cust_Id = ?";
        return CrudUtil.execute(sql,customer.getName(),customer.getC_Tp(),customer.getUser_Id());
    }

    public static boolean delete(String custId) throws SQLException {
        String sql = "DELETE FROM customer WHERE cust_Id = ?";
        return CrudUtil.execute(sql, custId);
    }

    public static CustomerDTO search(String custId) throws SQLException {

        String sql = "SELECT * FROM customer WHERE cust_Id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, custId);

        if (resultSet.next()) {
            String customerId = resultSet.getString(1);
            String custName = resultSet.getString(2);
            String contact = resultSet.getString(3);
            String userId = resultSet.getString(4);

            return new CustomerDTO(customerId,custName,contact,userId);
        }
        return null;
    }


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
