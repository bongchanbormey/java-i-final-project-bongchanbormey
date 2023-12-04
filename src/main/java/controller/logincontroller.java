package controller;

import Model.User;
import Model.UserDataHandler;
import com.example.myjava.main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.List;

public class logincontroller {
    @FXML
    public Label wronglogin;

    @FXML
    public TextField usernamefield;

    @FXML
    public PasswordField passwordfield;

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }



    public void handleloginbutton(Event e) throws IOException {
        checkLogin();
    }

    public void checkLogin() throws IOException {
        main m = new main();
        String username = usernamefield.getText();
        String password = passwordfield.getText();

        List<User> users = UserDataHandler.loadUsers();
        if (users != null) {
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password) && user.isActive()) {
                    wronglogin.setText("Success - User Dashboard");
                    currentUser = user;
                    m.changeScene("UserDashboard.fxml");
                    return;
                }
            }
        }

        if (username.equals("admin") && password.equals("123")) {
            wronglogin.setText("Success - Admin Dashboard");
            // For the admin, you might not need to set the current user.
            // AppData.getInstance().setCurrentUser(null);
            m.changeScene("AdminDashboard.fxml");
        } else if (username.isEmpty() || password.isEmpty()) {
            wronglogin.setText("Please enter both username and password.");
        } else {
            wronglogin.setText("Wrong username or password");
        }
    }

    public void handleregisterbutton(Event e) throws IOException {
        main me = new main();
        me.changeScene("RegisterPage.fxml");

    }
}