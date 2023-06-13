package lk.ijse.oniessoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.oniessoftware.model.EmployeeModel;
import lk.ijse.oniessoftware.dto.EmployeeDTO;
import lk.ijse.oniessoftware.util.Regex;


import java.io.IOException;
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
    public JFXButton btnEmployeeClear;

    @FXML
    private JFXButton btnAttendence;
    @FXML
    private JFXTextField txtAEmSearch;

    @FXML
    private TextField txtEmployeePay;

    @FXML
    private TextField txtEmployeeNumber;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeeNic;

    @FXML
    private TextField txtEmployeeId;


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
        String empId = txtEmployeeId.getText();
        String empName =txtEmployeeName.getText();
        String empNic = txtEmployeeNic.getText();
        Double salary = Double.parseDouble(txtEmployeePay.getText());
        String contact = txtEmployeeNumber.getText();
        if (Regex.validateEmployeeCID(empId) && Regex.validateName(empName)&& Regex.validateNIC(empNic)&&Regex.validateMobile(contact)) {
            try {
                boolean isSaved = EmployeeModel.save(empId, empName, empNic, salary, contact);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();

            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
        }
    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String empId = txtEmployeeId.getText();
        String empName =txtEmployeeName.getText();
        String empNic = txtEmployeeNic.getText();
        Double salary = Double.parseDouble(txtEmployeePay.getText());
        String contact = txtEmployeeNumber.getText();

        if (Regex.validateEmployeeCID(empId) && Regex.validateName(empName)&& Regex.validateNIC(empNic)&&Regex.validateMobile(contact)) {
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
       }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
        }
    }


        @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String empId = txtEmployeeId.getText();
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
        String empId = txtAEmSearch.getText();
        try {
            EmployeeDTO employee = EmployeeModel.search(empId);
            if (employee != null) {
                txtEmployeeId.setText(employee.getEmployeeId());
                txtEmployeeNic.setText(employee.getNic());
                txtEmployeeNumber.setText(employee.getContact());
                txtEmployeeName.setText(employee.getName());
                txtEmployeePay.setText(String.valueOf(employee.getPayment_hour()));

            } else {
                new Alert(Alert.AlertType.WARNING, "no employee found :(").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }

    public void tblEmployeeOnMouseCLicke(MouseEvent mouseEvent) {
        EmployeeDTO selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        if(selectedItem==null)return;
        txtEmployeeId.setText(selectedItem.getEmployeeId());
        txtEmployeeName.setText(selectedItem.getName());
        txtEmployeeNumber.setText(selectedItem.getContact());
        txtEmployeePay.setText(String.valueOf(selectedItem.getPayment_hour()));
        txtEmployeeNic.setText(selectedItem.getNic());
    }

    public void btnAttendenceOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Loginformcontroller.class.getResource("/view/view/Attendenceform.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    public void btnEmployeeClearOnAction(ActionEvent event) {

        txtEmployeeId.setText(null);
        txtEmployeeName.setText(null);
        txtEmployeeNic.setText(null);
        txtEmployeeNumber.setText(null);
        txtEmployeePay.setText(null);
    }
}
