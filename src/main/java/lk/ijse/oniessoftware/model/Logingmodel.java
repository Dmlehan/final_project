package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Logingmodel {

    public static boolean useVerified(String name, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE name= ? AND Password=?";
        ResultSet resultSet = CrudUtil.execute(sql, name, password);
        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public static boolean isUser(String text, String text1) {
        String sql = "";
        return true;
    }
}
