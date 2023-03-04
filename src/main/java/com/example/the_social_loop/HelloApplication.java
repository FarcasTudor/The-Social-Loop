package com.example.the_social_loop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import social_network.domain.Friendship;
import social_network.domain.User;
import social_network.domain.validator.UserValidator;
import social_network.repository.database.FriendshipDB;
import social_network.repository.database.UserDB;
import social_network.repository.memory.InMemoryRepository;
import social_network.service.ServiceUser;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        //background color: #282634
        //Red text color: #ff4057

        UserValidator userValidator = new UserValidator();
        String usernameDB="postgres";
        String passwordDB="postgres";
        String url="jdbc:postgresql://localhost:5432/postgres";
        InMemoryRepository<Long, User> userDB = new UserDB(url,usernameDB, passwordDB);
        InMemoryRepository<Long, Friendship> friendshipDB = new FriendshipDB(url,usernameDB, passwordDB);
        ServiceUser serviceUser = new ServiceUser(userDB, userValidator, friendshipDB);
        stg = stage;
        //stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("~Social Loop~");
        stage.setScene(scene);
        LogIn logIn = fxmlLoader.getController();
        logIn.setService(serviceUser);
        stage.show();
    }
    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch();
    }
}