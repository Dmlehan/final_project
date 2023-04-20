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
import lk.ijse.oniessoftware.dto.EmployeeDTO;
import lk.ijse.oniessoftware.dto.ItemDTO;
import lk.ijse.oniessoftware.model.EmployeeModel;
import lk.ijse.oniessoftware.model.ItemsModel;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ItemsformController  implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Onies";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password", "1234");
    }


    @FXML
    private TableView<ItemDTO> tableItem;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colProfit;

    @FXML
    private TableColumn<?, ?> colStore;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TextField txtItemStore;


    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtItemProfit;

    @FXML
    private TextField txtItemUnitPrice;

    @FXML
    private TextField txtItemType;


    @FXML
    private JFXButton btnItemSearch;

    @FXML
    private JFXButton btnItemAdd;

    @FXML
    private JFXButton btnItemUpdate;

    @FXML
    private ImageView btnUpdate1;

    @FXML
    private JFXButton btnItemDelete;

    @FXML
    private JFXTextField txtItemsSearch;

    ObservableList<ItemDTO> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValue();
        populateItemsTable();
        searchFilter();
    }
    private void searchFilter() {
        FilteredList<ItemDTO> filteredData = new FilteredList<>(data, b -> true);
      txtItemsSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate(ItemDTO -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (ItemDTO.getItem_Code().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if(ItemDTO.getDescription().toLowerCase().contains(searchKeyword)){
                    return true;
                }else
                    return false;

            });
        });

        SortedList<ItemDTO> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableItem.comparatorProperty());
        tableItem.setItems(sortedData);
    }

    private void setCellValue() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("item_Code"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colProfit.setCellValueFactory(new PropertyValueFactory<>("profit"));
        colStore.setCellValueFactory(new PropertyValueFactory<>("store"));
        colType.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void populateItemsTable() {
        try {
            ResultSet rs = ItemsModel.getAll();
            data.clear();
            while (rs.next()){
                data.add(new ItemDTO(rs.getString(1),rs.getInt(2),rs.getString(3),rs.getDouble(4),rs.getDouble(5)));
            }
            if (data != null){
                tableItem.setItems(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    @FXML
    void btnItemAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnItemDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnItemSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnItemUpdateOnAction(ActionEvent event) {

    }


}

