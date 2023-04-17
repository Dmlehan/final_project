

package lk.ijse.oniessoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import lk.ijse.oniessoftware.dto.DeliverDTO;
import lk.ijse.oniessoftware.model.DeliverModel;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class DeliverformController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Onies";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password", "1234");
    }

    @FXML
    private TableView<DeliverDTO> tableDeliver;

    @FXML
    private TableColumn<?, ?> colDeliverId;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colVehicle;

    @FXML
    private TextField txtDeliverId;

    @FXML
    private TextField txtDVehicle;

    @FXML
    private TextField txtDEmployeeId;

    @FXML
    private TextField txtDPrice;

    @FXML
    private TextField txtDOrderId;

    @FXML
    private TextField txtDate;

    @FXML
    private JFXButton btnDeliverSearch;

    @FXML
    private JFXTextField txtDeliverSearch;

    @FXML
    private JFXButton btnDeliverAdd;

    @FXML
    private JFXButton btnDeliverUpdate;

    @FXML
    private ImageView btnUpdate1;

    @FXML
    private JFXButton btnDeliverDelete;

    ObservableList<DeliverDTO> data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValue();
        populateDeliverTable();
        searchFilter();

    }
    private void searchFilter() {
        FilteredList<DeliverDTO> filteredData = new FilteredList<>(data, b -> true);
        txtDeliverSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(DeliverDTO -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (DeliverDTO.getOrderId().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if(DeliverDTO.getDeliverId().toLowerCase().contains(searchKeyword)){
                    return true;
                }else
                    return false;

            });
        });

        SortedList<DeliverDTO> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableDeliver.comparatorProperty());
        tableDeliver.setItems(sortedData);
    }
    private void setCellValue() {
        colDeliverId.setCellValueFactory(new PropertyValueFactory<>("deliver_Id"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price_of_Km"));
        colVehicle.setCellValueFactory(new PropertyValueFactory<>("vehicle"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("empId"));
    }


    private void populateDeliverTable() {
        try {
            ResultSet rs = DeliverModel.getAll();
            data.clear();
            while (rs.next()){
                data.add(new DeliverDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDouble(6)));
            }
            if (data != null){
                tableDeliver.setItems(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }


    @FXML
    void btnDeliverAddOnAction(ActionEvent event) {
        String deliverId = txtDeliverId.getText();
        String employeeId = txtDEmployeeId.getText();
        String orderId= txtDOrderId.getText();
        String date = txtDate.getText();
        String vehicle = txtDVehicle.getText();
        Double price = Double.parseDouble(txtDPrice.getText());

    }

    @FXML
    void btnDeliverUpdateOnAction(ActionEvent event) {
        String deliverId = txtDeliverId.getText();
        String employeeId = txtDEmployeeId.getText();
        String orderId= txtDOrderId.getText();
        String date = txtDate.getText();
        String vehicle = txtDVehicle.getText();
        Double price = Double.parseDouble(txtDPrice.getText());



        var deliver = new DeliverDTO(deliverId,employeeId,orderId,date,vehicle,price);

        try {
            boolean isUpdated = DeliverModel.update(deliver);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "huree! Deliver Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeliverDeleteOnAction(ActionEvent event) {

        String orders_Id = txtDOrderId.getText();
        try {
            boolean isDeleted = DeliverModel.delete(orders_Id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Deliver deleted").show();
                populateDeliverTable();
                searchFilter();
            }else {
                new Alert(Alert.AlertType.WARNING,"Deliver not deleted").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }

    }

    @FXML
    void btnDeliverSearchOnAction(ActionEvent event) {

    }



}
