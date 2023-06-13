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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.oniessoftware.dto.CustomerDTO;
import lk.ijse.oniessoftware.dto.EmployeeDTO;
import lk.ijse.oniessoftware.model.CustomerModel;
import lk.ijse.oniessoftware.model.EmployeeModel;
import lk.ijse.oniessoftware.util.Regex;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class CustomerformController implements Initializable {

    private final static String URL = "jdbc:mysql://localhost:3306/Onies";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password", "1234");
    }

    public JFXButton btnCustomerClear;
    @FXML
    private JFXButton btnCustomerUpdate;
    @FXML
    private AnchorPane customerAnchor;

    @FXML
    private TextField txtCustId;

    @FXML
    private TextField txtCustNumber;

    @FXML
    private TextField txtCustName;

    @FXML
    private TextField txtUserId;

    @FXML
    private TableView<CustomerDTO> tblCustomer;

    @FXML
    private TableColumn<?, ?> colCustId;

    @FXML
    private TableColumn<?, ?> colCustName;

    @FXML
    private TableColumn<?, ?> colCustNumber;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnAddCustomer;

    @FXML
    private JFXButton btnCustomerDelete;

    @FXML
    private Text lblCustomer;

    @FXML
    private JFXTextField txtCustomerSearch;

    ObservableList<CustomerDTO> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValue();
        populateCustomerTable();
        searchFilter();
    }

    private void searchFilter() {
        FilteredList<CustomerDTO> filteredData = new FilteredList<>(data, b -> true);
        txtCustomerSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(CustomerDTO -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (CustomerDTO.getCust_Id().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if(CustomerDTO.getName().toLowerCase().contains(searchKeyword)){
                    return true;
                }else
                    return false;

            });
        });

        SortedList<CustomerDTO> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblCustomer.comparatorProperty());
        tblCustomer.setItems(sortedData);
    }

    private void setCellValue() {
        colCustId.setCellValueFactory(new PropertyValueFactory<>("cust_Id"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustNumber.setCellValueFactory(new PropertyValueFactory<>("c_Tp"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("user_Id"));

    }

    private void populateCustomerTable() {
        try {
            ResultSet rs = CustomerModel.getAll();
            data.clear();
            while (rs.next()){
                data.add(new CustomerDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }
            if (data != null){
                tblCustomer.setItems(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }



    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        String custId = txtCustId.getText();
        String custName=txtCustName.getText();
        String contact=txtCustNumber.getText();
        String user=txtUserId.getText();
        if(Regex.validateCustomerCID(custId)&& Regex.validateMobile(contact)&&Regex.validateUserCID(user)){
        try {
            boolean isSaved = CustomerModel.save(custId,custName,contact,user);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer saved!!!").show();
                populateCustomerTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();

        }

       }else{
            new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
        }
    }

    @FXML
    void btnCustomerDeleteOnAction(ActionEvent event) {

        String custId = txtCustId.getText();
        try {
            boolean isDeleted = CustomerModel.delete(custId);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer deleted").show();
                populateCustomerTable();
                searchFilter();
            }else {
                new Alert(Alert.AlertType.WARNING,"Customer not deleted").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }


    }

    @FXML
    void btnCustomerUpdateOnAction(ActionEvent event) {
        String custId = txtCustId.getText();
        String custName=txtCustName.getText();
        String contact=txtCustNumber.getText();
        String userId=txtUserId.getText();
        if(Regex.validateCustomerCID(custId)&& Regex.validateMobile(contact)&&Regex.validateUserCID(userId)) {
            var customer = new CustomerDTO(custId, custName, contact, userId);

            try {
                boolean isUpdated = CustomerModel.update(customer);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "huree! Customer Updated!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
                e.printStackTrace();
            }

        }

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

        String custId = txtCustomerSearch.getText();
        try {
           CustomerDTO customer = CustomerModel.search(custId);
            if (customer != null) {
                txtCustId.setText(customer.getCust_Id());
                txtCustName.setText(customer.getName());
                txtCustNumber.setText(customer.getC_Tp());
                txtUserId.setText(customer.getUser_Id());


            } else {
                new Alert(Alert.AlertType.WARNING, "no Customer found :(").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }

    }

    public void tblCustomerOnMouseCLick(MouseEvent mouseEvent) {
        CustomerDTO selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if(selectedItem==null)return;
        txtCustId.setText(selectedItem.getCust_Id());
        txtUserId.setText(selectedItem.getUser_Id());
        txtCustNumber.setText(selectedItem.getC_Tp());
        txtCustName.setText(selectedItem.getName());
    }

    public void btnCustomerClearOnAction(ActionEvent event) {
        txtCustId.setText(null);
        txtCustName.setText(null);
        txtCustNumber.setText(null);
        txtUserId.setText(null);
    }
}
