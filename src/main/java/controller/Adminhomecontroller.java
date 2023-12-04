package controller;

import Model.Order;
import com.example.myjava.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Adminhomecontroller implements Initializable {

    @FXML
    private Label totalincome;

    @FXML
    private Label numberOfCustomersLabel;

    @FXML
    private BarChart<String, Double> OrderChart;

    @FXML
    private LineChart<String, Double> IncomeChart;


    @FXML
    public void handleadminlogoutbutton(Event e) throws IOException {
        main me = new main();
        me.changeScene("LoginPage.fxml");
    }

    public void onCustomerbtn(Event e) throws IOException {
        main me = new main();
        me.changeScene("AdminCustomer.fxml");
    }

    public void onproductbtn(Event e) throws IOException {
        main me = new main();
        me.changeScene("AdminDashboard.fxml");
    }

    public void onOrderbtn(Event e) throws IOException {
        main me = new main();
        me.changeScene("AdminOrder.fxml");
    }

    public void onHomebtn(Event e) throws IOException {
        main me = new main();
        me.changeScene("AdminHome.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int numberOfCustomers = countCustomers();
            numberOfCustomersLabel.setText(String.valueOf(numberOfCustomers));
            updateTotalIncome();
            updateIncomeChart();
            updateOrderChart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int countCustomers() throws IOException {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            while (reader.readLine() != null) {
                count++;
            }
        }

        return count;
    }

    private void updateTotalIncome() {
        try (BufferedReader br = new BufferedReader(new FileReader("Allpurchased-details.txt"))) {
            String line;
            int Totalincome = 0;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Total Price: ")) {
                    Totalincome += Integer.parseInt(line.split(":")[1].trim());
                }
            }
            totalincome.setText("$" + Totalincome);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateIncomeChart() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Allpurchased-details.txt"));
            String line;
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName("Total Income");

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Total Price:")) {
                    String totalPrice = line.split(":")[1].trim();
                    double currentPrice = Double.parseDouble(totalPrice);

                    String category = series.getData().size() + "";
                    series.getData().add(new XYChart.Data<>(category, currentPrice));
                }
            }
            IncomeChart.getData().add(series);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void updateOrderChart() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Product.txt"));
            String line;
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName("Product Stocks");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String ProdName = parts[1];
                    String stock = parts[2];
                    // Parse the stock value as a string and convert it to double
                    series.getData().add(new XYChart.Data<>(ProdName, Double.parseDouble(stock)));
                }
            }

            OrderChart.getData().add(series);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}