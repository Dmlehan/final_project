package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.db.DBConnection;
import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateAccountModel {


    public static boolean empIdVerified(String empId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE emp_Id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, empId);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public static boolean save(String users_Id, String userName,String emp_Id, String password) throws SQLException {
        String sql = "INSERT INTO Users(users_Id,name, emp_Id, Password) VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(sql, users_Id, userName, emp_Id, password);
    }
}
