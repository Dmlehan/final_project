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
import lk.ijse.oniessoftware.dto.HarvestDTO;
import lk.ijse.oniessoftware.model.EmployeeModel;
import lk.ijse.oniessoftware.model.HarvestModel;
import lk.ijse.oniessoftware.util.Regex;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class HarvestformController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Onies";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user","root");
        props.setProperty("password", "1234");
    }

        @FXML
        private TableView<HarvestDTO> tableHarvest;

        @FXML
        private TableColumn<?, ?> colHarvestType;

        @FXML
        private TableColumn<?, ?> colHarvestCode;

        @FXML
        private TableColumn<?, ?> colQuantity;

        @FXML
        private TextField txtHarvestType;

        @FXML
        private TextField txtHarvestQuantity;

        @FXML
        private TextField txtHarvestCode;

        @FXML
        private JFXButton btnHarvestSearch;

        @FXML
        private JFXButton btnHarvestAdd;

        @FXML
        private JFXButton btnHarvestUpdate;

        @FXML
        private ImageView btnUpdate1;

        @FXML
        private JFXButton btnHarvestDelete;

        @FXML
        private JFXTextField txtHarvestSearch;
    ObservableList<HarvestDTO> data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValue();
        populateHarvestTable();
        searchFilter();

    }
    private void searchFilter() {
        FilteredList<HarvestDTO> filteredData = new FilteredList<>(data, b -> true);
        txtHarvestSearch.textProperty().addListener((observable, oldValue, newValue) ->{
            filteredData.setPredicate( HarvestDTO -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (HarvestDTO.getHarvestId().toLowerCase().contains(searchKeyword)){
                    return true;
                }else if(HarvestDTO.getHarvestType().toLowerCase().contains(searchKeyword)){
                    return true;
                }else
                    return false;

            });
        });

        SortedList<HarvestDTO> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableHarvest.comparatorProperty());
        tableHarvest.setItems(sortedData);
    }
    private void setCellValue() {
        colHarvestCode.setCellValueFactory(new PropertyValueFactory<>("harvestId"));
        colHarvestType.setCellValueFactory(new PropertyValueFactory<>("harvestType"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("harvestQuantity"));

    }
    private void populateHarvestTable() {
        try {
            ResultSet rs = HarvestModel.getAll();
            data.clear();
            while (rs.next()){
                data.add(new HarvestDTO(rs.getString(1),rs.getString(2),rs.getDouble(3)));
            }
            if (data != null){
                tableHarvest.setItems(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

        @FXML
        void btnHarvestAddOnAction(ActionEvent event) {
            String harvestCode = txtHarvestCode.getText();
            String harvestType = txtHarvestType.getText();
            Double quantity = Double.parseDouble(txtHarvestQuantity.getText());
            if(Regex.validateHarvestCID(harvestCode)){
            try {

                boolean isSaved = HarvestModel.save(harvestCode,harvestType,quantity);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Harvest saved!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
             }
            }else{ new Alert(Alert.AlertType.ERROR,"Invalid Input").show();

            }
        }

        @FXML
        void btnHarvestDeleteOnAction(ActionEvent event) {
            String harvestCode = txtHarvestCode.getText();
            try {
                boolean isDeleted = HarvestModel.delete(harvestCode);
                if (isDeleted){
                    new Alert(Alert.AlertType.CONFIRMATION,"Harvest deleted").show();
                    populateHarvestTable();
                    searchFilter();
                }else {
                    new Alert(Alert.AlertType.WARNING,"Harvest not deleted").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"SQL Error").show();
            }

        }

        @FXML
        void btnHarvestSearchOnAction(ActionEvent event) {
           String hCode = txtHarvestSearch.getText();
            try {
                HarvestDTO harvest = HarvestModel.search(hCode);
                if (harvest != null) {
                    txtHarvestCode.setText(harvest.getHarvestId());
                    txtHarvestType.setText(harvest.getHarvestType());
                    txtHarvestQuantity.setText(String.valueOf(harvest.getHarvestQuantity()));

                } else {
                    new Alert(Alert.AlertType.WARNING, "no customer found :(").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
            }

        }

        @FXML
        void btnHarvestUpdateOnAction(ActionEvent event){

            String harvestCode = txtHarvestCode.getText();
            String harvestType = txtHarvestType.getText();
            Double quantity = Double.parseDouble(txtHarvestQuantity.getText());
            if(Regex.validateHarvestCID(harvestCode)) {
              var harvest = new HarvestDTO(harvestCode,harvestType,quantity);
                try {
                    boolean isUpdated = HarvestModel.update(harvest);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "huree! Harvest Updated!").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
                    e.printStackTrace();
                }
            }else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
                }

        }


    public void tblHarvestOnMouseCLick(MouseEvent mouseEvent) {
        HarvestDTO selectItem=tableHarvest.getSelectionModel().getSelectedItem();
        if(selectItem==null) return;
        txtHarvestCode.setText(selectItem.getHarvestId());
        txtHarvestType.setText(selectItem.getHarvestType());
        txtHarvestQuantity.setText(String.valueOf(selectItem.getHarvestQuantity()));
    }

    public void btnHarvestClearOnAction(ActionEvent event) {
        txtHarvestCode.setText(null);
        txtHarvestType.setText(null);
        txtHarvestQuantity.setText(null);
    }
}


