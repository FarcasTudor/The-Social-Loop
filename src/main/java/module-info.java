module com.example.the_social_loop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.the_social_loop to javafx.fxml;
    opens social_network.domain to javafx.base;

    exports social_network.domain;
    exports com.example.the_social_loop;
}