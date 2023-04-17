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
import javafx.scene.layout.AnchorPane;
import lk.ijse.oniessoftware.dto.SupplierDTO;
import lk.ijse.oniessoftware.model.SupplierModel;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class SupplierformController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Onies";
    private final static Properties props = new Properties();

    @FXML
    private AnchorPane supplierAnchor;

    @FXML
    private TableView<SupplierDTO> tableSupplier;
    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colTypes;


    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtSupplierNumber;

    @FXML
    private TextField txtSuppliertName;

    @FXML
    private TextField txtSupplierType;

    @FXML
    private JFXButton btnSupplierSearch;

    @FXML
    private JFXButton btnSupplierAdd;

    @FXML
    private JFXButton btnSupplierUpdate;

    @FXML
    private ImageView btnUpdate1;

    @FXML
    private JFXButton btnSupplierDelete;

    @FXML
    private JFXTextField txtSupplierSearch;

    ObservableList<SupplierDTO> data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValue();
        populateEmployeeTable();
        searchFilter();
    }
    private void searchFilter() {
        FilteredList<SupplierDTO> filteredData = new FilteredList<>(data, b -> true);
        txtSupplierSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate( SupplierDTO-> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (SupplierDTO.getSupplierId().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if(SupplierDTO.getSupplierName().toLowerCase().contains(searchKeyword)){
                    return true;
                }else
                    return false;

            });
        });

        SortedList<SupplierDTO> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableSupplier.comparatorProperty());
        tableSupplier.setItems(sortedData);
    }

    private void setCellValue() {
      colSupplierId.setCellValueFactory(new PropertyValueFactory<>("sup_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("tp"));
        colTypes.setCellValueFactory(new PropertyValueFactory<>("types"));

    }

    private void populateEmployeeTable() {
        try {
            ResultSet rs = SupplierModel.getAll();
            data.clear();
            while (rs.next()){
                data.add(new SupplierDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            if (data != null){
                tableSupplier.setItems(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }


    @FXML
    void btnSupplierAddOnAction(ActionEvent event) {
        String supId = txtSupplierId.getText();
        String name = txtSuppliertName.getText();
        String contact = txtSupplierNumber.getText();
        String types = txtSupplierType.getText();
    }
    @FXML
    void btnSupplierUpdateOnAction(ActionEvent event) {

        String supId = txtSupplierId.getText();
        String name = txtSuppliertName.getText();
        String contact = txtSupplierNumber.getText();
        String types = txtSupplierType.getText();

        var supplier = new SupplierDTO(supId,name,contact,types);

        try {
            boolean isUpdated = SupplierModel.update(supplier);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "huree! Supplier Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
            e.printStackTrace();
        }
    }


    @FXML
    void btnSupplierDeleteOnAction(ActionEvent event) {

        String supId = txtSupplierId.getText();
        try {
            boolean isDeleted = SupplierModel.delete(supId);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier deleted").show();
                populateEmployeeTable();
                searchFilter();
            }else {
                new Alert(Alert.AlertType.WARNING,"Supplier not deleted").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }
    }



    @FXML
    void btnSupplierSearchOnAction(ActionEvent event) {

    }



}
