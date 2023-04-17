package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.dto.EmployeeDTO;
import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel {
    public static ResultSet getAll() throws SQLException {
        String query = "SELECT * FROM employee";
        ResultSet rs = CrudUtil.execute(query);
        return rs;
    }
    public static boolean save(String empId, String empName, String empNic, Double salary, String contact) throws SQLException {
        String sql = "INSERT INTO employee(emp_Id, name, nic, payment_Hour, tp) VALUES(?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, empId, empName, empNic, salary, contact);
    }
    public static boolean update(EmployeeDTO employee) throws SQLException {
        String sql = "UPDATE employee SET name = ?, nic = ?, payment_Hour = ?, tp = ? WHERE emp_Id = ?";
        return CrudUtil.execute(sql, employee.getEmployeeId(), employee.getName(), employee.getNic(), employee.getPayment_hour(),employee.getContact());
    }
    public static boolean delete(String empId) throws SQLException {
        String sql = "DELETE FROM employee WHERE emp_Id = ?";
        return CrudUtil.execute(sql, empId);
    }
}

