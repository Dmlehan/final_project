package lk.ijse.oniessoftware.model;

import lk.ijse.oniessoftware.dto.HarvestDTO;
import lk.ijse.oniessoftware.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

    public class HarvestModel {
        public static ResultSet getAll() throws SQLException {
            String query = "SELECT * FROM harvest";
            ResultSet rs = CrudUtil.execute(query);
            return rs;
        }
        public static boolean save(String hCode, String types,  Double Quantity) throws SQLException {
            String sql = "INSERT INTO harvest(h_code, types, Qty) VALUES(?, ?, ?)";
            return CrudUtil.execute(sql, hCode,types,Quantity);
        }
        public static boolean update(HarvestDTO harvest) throws SQLException {
            String sql = "UPDATE harvest  types = ?, Qty = ?, WHERE h_code = ?";
            return CrudUtil.execute(sql, harvest.getHarvestId(), harvest.getHarvestType(),harvest.getHarvestQuantity());
        }
        public static boolean delete(String empId) throws SQLException {
            String sql = "DELETE FROM harvest WHERE h_code = ?";
            return CrudUtil.execute(sql, empId);
        }

        public static HarvestDTO search(String hCode) throws SQLException {
            String sql = "SELECT * FROM harvest WHERE h_code = ?";
            ResultSet resultSet = CrudUtil.execute(sql,hCode );

            if (resultSet.next()) {
                String code= resultSet.getString(1);
                String types = resultSet.getString(2);
                Double Qty = resultSet.getDouble(3);

                return new HarvestDTO(code,types,Qty);
            }
            return null;
        }
    }

