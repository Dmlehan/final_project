package lk.ijse.oniessoftware.controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.oniessoftware.dto.UsersDTO;
import lk.ijse.oniessoftware.model.ForgotPasswordModel;
import lk.ijse.oniessoftware.util.Regex;

import java.io.IOException;
import java.sql.SQLException;

public class ForgetPasswordController {

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXTextField txtUserPassword;

    @FXML
    private JFXTextField txtConfirmUserPassword;
    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtEmployeeID;

    @FXML
    private JFXButton btnResertButton;

    @FXML
    private AnchorPane root;

    private static Stage loginStage;

    Scene loginScene;

        @FXML
        public void btnResertButtonOnAction(ActionEvent event) throws IOException{
            String userName = txtUserName.getText();
            String userId = txtUserId.getText();
            String empId = txtEmployeeID.getText();
            String password = txtUserPassword.getText();
            String confirmPassword = txtConfirmUserPassword.getText();

            if (!password.equals(confirmPassword)) {
                new Alert(Alert.AlertType.WARNING, "Passwords do not match!").show();
                return;
            }

            if (Regex.validateEmployeeCID(empId) && Regex.validateUsername(userName)&& Regex.validatePassword(password)) {
                try {
                    boolean isUserVerified = ForgotPasswordModel.userVerified(userId);
                    if (isUserVerified) {
                        var user = new UsersDTO(userId, userName, empId, password);
                        boolean isUpdated = ForgotPasswordModel.update(user);
                        if (isUpdated) {
                            new Alert(Alert.AlertType.INFORMATION, "Password reset successful!").show();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Oops something went wrong while updating password!").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.WARNING, "User Not Verified!").show();
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

