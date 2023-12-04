package controller;
import Model.User;
import Model.UserDataHandler;
import com.example.myjava.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static Model.UserDataHandler.loadUsers;

public class adminCustomersController {
    private static final String FILE_PATH = "user.txt";

    @FXML
    private TableView<User> tableCustomersPage;

    @FXML
    private TableColumn<User, String> nameSurnameCo;

    @FXML
    private TableColumn<User, String> emailCo;

    @FXML
    private TableColumn<User, String> usernameCo;

    @FXML
    private TableColumn<User, Integer> orderCo;

    @FXML
    private Button deletebutton;

    @FXML
    private TextField txtusername;

    public void gobackbutton(Event e) throws IOException {
        main me = new main();
        me.changeScene("AdminDashboard.fxml");
    }

    public void searchonclick(Event e) throws IOException {
        String usernameToSearch = txtusername.getText();
        if (!usernameToSearch.isEmpty()) {
            searchuser(usernameToSearch);
        } else {
            ObservableList<User> users = FXCollections.observableArrayList(loadUsers());
            tableCustomersPage.setItems(users);
        }
        txtusername.setText("");
    }

    public void showAllUsers() throws IOException {
        ObservableList<User> users = FXCollections.observableArrayList(UserDataHandler.loadUsers());
        tableCustomersPage.setItems(users);
    }

    public void showuser(Event e) throws IOException{
        showAllUsers();
    }

    public void searchuser(String username) throws IOException {
        ObservableList<User> users = FXCollections.observableArrayList(loadUsers());
        ArrayList<User> search = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                search.add(user);
            }
        }
        tableCustomersPage.setItems(FXCollections.observableArrayList(search));
    }

    public void initialize(){
        nameSurnameCo.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        emailCo.setCellValueFactory(new PropertyValueFactory<>("email"));
        usernameCo.setCellValueFactory(new PropertyValueFactory<>("username"));
        orderCo.setCellValueFactory(new PropertyValueFactory<>("ordercount"));

        // Add event listener for row selection
        tableCustomersPage.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                deletebutton.setDisable(false);
            } else {
                deletebutton.setDisable(true);
            }
        });

        // Load user data and populate the table
        ObservableList<User> users = FXCollections.observableArrayList(UserDataHandler.loadUsers());
        tableCustomersPage.setItems(users);
    }

    @FXML
    public void deletebuttononaction(Event e) throws IOException{
        User selectedUser = tableCustomersPage.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Delete the user from the application's memory
            UserDataHandler.deleteUser(selectedUser);
            tableCustomersPage.getItems().remove(selectedUser);
            tableCustomersPage.refresh(); // Refresh the table view

            // Now, delete the user from the 'user.txt' file
            ObservableList<User> users = tableCustomersPage.getItems();
            UserDataHandler.saveUsers(users);
        }
    }

    public static void saveUsers(List<User> users) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                writer.println(u.getFullname() + "," + u.getUsername() + "," + u.getEmail() + "," + u.getPassword() + "," + u.getOrdercount());
            }
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateoncount(String username, int quantity) throws IOException {
        ObservableList<User> customers = tableCustomersPage.getItems();
        for (User customer : customers) {
            if (customer.getUsername().equals(username)) {
                int currentOrdercount = customer.getOrdercount();
                int newOrderCount = currentOrdercount + quantity;
                customer.setOrdercount(newOrderCount);
                break;
            }
        }
        tableCustomersPage.refresh();
        UserDataHandler.saveUsers(customers);
    }
}