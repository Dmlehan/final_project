package lk.ijse.oniessoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.oniessoftware.model.Logingmodel;

import java.io.IOException;
import java.sql.SQLException;

public class Loginformcontroller {

    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXButton btnLogin;
    public AnchorPane pane;


    public void txtUserNameOnAction(ActionEvent actionEvent) {
    }

    public void txtPaswordOnAction(ActionEvent actionEvent) {
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String name = txtUserName.getText();
        String password = txtPassword.getText();
        try {
            boolean isUserVerified = Logingmodel.useVerified(name,password);
            if (isUserVerified) {
                System.out.println("Verified");
//                new Alert(Alert.AlertType.CONFIRMATION, "User Verified!!!").show();
                pane.getChildren().clear();
                pane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml")));
            } else {
                new Alert(Alert.AlertType.WARNING, "User Not Verified!!!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Oops something wrong!!!").show();
            e.printStackTrace();
        }


    }
}
