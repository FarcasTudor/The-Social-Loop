package com.example.the_social_loop;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import social_network.domain.Friendship;
import social_network.domain.FriendshipStatus;
import social_network.domain.User;
import social_network.domain.UserDTO;
import social_network.service.Observer.Observable;
import social_network.service.ServiceUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MeniuController implements Initializable {

    HelloApplication main = new HelloApplication();
    private ServiceUser serviceUser;

    private final ObservableList<UserDTO> users = FXCollections.observableArrayList();

    private final ObservableList<UserDTO> friends = FXCollections.observableArrayList();
    private final ObservableList<UserDTO> requests = FXCollections.observableArrayList();

    @FXML
    private Pane paneFriendsTable;
    @FXML
    private Pane paneRequestsTable;

    @FXML
    private TableView<UserDTO> friendsTable;
    @FXML
    private TableView<UserDTO> requestsTable;

    @FXML
    private TableColumn<UserDTO, String> firstNameColumn;
    @FXML
    private TableColumn<UserDTO, String> lastNameColumn;
    @FXML
    private TableColumn<UserDTO, LocalDateTime> friendsFromColumn;

    @FXML
    private TableColumn<UserDTO, String> firstNameColumn1;
    @FXML
    private TableColumn<UserDTO, String> lastNameColumn1;
    @FXML
    private TableColumn<UserDTO, LocalDateTime> dateColumn1;
    private User user;
    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane slider;

    @FXML
    private StackPane stackPane;
    @FXML
    private String username;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Pane profilePane;

    @FXML
    private Button friendsButton;

    @FXML
    private Button deleteFriendButton;

    @FXML
    private Button addFriendButton;
    @FXML
    private TextField searchBar;

    @FXML
    private Button requestsButton;

    public void setService(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
        initModel();
    }
    public void initModel() {
        initializeTableFriends();
        initializeTableRequests();
        viewProfile();
    }
    public ServiceUser getService() {
        return serviceUser;
    }

    public void setLoggedUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slider.setTranslateX(-215);

        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-215);
            slide.setOnFinished(ActionEvent -> {
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });

            TranslateTransition slide1 = new TranslateTransition();
            slide1.setNode(stackPane);
            slide1.setDuration(Duration.seconds(0.4));
            slide1.setToX(0);
            slide1.play();
            stackPane.setTranslateX(-100);
        });

        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-215);
            slide.play();

            slider.setTranslateX(0);
            slide.setOnFinished(ActionEvent -> {
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });

            TranslateTransition slide2 = new TranslateTransition();
            slide2.setNode(stackPane);
            slide2.setDuration(Duration.seconds(0.4));
            slide2.setToX(-100);
            slide2.play();
            stackPane.setTranslateX(0);
        });
    }
    public void logOut() throws IOException {
        Stage stage = (Stage) slider.getScene().getWindow();
        stage.close();
    }

    public void setUsername(String username) {

        this.username = username;
        User user = serviceUser.findUserByUsername(username);
        userNameLabel.setText(username);
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
    }
    public void viewProfile() {
        profilePane.toFront();
        userNameLabel.setText(user.getUsername());
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
    }


    public void getFriendList() {
        Iterable<Friendship> friendships = serviceUser.findAllFriendships();
        for (Friendship friendship : friendships) {
           // System.out.println(friendship.getId());
            if (friendship.getId1().equals(user.getId())) {
                switch (friendship.getStatus()) {
                    case ACCEPTED ->
                            friends.add(new UserDTO(friendship.getId(),
                                    serviceUser.findOne(friendship.getId2()).getFirstName(),
                                    serviceUser.findOne(friendship.getId2()).getLastName(),
                                    friendship.getDate().toString()));
                }
            } else if (friendship.getId2().equals(user.getId())) {
                switch (friendship.getStatus()){
                    case ACCEPTED ->
                            friends.add(new UserDTO(friendship.getId(),
                                    serviceUser.findOne(friendship.getId1()).getFirstName(),
                                    serviceUser.findOne(friendship.getId1()).getLastName(),
                                    friendship.getDate().toString()));
                    case PENDING ->
                            requests.add(new UserDTO(friendship.getId(),
                                    serviceUser.findOne(friendship.getId1()).getFirstName(),
                                    serviceUser.findOne(friendship.getId1()).getLastName(),
                                    friendship.getDate().toString()));
                }
            }
        }
    }

    public void initializeTableFriends() {
        paneFriendsTable.toFront();
        friends.clear();
        getFriendList();
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        friendsFromColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        friendsTable.setItems(friends);
    }

    public void initializeTableRequests() {
        paneRequestsTable.toFront();
        requests.clear();
        getFriendList();
        firstNameColumn1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn1.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateColumn1.setCellValueFactory(new PropertyValueFactory<>("date"));
        requestsTable.setItems(requests);
        /*searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchTerm = newValue.toLowerCase();
            ObservableList<UserDTO> searchResults= FXCollections.observableArrayList();
            for (UserDTO userDTO : us) {

            }
        });*/
    }

    public void addFriend() throws FileNotFoundException {
        String username = searchBar.getText();
        Iterable<User> users = serviceUser.findAll();
        int id = 0;
        for(Friendship f : serviceUser.findAllFriendships()){
            if(f.getId() > id)
                id = f.getId().intValue();
        }
        id = id + 1;
        AtomicBoolean found = new AtomicBoolean(false);
        for(User u : users) {
            if(u.getUsername().equals(username)) {
                serviceUser.saveFriend(this.user.getId(), u.getId());
                found.set(true);
            }
        }
        if(!found.get())
            MessageAlert.showErrorMessage(null, "User not found!");
        friends.clear();
        //requests.clear();
        initializeTableFriends();

    }

    public void deleteFriend() {
        UserDTO userDTO = friendsTable.getSelectionModel().getSelectedItem();
        if (userDTO != null) {
            try {
                Friendship friendship = serviceUser.findOneFriendship(userDTO.getId());
                serviceUser.deleteFriend(friendship.getId1(), friendship.getId2());
                initializeTableFriends();
            } catch (Exception e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        } else {
            MessageAlert.showErrorMessage(null, "Please select a friend!");
        }
    }

    public void onAcceptFriendRequestButtonClick() {
        try {
            UserDTO userDTO = requestsTable.getSelectionModel().getSelectedItem();
            //Friendship friendship = serviceUser.findOneFriendship(userDTO.getId());
            for (Friendship f : getAllFriendships()) {
                if (f.getId().equals(userDTO.getId())) {
                    System.out.println(f.getId()+ " " + f.getId1() + " " + f.getId2());
                    serviceUser.updateFriendshipStatus(f);
                    break;
                }
            }

            friends.clear();
            requests.clear();
            initializeTableFriends();
            initializeTableRequests();
            getFriendList();
        } catch(Exception e) {
            MessageAlert.showErrorMessage(null, "Please select a request!");
        }


    }

    public List<Friendship> getAllFriendships(){
        return serviceUser.getFriendships();
    }




}
