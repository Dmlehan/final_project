package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.db.DBConnection;
import lk.ijse.oniessoftware.dto.UsersDTO;
import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPasswordModel {
    public static boolean userVerified(String users_Id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM users WHERE users_Id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, users_Id);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public static boolean update(UsersDTO user) throws SQLException {
        String sql = "UPDATE users SET name = ?, emp_Id = ?, password = ? WHERE users_Id = ?";
        return CrudUtil.execute(sql, user.getName(), user.getEmpId(),user.getPassword(),user.getUsers_Id());
    }

}
