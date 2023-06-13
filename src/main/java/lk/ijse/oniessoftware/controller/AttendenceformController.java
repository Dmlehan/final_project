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
import lk.ijse.oniessoftware.db.DBConnection;
import lk.ijse.oniessoftware.dto.AttendenceDTO;
import lk.ijse.oniessoftware.model.AttendenceModel;
import lk.ijse.oniessoftware.model.EmployeeModel;
import lk.ijse.oniessoftware.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;


public class AttendenceformController implements Initializable {

    @FXML
    private TextField txtEmployeeID;

    @FXML
    private TextField txtAttendenceCode;

    @FXML
    private TextField txtExtraHour;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDailySalary;

    @FXML
    private TableView<AttendenceDTO> tblEmpAttendence;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colAttendenceCode;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colExtra_hour;

    @FXML
    private TableColumn<?, ?> colDailySalary;

    @FXML
    private JFXTextField txtAEmpSearch;


    @FXML
    private JFXButton btnAttendSearch;

    @FXML
    private JFXButton btnAttendencAdd;

    @FXML
    private JFXButton btnAttendenceUpdate;

    @FXML
    private ImageView btnUpdate1;

    @FXML
    private JFXButton btnAttendencDelete;


            ObservableList<AttendenceDTO> data = FXCollections.observableArrayList();

            @Override
            public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
                setCellValue();
                populateAttendenceTable();
                searchFilter();
            }

            private void searchFilter() {
                FilteredList<AttendenceDTO> filteredData = new FilteredList<>(data, b -> true);
                txtAEmpSearch.textProperty().addListener((observable, oldValue, newValue) ->{
                    filteredData.setPredicate( AttendenceDTO-> {
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                            return true;
                        }
                        String searchKeyword = newValue.toLowerCase();

                        if (AttendenceDTO.getEmp_id().toLowerCase().contains(searchKeyword)){
                            return true;
                        }else if(AttendenceDTO.getDate().toLowerCase().contains(searchKeyword)){
                            return true;
                        }else
                            return false;

                    });
                });

                SortedList<AttendenceDTO> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(tblEmpAttendence.comparatorProperty());
                tblEmpAttendence.setItems(sortedData);
            }
            private void populateAttendenceTable() {
                try {
                    ResultSet rs = AttendenceModel.getAll();
                    data.clear();
                    while (rs.next()){
                        data.add(new AttendenceDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4), rs.getDouble(5)));
                    }
                    if (data != null){
                        tblEmpAttendence.setItems(data);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
                }
            }
            private void setCellValue() {
                colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                colAttendenceCode.setCellValueFactory(new PropertyValueFactory<>("a_code"));
                colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
                colExtra_hour.setCellValueFactory(new PropertyValueFactory<>("extra_hour"));
                colDailySalary.setCellValueFactory(new PropertyValueFactory<>("dailySalary"));
           }


            @FXML
            void btnAttendencAddOnAction(ActionEvent event) {
                String date = txtDate.getText();
                String a_Code =txtAttendenceCode.getText();
                String emp_Id = txtEmployeeID.getText();
                Integer extrs_hour =Integer.parseInt(txtExtraHour.getText());
                Double salary = Double.parseDouble(txtDailySalary.getText());


                if (Regex.validateEmployeeCID(emp_Id) && Regex.validateAttendenceCID(a_Code)) {
                    try {
                        boolean isSaved = AttendenceModel.save(date,a_Code,emp_Id,extrs_hour,salary);
                        if (isSaved) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Attendence saved!!!").show();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();

                    }
                }else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
                }
            }
            public void btnAttendSearchOnAction(ActionEvent event) {
                String empId = txtAEmpSearch.getText();
                try {
                    AttendenceDTO attendence= AttendenceModel.search(empId);
                    if (attendence!= null) {
                        txtDate.setText(attendence.getDate());
                        txtAttendenceCode.setText(attendence.getA_code());
                        txtDailySalary.setText(String.valueOf(attendence.getDailySalary()));
                        txtExtraHour.setText(String.valueOf(attendence.getExtra_hour()));

                    } else {
                        new Alert(Alert.AlertType.WARNING, "no employee found :(").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
                }


           }
           public void btnAttendenceUpdateOnAction(ActionEvent event) {
               String date = txtDate.getText();
               String a_Code =txtAttendenceCode.getText();
               String emp_Id = txtEmployeeID.getText();
               Integer extrs_hour =Integer.parseInt(txtExtraHour.getText());
               Double salary = Double.parseDouble(txtDailySalary.getText());

               if (Regex.validateEmployeeCID(emp_Id) && Regex.validateAttendenceCID(a_Code)) {
                   var attendence = new AttendenceDTO(date,a_Code,emp_Id,extrs_hour,salary);

                   try {
                       boolean isUpdated = AttendenceModel.update(attendence);
                       if (isUpdated) {
                           new Alert(Alert.AlertType.CONFIRMATION, "huree! Attendence Updated!").show();
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
            void btnAttendencDeleteOnAction(ActionEvent event) {
                String empId = txtEmployeeID.getText();
                try {
                    boolean isDeleted = EmployeeModel.delete(empId);
                    if (isDeleted){
                        new Alert(Alert.AlertType.CONFIRMATION,"Attendence deleted").show();
                        populateAttendenceTable();
                        searchFilter();
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Attendence not deleted").show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR,"SQL Error").show();
                }
            }

       public void tblEmployeeAttendenceOnMouseCLicke(javafx.scene.input.MouseEvent mouseEvent) {
          AttendenceDTO selectedItem=tblEmpAttendence.getSelectionModel().getSelectedItem();
          if(selectedItem==null)return;
             txtDate.setText(selectedItem.getDate());
             txtAttendenceCode.setText(selectedItem.getA_code());
             txtEmployeeID.setText(selectedItem.getEmp_id());
             txtExtraHour.setText(String.valueOf(selectedItem.getExtra_hour()));
             txtDailySalary.setText(String.valueOf(selectedItem.getDailySalary()));
    }

    public void reportOnAction(ActionEvent event) throws SQLException, JRException {
        JasperDesign jasDesign = JRXmlLoader.load("src/main/java/lk/ijse/oniessoftware/report/Attend.jrxml");
        JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
        JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasPrint,false);
    }

    public void btnAttendenceClearOnAction(ActionEvent event) {
             txtDate.setText(null);
             txtAttendenceCode.setText(null);
             txtDailySalary.setText(null);
             txtEmployeeID.setText(null);
             txtExtraHour.setText(null);
             txtDailySalary.setText(null);

    }
}





