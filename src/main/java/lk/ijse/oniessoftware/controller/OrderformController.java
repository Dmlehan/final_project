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
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.oniessoftware.db.DBConnection;
import lk.ijse.oniessoftware.dto.CartDTO;
import lk.ijse.oniessoftware.dto.CustomerDTO;
import lk.ijse.oniessoftware.dto.ItemDTO;
import lk.ijse.oniessoftware.dto.OrdersDTO;
import lk.ijse.oniessoftware.model.CustomerModel;
import lk.ijse.oniessoftware.model.ItemsModel;
import lk.ijse.oniessoftware.model.OrdersModel;
import lk.ijse.oniessoftware.model.PlaceOrderModel;
import lk.ijse.oniessoftware.model.tm.PlaceOrderTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class OrderformController implements Initializable {
    private final static String URL = "jdbc:mysql://localhost:3306/Onies";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    @FXML
    private AnchorPane oderAnchor;

    @FXML
    private TableView<PlaceOrderTM> tblOrder;

    @FXML
    private TableColumn<?, ?> colOderId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colODate;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotalPrice;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private JFXTextField txtOderSearch;

    @FXML
    private JFXButton btnOrderAdd;

    @FXML
    private JFXButton btnOrderUpdate;

    @FXML
    private ImageView btnUpdate1;

    @FXML
    private JFXButton btnOrderDelete;

    @FXML
    private Pane oderPane;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtODate;

    @FXML
    private TextField txtOCustomerName;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private ComboBox<String> cmbCustId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private JFXButton btnOrderSearch;

    private ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCustIds();
        setItemId();
        setCellValueFactory();
        setOrderId();
        setOrderDate();
    }

    private void setOrderDate() {
        txtODate.setText(String.valueOf(LocalDate.now()));
    }

    private void setOrderId() {
        try {
            String nextId = OrdersModel.generateNextOrderId();
            txtOrderId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colOderId.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
        colODate.setCellValueFactory(new PropertyValueFactory<>("unit_price"));

    }

    private void setItemId() {
        try {
            List <String> ids = ItemsModel.getIds();

            cmbItemId.getItems().addAll(ids);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setCustIds() {
        try {
            List <String> ids = CustomerModel.getIds();

            cmbCustId.getItems().addAll(ids);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddtoCartOnACtion(ActionEvent event) {
        System.out.println(10);
        String code = cmbItemId.getSelectionModel().getSelectedItem();
        String description = txtItemName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double total = qty * unitPrice;
        Button btnRemove = new Button("Remove");
        btnRemove.setCursor(Cursor.HAND);

        setRemoveBtnOnAction(btnRemove); /* set action to the btnRemove */

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                if (colOderId.getCellData(i).equals(code)) {
                    qty += (int) colQty.getCellData(i);
                    total = qty * unitPrice;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal_price(total);

                    tblOrder.refresh();
                    /*calculateNetTotal();*/
                    return;
                }
            }
        }

        PlaceOrderTM tm = new PlaceOrderTM(code, description, unitPrice,qty, total, btnRemove);

        obList.add(tm);
        tblOrder.setItems(obList);
        /*calculateNetTotal();*/

        txtQty.setText("");
    }

    private void setRemoveBtnOnAction(Button btnRemove) {
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = tblOrder.getSelectionModel().getSelectedIndex();
                obList.remove(index);

               tblOrder.refresh();
                /*calculateNetTotal();*/
            }

        });
    }


    @FXML
    void btnOrderDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCUstIdOnAction(ActionEvent event) {
        String custId = cmbCustId.getSelectionModel().getSelectedItem();
        CustomerDTO customer = null;
        try {
            customer = CustomerModel.serchById(custId);
            txtOCustomerName.setText(customer.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void cmbItemIdOnAction(ActionEvent event) {
        String itemId = cmbItemId.getSelectionModel().getSelectedItem();
        ItemDTO item = null;
        try {
            item = ItemsModel.serchById(itemId);
            txtItemName.setText(item.getDescription());
            txtQtyOnHand.setText(String.valueOf(item.getStore()));
            txtUnitPrice.setText(String.valueOf(item.getUnit_price()));
            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnOrderAddOnAction(ActionEvent actionEvent) {
        String oId = txtOrderId.getText();
        String cusId = cmbCustId.getValue();

        List<CartDTO> cartDTOList = new ArrayList<>();

        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            PlaceOrderTM tm = obList.get(i);

            CartDTO cartDTO = new CartDTO(tm.getItem_id(), tm.getQty(),tm.getUnit_price());
            cartDTOList.add(cartDTO);
        }

        try {
            boolean isPlaced = PlaceOrderModel.placeOrder(oId, cusId, cartDTOList);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }


    }

    public void reportOnAction(ActionEvent actionEvent) {
        Thread t1 = new Thread(() -> {

            HashMap<String,Object> hm=new HashMap<>();
            hm.put("orderId",txtOrderId.getText());



            try {
                JasperDesign design = JRXmlLoader.load(new File("D:\\Final OOP project\\New folder\\final_project\\src\\main\\java\\lk\\ijse\\oniessoftware\\report\\order.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);
                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, hm, DBConnection.getInstance().getConnection());
//            JasperPrintManager.printReport(jasperPrint, true);

                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException | SQLException e) {
                e.printStackTrace();
            }
        });

        t1.start();

       /* try {
            JasperDesign design = JRXmlLoader.load(new File("D:\\Final OOP project\\New folder\\final_project\\src\\main\\java\\lk\\ijse\\oniessoftware\\report\\order.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
//            JasperPrintManager.printReport(jasperPrint, true);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();*/

    }
}

