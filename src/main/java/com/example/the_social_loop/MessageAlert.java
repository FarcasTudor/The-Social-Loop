package com.example.the_social_loop;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageAlert {
    public static void ShowMessage(Stage owner, Alert.AlertType type, String header, String text) {
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.setTitle("Message");
        message.showAndWait();
    }

    public static void showErrorMessage(Stage owner, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(owner);
        alert.setTitle("Error");
        alert.setHeaderText(text);
        alert.showAndWait();
    }
}
