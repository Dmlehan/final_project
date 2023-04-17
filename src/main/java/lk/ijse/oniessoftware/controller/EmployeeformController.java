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
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.oniessoftware.model.EmployeeModel;
import lk.ijse.oniessoftware.dto.EmployeeDTO;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmployeeformController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Onies";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password", "1234");
    }

    public JFXButton btnDelete;
    @FXML
    private JFXTextField txtAEmSearch;

    @FXML
    private JFXTextField txtAEmId;

    @FXML
    private JFXTextField txtAEmName;

    @FXML
    private JFXTextField txtAEmNic;

    @FXML
    private JFXTextField txtAEmTele;

    @FXML
    private JFXTextField txtEmPH;

    @FXML
    private TableView<EmployeeDTO> tblEmployee;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colCcontact;

    @FXML
    private TableColumn<?, ?> colPay;




    ObservableList<EmployeeDTO> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValue();
        populateEmployeeTable();
        searchFilter();
    }

    private void searchFilter() {
        FilteredList<EmployeeDTO> filteredData = new FilteredList<>(data, b -> true);
        txtAEmSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(EmployeeDTO -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (EmployeeDTO.getEmployeeId().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if(EmployeeDTO.getName().toLowerCase().contains(searchKeyword)){
                    return true;
                }else
                    return false;

            });
        });

        SortedList<EmployeeDTO> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblEmployee.comparatorProperty());
        tblEmployee.setItems(sortedData);
    }

    private void setCellValue() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colCcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colPay.setCellValueFactory(new PropertyValueFactory<>("payment_hour"));
    }

    private void populateEmployeeTable() {
        try {
            ResultSet rs = EmployeeModel.getAll();
            data.clear();
            while (rs.next()){
                data.add(new EmployeeDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getString(5)));
            }
            if (data != null){
                tblEmployee.setItems(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }
    @FXML
    void btnAddOnAction(ActionEvent event) {
        String empId = txtAEmId.getText();
        String empName = txtAEmName.getText();
        String empNic = txtAEmNic.getText();
        Double salary = Double.parseDouble(txtEmPH.getText());
        String contact = txtAEmTele.getText();

        try {
            boolean isSaved = EmployeeModel.save(empId,empName,empNic,salary,contact);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();

        }
    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String empId = txtAEmId.getText();
        String empName = txtAEmName.getText();
        String empNic = txtAEmNic.getText();
        Double salary = Double.parseDouble(txtEmPH.getText());
        String contact = txtAEmTele.getText();

        var employee = new EmployeeDTO(empId, empName, empNic, salary, contact);

        try {
            boolean isUpdated = EmployeeModel.update(employee);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "huree! Employee Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String empId = txtAEmId.getText();
        try {
            boolean isDeleted = EmployeeModel.delete(empId);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee deleted").show();
                populateEmployeeTable();
                searchFilter();
            }else {
                new Alert(Alert.AlertType.WARNING,"Employee not deleted").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }
    }


    public void btnSearchOnAction(ActionEvent actionEvent) {
    }
}
