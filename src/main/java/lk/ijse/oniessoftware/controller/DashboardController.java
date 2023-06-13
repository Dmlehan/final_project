package lk.ijse.oniessoftware.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    public JFXButton btnSupplier;
    public static String comDate;


    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnItems;

    @FXML
    private JFXButton btnHarvest;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXButton btnDeliver;

    @FXML
    private AnchorPane pane;
    @FXML

    void customerBtnOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/view/customersform.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.pane.getChildren().clear();
        this.pane.getChildren().add(load);
    }
    @FXML
    void dashboardBtnOnAction(ActionEvent actionEvent) throws IOException {
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/view/homeForm.fxml.")));
    }
    @FXML
    void btnItemsOnAction(ActionEvent actionEvent) throws IOException {
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/view/itemsform.fxml")));
    }
    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/view/Employeeform.fxml")));
    }

    @FXML
    void btnHarvestOnAction(ActionEvent event) throws IOException {
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/view/harvestform.fxml")));
    }
    @FXML
    void btnOrdersOnAction(ActionEvent event) throws IOException {
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/view/Ordersform.fxml")));
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/view/supplierform.fxml")));
    }

    @FXML
    void btnDeliverOnAction(ActionEvent event) throws IOException{
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/view/Deliverform.fxml")));
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/view/view/loginform.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage loginStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        loginStage.setResizable(true);
        loginStage.setScene(loginScene);
        //loginStage.setMaximized(true);
        loginStage.show();

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        pane.getChildren().clear();
        try {
            pane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/view/homeForm.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
