package com.example.the_social_loop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import social_network.domain.Friendship;
import social_network.domain.User;
import social_network.domain.validator.UserValidator;
import social_network.exceptions.ValidationException;
import social_network.repository.database.FriendshipDB;
import social_network.repository.database.UserDB;
import social_network.repository.memory.InMemoryRepository;
import social_network.service.ServiceUser;
import social_network.service.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import social_network.service.ServiceUser;
public class LogIn  {

    private ServiceUser serviceUser;
    @FXML
    private Button button;

    @FXML
    private Label register;
    @FXML
    private Label wrongLogIn;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;


    public void setService(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }
    public ServiceUser getService() {
        return serviceUser;
    }
    public void userLogIn(ActionEvent event) throws IOException {
        checkLogin();
    }
    private void checkLogin() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        System.out.println(username);
        Iterable<User> users = serviceUser.findAll();
        if(username.isEmpty() || password.isEmpty()) {
            wrongLogIn.setText("Enter username and password!");
        }
        else {
            for(User user : users) {
                System.out.println(user.getUsername());
                if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    System.out.println(user);
                    wrongLogIn.setText("");
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("userWindow.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 851, 551);
                    MeniuController userWindow = fxmlLoader.getController();
                    userWindow.setLoggedUser(user);
                    userWindow.setService(serviceUser);
                    userWindow.initModel();
                    Stage stage = new Stage();
                    stage.setTitle("Social Loop");
                    stage.setScene(scene);
                    stage.show();
                    break;
                }
                else {
                    wrongLogIn.setText("Wrong username or password!");
                }
            }
        }
    }

    public void onRegisterButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) button.getScene().getWindow();
        Register registerController = fxmlLoader.getController();
        registerController.setService(this.serviceUser);
        stage.setTitle("Register");
        stage.setScene(scene);
    }
}
