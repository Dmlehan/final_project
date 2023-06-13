package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.dto.AttendenceDTO;
import lk.ijse.oniessoftware.dto.EmployeeDTO;
import lk.ijse.oniessoftware.model.tm.AttendeceTM;
import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendenceModel {

    public static ResultSet getAll() throws SQLException {
        String query = "SELECT * FROM employee_attendence ";
        ResultSet rs = CrudUtil.execute(query);
        return rs;
    }

    public static boolean save(String date, String a_Code, String empId, Integer extraHour,Double daily_salary) throws SQLException {
        String sql = "INSERT INTO employee_attendence(date,a_Code,emp_Id,extra_hour,daily_salary) VALUES(?, ?, ?, ?,?)";
        return CrudUtil.execute(sql,date,a_Code,empId,extraHour,daily_salary);
    }

    public static boolean update(AttendenceDTO attendence) throws SQLException {
        String sql = "UPDATE employee_attendence SET date = ?, a_Code= ?, extra_hour = ?,daily_salary=? WHERE emp_Id = ?";
        return CrudUtil.execute(sql, attendence.getA_code(),attendence.getDate(),attendence.getExtra_hour(),attendence.getDailySalary(),attendence.getEmp_id());
    }

    public static boolean delete(String empId) throws SQLException {
        String sql = "DELETE FROM employee_attendence WHERE emp_Id = ?";
        return CrudUtil.execute(sql, empId);
    }

    public static AttendenceDTO search(String empId) throws SQLException {

        String sql = "SELECT * FROM employee_attendence WHERE emp_Id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, empId);

        if (resultSet.next()) {
            String date = resultSet.getString(1);
            String a_Code = resultSet.getString(2);
            String emp_Id = resultSet.getString(3);
           Integer extra_hour = resultSet.getInt(4);
           Double daily_Salary = resultSet.getDouble(5);



            return new AttendenceDTO(date, a_Code, emp_Id, extra_hour,daily_Salary );
        }
        return null;
    }
}
