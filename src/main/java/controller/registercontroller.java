package controller;

import Model.User;
import Model.UserDataHandler;
import com.example.myjava.main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;


public class registercontroller {
    @FXML
    protected TextField fullnameField;

    @FXML
    protected TextField usernameField;

    @FXML
    protected TextField emailField;

    @FXML
    protected PasswordField passwordField;

    @FXML
    protected PasswordField confirmPasswordField;

    public void handleloginbutton2(Event e) throws IOException {
        main me = new main();
        me.changeScene("LoginPage.fxml");
    }

    public void handleCreateButton(Event e) throws IOException {
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords does not match!");
            return;
        }

        String username = usernameField.getText();

        // Check if the user with the given username already exists
        User existinguser = UserDataHandler.findUserbyUserName(username);

        if(existinguser != null){
            System.out.println("Username is already taken, please enter a different username.");
            return;
        }

        User newUser = new User();
        newUser.setFullname(fullnameField.getText());  // Set the fullname property
        newUser.setUsername(usernameField.getText());
        newUser.setEmail(emailField.getText());
        newUser.setPassword(password);

        UserDataHandler.saveUser(newUser);
        fullnameField.setText("");
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");


    }
}