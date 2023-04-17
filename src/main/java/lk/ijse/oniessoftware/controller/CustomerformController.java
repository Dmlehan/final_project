package lk.ijse.oniessoftware.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class CustomerformController {

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
    private TableView<?> customerTable;

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
    private ImageView btnCustomerUpdate;

    @FXML
    private JFXButton btnCustomerDelete;

    @FXML
    private Text lblCustomer;

    @FXML
    private JFXTextField txtCustomerSearch;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnCustomerDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnCustomerUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

}
