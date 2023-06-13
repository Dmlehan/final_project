package lk.ijse.oniessoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lk.ijse.oniessoftware.model.CreateAccountModel;
import lk.ijse.oniessoftware.util.Regex;

import java.io.IOException;
import java.sql.SQLException;

public class CreateaccountController {

    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtConfirmPassword;

    @FXML
    private JFXButton btnCreate;

    @FXML
    void btnCreateOnAction(ActionEvent event) {
        String empId = txtEmployeeId.getText();
        String userId = txtUserId.getText();
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (!password.equals(confirmPassword)) {
            new Alert(Alert.AlertType.WARNING, "Passwords do not match!").show();
            return;
        }
        if (Regex.validateEmployeeCID(empId) && Regex.validateUserCID(userId) && Regex.validateUsername(username)&& Regex.validatePassword(password)) {
            try {

                boolean empIdVerified = CreateAccountModel.empIdVerified(empId);
                if (empIdVerified) {
                    boolean isSave = CreateAccountModel.save(userId,username,empId,password);
                    if (isSave) {
                        new Alert(Alert.AlertType.INFORMATION, "Account Create successful!").show();

                    } else {
                        new Alert(Alert.AlertType.ERROR, "Oops something went wrong while updating password!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "EmpId Not Verified!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Oops something wrong!").show();
            }
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Hint : [aA-zZ0-9@]{8,20}").show();
        }

    }

}
