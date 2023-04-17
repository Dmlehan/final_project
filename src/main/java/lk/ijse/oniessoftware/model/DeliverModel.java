package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.dto.DeliverDTO;
import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliverModel {
        public static ResultSet getAll() throws SQLException {
            String query = "SELECT * FROM deliver";
            ResultSet rs = CrudUtil.execute(query);
            return rs;
        }
        public static boolean save(String deliverId, String employeeId, String orderId , String vehicle, String date,Double price) throws SQLException {
            String sql = "INSERT INTO deliver(deliver_id,emp_Id,orders_Id,vehicle,date,price_of_km) VALUES(?, ?, ?, ?, ?)";
            return CrudUtil.execute(sql,deliverId,employeeId,orderId,vehicle,date,price);
        }
        public static boolean update(DeliverDTO deliver) throws SQLException {
            String sql = "UPDATE deliver SET deliver = ?, emp_Id = ?, orders_Id = ?, vehicle = ?,date=?,price_of_km=? WHERE orders_Id = ?";
            return CrudUtil.execute(sql, deliver.getDeliverId(),deliver.getEmployeeId(),deliver.getOrderId(),deliver.getVehicle(),deliver.getDate(),deliver.getPrice());
        }
        public static boolean delete(String orders_Id) throws SQLException {
            String sql = "DELETE FROM deliver WHERE orders_Id = ?";
            return CrudUtil.execute(sql, orders_Id);
        }
    }



