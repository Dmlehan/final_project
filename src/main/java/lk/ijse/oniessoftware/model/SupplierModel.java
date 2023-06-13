package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.dto.EmployeeDTO;
import lk.ijse.oniessoftware.dto.SupplierDTO;
import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public static ResultSet getAll() throws SQLException {
        String query = "SELECT * FROM supplier";
        ResultSet rs = CrudUtil.execute(query);
        return rs;
    }
    public static boolean save(String supId, String supName,String contact,String types) throws SQLException {
        String sql = "INSERT INTO supplier(sup_id, name,tp, types) VALUES(?, ?, ?, ? )";
        return CrudUtil.execute(sql, supId,supName,contact,types);
    }
    public static boolean update(SupplierDTO supplier) throws SQLException {
        String sql = "UPDATE supplier SET name = ?, tp = ?, types= ? WHERE sup_id = ?";
        return CrudUtil.execute(sql, supplier.getSupplierId(),supplier.getSupplierName(),supplier.getContactNumber(),supplier.getTypes());
    }
    public static boolean delete(String supId) throws SQLException {
        String sql = "DELETE FROM supplier WHERE sup_id = ?";
        return CrudUtil.execute(sql, supId);
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT sup_id FROM supplier";

        ResultSet resultSet = CrudUtil.execute(sql);
        List<String> ids = new ArrayList<>();

        while (resultSet.next()){
            ids.add(resultSet.getString(1));
        }
        return  ids;
    }


}
