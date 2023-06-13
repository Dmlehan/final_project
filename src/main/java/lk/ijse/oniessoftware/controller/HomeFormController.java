package lk.ijse.oniessoftware.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lk.ijse.oniessoftware.dto.CustomerDTO;
import lk.ijse.oniessoftware.model.*;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeFormController implements Initializable {


    public Label lblCustomerCount;
    @FXML
    private Label lblTime;

    @FXML
    private Label lblDate;
    public static String comDate;
    @FXML
    private Label lblEmployeeCount;
    @FXML
    private Label lblSupplyCount;

    @FXML
    private Label lbllItemCount;

    @FXML
    private BarChart<String, Integer> barchart;




    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));

            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            lblDate.setText(formatter2.format(date));
            comDate =lblDate .getText();
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    @SneakyThrows
    public void initialize(URL url, ResourceBundle resourceBundle){
        initClock();
        setItemCount();
        setSupplyCount();
        setCustomerCount();
        setEmployeeCount();
        setDeliveredCount();
        loadTypeChart();

      /*  XYChart.Series <String,Integer> series = new XYChart.Series();

        series  .setName("Onies");
        series .getData().add(new XYChart.Data("Customer",setCustomerCount()));
        series .getData().add(new XYChart.Data("Suppliers",setSupplyCount()));
        series .getData().add(new XYChart.Data("Employee",setEmployeeCount()));
        series .getData().add(new XYChart.Data("Items",setItemCount()));
        series .getData().add(new XYChart.Data("Orders",setOrdersCount()));
        series .getData().add(new XYChart.Data("Delivered",setDeliveredCount()));
        barchart.getData().addAll(series);

       /*for(Node n:barchart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill:   #8DD4F3;");
        }*/


    }
   public int setEmployeeCount() throws SQLException {
        ResultSet all = EmployeeModel.getAll();
        int count = 0;
        while(all.next()){
            count++;
        }
        return count;
    }


    public int setCustomerCount()throws SQLException {
        ResultSet all = CustomerModel.getAll();
        int count = 0;
        while(all.next()){
            count++;
        }
        return  count;
    }

    public int setSupplyCount()throws SQLException {
        ResultSet all = SupplierModel.getAll();
        int count = 0;
        while(all.next()){
            count++;
        }
        return count;
    }

    public int setItemCount()throws SQLException {
        ResultSet all = CustomerModel.getAll();
        int count = 0;
        while(all.next()){
            count++;
        }

        return count;
    }
    public int setOrdersCount()throws SQLException {
        ResultSet all = OrdersModel.getAll();
        int count = 0;
        while(all.next()){
            count++;
        }

        return count;
    }
    public int setDeliveredCount() throws SQLException {
        ResultSet all = DeliverModel.getAll();
        int count = 0;
        while(all.next()){
            count++;
        }
        return count;
    }

    private void loadTypeChart() throws SQLException {
        XYChart.Series<String, Integer>[] series1 = new XYChart.Series[6];

        series1[0] = new XYChart.Series<>();
        String type = "Employee";
        series1[0].getData().add(new XYChart.Data<>("", setEmployeeCount()));
        series1[0].setName(type);

        series1[1] = new XYChart.Series<>();
        String type1 = "Supplier";
        series1[1].getData().add(new XYChart.Data<>("", setSupplyCount()));
        series1[1].setName(type1);

        series1[2] = new XYChart.Series<>();
        String type2 = "Customer";
        series1[2].getData().add(new XYChart.Data<>("", setCustomerCount()));
        series1[2].setName(type2);

        series1[3] = new XYChart.Series<>();
        String type3 = "Item";
        series1[3].getData().add(new XYChart.Data<>("", setItemCount()));
        series1[3].setName(type3);

        series1[4] = new XYChart.Series<>();
        String type4 = "Orders";
        series1[4].getData().add(new XYChart.Data<>("", setOrdersCount()));
        series1[4].setName(type4);


        series1[5] = new XYChart.Series<>();
        String type5 = "Delivered";
        series1[5].getData().add(new XYChart.Data<>("", setDeliveredCount()));
        series1[5].setName(type5);
        barchart.getData().addAll(series1);


}}


