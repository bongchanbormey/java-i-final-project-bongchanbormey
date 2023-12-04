package controller;

import Model.Order;
import Model.User;
import Model.UserDataHandler;
import com.example.myjava.main;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userOrderController  implements Initializable{

    @FXML
    private TableView<Order> SpecificUser;

    @FXML
    private TableColumn<Order, String> ProdID;

    @FXML
    private TableColumn<Order, String> ProdName;

    @FXML
    private TableColumn<Order, String> PricePaid;

    @FXML
    private TableColumn<Order, String> Quantity;

    @FXML
    private TableColumn<Order, String> TotalPrice;

    @FXML
    private TableColumn<Order, String> Customer;

    public void onbackbtn (Event e) throws IOException {
        main me = new main();
        me.changeScene("UserDashboard.fxml");
    }

    public void onlogoutbtn (Event e) throws IOException {
        main me = new main();
        me.changeScene("LoginPage.fxml");
    }

    private void loadOrderHistory() {
        User currentUser = logincontroller.getCurrentUser();
        if (currentUser != null) {
            String username = currentUser.getUsername();
            ObservableList<Order> orderHistory = UserDataHandler.loadOrderHistory(username);
            System.out.println("Loaded Order History: " + orderHistory); // Print for debugging
            SpecificUser.setItems(orderHistory);
        }
    }


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        initeCols();
        loadOrderHistory(); // Add this line

    }
    private void initeCols() {
        ProdID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        ProdName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        PricePaid.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        TotalPrice.setCellValueFactory(new PropertyValueFactory<>("productTotalprice"));
        Customer.setCellValueFactory(new PropertyValueFactory<>("username"));
    }
}