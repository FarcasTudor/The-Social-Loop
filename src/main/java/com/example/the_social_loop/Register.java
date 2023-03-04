package com.example.the_social_loop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import social_network.domain.User;
import social_network.exceptions.DuplicateException;
import social_network.service.ServiceUser;

import java.io.IOException;
import java.security.Provider;
import java.util.Collection;

public class Register {
    private ServiceUser serviceUser;

    public ServiceUser getService() {
        return serviceUser;
    }
    public void setService(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @FXML
    private Label register;
    @FXML
    private Label wrongSignUp;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField age;
    @FXML
    private TextField username;
    @FXML
    private Button registerButton;
    @FXML
    private PasswordField password;
    @FXML
    private Label backToLogin;

    public void checkRegistering() throws Exception {
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String age = this.age.getText();
        String username = this.username.getText();
        String password = this.password.getText();
        if(firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || username.isEmpty() || password.isEmpty()) {
            wrongSignUp.setText("Enter all the fields!");
        }
        else {
            try {
                Collection<User> users = (Collection<User>) serviceUser.findAll();
                Long id = Long.valueOf(users.size() + 1);
                serviceUser.saveUser(id, firstName,lastName,Integer.parseInt(age), username, password);
                wrongSignUp.setText("You have successfully registered!");
            } catch (Exception e) {
                wrongSignUp.setText(e.getMessage());
            }
        }
    }

    public void onLoginButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) registerButton.getScene().getWindow();
        LogIn registerController = fxmlLoader.getController();
        registerController.setService(this.serviceUser);
        stage.setScene(scene);
    }

}
