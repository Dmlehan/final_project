package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.dto.EmployeeDTO;
import lk.ijse.oniessoftware.dto.OrdersDTO;
import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersModel {

    public static ResultSet getAll() throws SQLException {
        String query = "SELECT * FROM orders";
        ResultSet rs = CrudUtil.execute(query);
        return rs;
    }
    public static boolean save(String orderId, String custId,String date) throws SQLException {
        String sql = "INSERT INTO employee(orders_Id,cust_Id,dates) VALUES(?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,orderId,custId,date);
    }
    public static boolean update(OrdersDTO order) throws SQLException {
        String sql = "UPDATE employee SET name = ?, nic = ?, payment_Hour = ?, tp = ? WHERE emp_Id = ?";
        return CrudUtil.execute(sql, order.getOrderId(),order.getCustId(),order.getDate());
    }
    public static boolean delete(String orderId) throws SQLException {
        String sql = "DELETE FROM orders WHERE orders_Id = ?";
        return CrudUtil.execute(sql, orderId);
    }
}
