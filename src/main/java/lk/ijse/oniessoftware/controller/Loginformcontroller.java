package lk.ijse.oniessoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.oniessoftware.model.Logingmodel;
import lk.ijse.oniessoftware.util.Regex;

import java.io.IOException;
import java.sql.SQLException;

public class Loginformcontroller {

    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXButton btnLogin;
    public AnchorPane pane;
    public JFXButton btnCreateAccount;


    public void txtUserNameOnAction(ActionEvent actionEvent) {
    }

    public void txtPaswordOnAction(ActionEvent actionEvent) {
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String name = txtUserName.getText();
        String password = txtPassword.getText();
        if(Regex.validateUsername(txtUserName.getText())&&Regex.validatePassword(txtPassword.getText())){
            try {
                boolean isUserVerified = Logingmodel.useVerified(name,password);
                if (isUserVerified) {
                    System.out.println("Verified");
//                new Alert(Alert.AlertType.CONFIRMATION, "User Verified!!!").show();
                    pane.getChildren().clear();
                    pane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/view/dashboard.fxml")));
                } else {
                    new Alert(Alert.AlertType.WARNING, "User Not Verified!!!").show();
                }
            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR,"Oops something wrong!!!").show();
                e.printStackTrace();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Invalid Input !").show();
        }



    }
    @FXML
    void btnCreateAccountOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Loginformcontroller.class.getResource("/view/view/createaccount.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    public void btnForgetPasswordOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Loginformcontroller.class.getResource("/view/view/ForgetPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
}
