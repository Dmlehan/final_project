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
import lk.ijse.oniessoftware.dto.EmployeeDTO;
import lk.ijse.oniessoftware.dto.ItemDTO;
import lk.ijse.oniessoftware.model.EmployeeModel;
import lk.ijse.oniessoftware.model.ItemsModel;
import lk.ijse.oniessoftware.util.Regex;

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
        String  itemCode = txtItemCode.getText();
        String itemType = txtItemType.getText();
        int itemStore = Integer.parseInt(txtItemStore.getText());
        Double unitPrice = Double.parseDouble(txtItemUnitPrice.getText());
        Double profit = Double.parseDouble(txtItemProfit.getText());

        if (Regex.validateItemCID(itemCode)) {
            try {
                boolean isSaved = ItemsModel.save(itemCode, itemStore,itemType,unitPrice, profit);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item saved!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();

            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
        }


    }

    @FXML
    void btnItemDeleteOnAction(ActionEvent event) {
        String itemId = txtItemCode.getText();
        try {
            boolean isDeleted = ItemsModel.delete(itemId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Deleted").show();
                populateItemsTable();
                searchFilter();
            } else {
                new Alert(Alert.AlertType.WARNING, "Item not Deleted").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error").show();
        }
    }

    @FXML
    void btnItemSearchOnAction(ActionEvent event) {
        String itemCode=txtItemCode.getText();
        try {
            ItemDTO item = ItemsModel.search(itemCode);
            if (item != null) {
                txtItemCode.setText(item.getItem_Code());
                txtItemType.setText(item.getDescription());
                txtItemProfit.setText(String.valueOf(item.getProfit()));
                txtItemUnitPrice.setText(String.valueOf(item.getUnit_price()));
                txtItemStore.setText(String.valueOf(item.getStore()));
            } else {
                new Alert(Alert.AlertType.WARNING, "no item found:(").show();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR,"oops some thing went wrong:(").show();
        }


        }



    @FXML
    void btnItemUpdateOnAction(ActionEvent event) {
        String  itemCode = txtItemCode.getText();
        String itemType = txtItemType.getText();
        int itemStore = Integer.parseInt(txtItemStore.getText());
        Double unitPrice = Double.parseDouble(txtItemUnitPrice.getText());
        Double profit = Double.parseDouble(txtItemProfit.getText());
        if (Regex.validateItemCID(itemCode)) {
            var item = new ItemDTO(itemCode,itemStore,itemType,unitPrice,profit);

            try {
                boolean isUpdated = ItemsModel.update(item);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "huree! Item Updated!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
                e.printStackTrace();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
        }


    }


    public void tblItemOnMouseCLick(MouseEvent mouseEvent) {
        ItemDTO selectedItem=tableItem.getSelectionModel().getSelectedItem();
        if(selectedItem==null) return;
        txtItemCode.setText(selectedItem.getItem_Code());
        txtItemType.setText(selectedItem.getDescription());
        txtItemProfit.setText(String.valueOf(selectedItem.getProfit()));
        txtItemStore.setText(String.valueOf(selectedItem.getStore()));
        txtItemUnitPrice.setText(String.valueOf(selectedItem.getUnit_price()));
    }

    public void btnItemClearOnAction(ActionEvent event) {
        txtItemCode.setText(null);
        txtItemType.setText(null);
        txtItemProfit.setText(null);
        txtItemStore.setText(null);
        txtItemUnitPrice.setText(null);
    }
}

