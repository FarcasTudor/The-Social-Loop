package social_network;

import social_network.domain.Friendship;
import social_network.domain.User;
import social_network.domain.validator.UserValidator;
import social_network.repository.database.FriendshipDB;
import social_network.repository.database.UserDB;
import social_network.repository.memory.InMemoryRepository;
import social_network.service.ServiceUser;
import social_network.ui.UI;

public class Main {
    public static void main(String[] args) {
        //The Social Loop.
        //ByteChat
        UserValidator userValidator = new UserValidator();

        //UserFile userFileRepo = new UserFile("D:\\ANUL 2\\MAP\\SocialNetwork-RepoBD\\src\\Users.txt");
        //FriendshipFile friendshipFileRepo = new FriendshipFile("D:\\ANUL 2\\MAP\\SocialNetwork-RepoBD\\src\\Friendships.txt");
        String username="postgres";
        String password="postgres";
        String url="jdbc:postgresql://localhost:5432/postgres";

        InMemoryRepository<Long,User> userDB = new UserDB(url,username, password);
        InMemoryRepository<Long, Friendship> friendshipDB = new FriendshipDB(url,username, password);

        ServiceUser serviceUser = new ServiceUser(userDB, userValidator, friendshipDB);
        UI<Long, User> ui = new UI<>(serviceUser);
        ui.run();
    }
    /*
    <?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.Cursor?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.BorderPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<AnchorPane prefHeight="514.0" prefWidth="851.0" stylesheets="" xmlns="http://javafx.com/javafx/19" xmlns:fx="http://javafx.com/fxml/1" fx:controller="com.example.the_social_loop.MeniuController">
   <children>
      <BorderPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="551.0" prefWidth="851.0">
         <top>
            <AnchorPane prefHeight="46.0" prefWidth="853.0" BorderPane.alignment="CENTER">
               <children>
                  <Pane prefHeight="31.0" prefWidth="851.0" style="-fx-background-color: #3D0067;">
                     <children>
                        <Label alignment="CENTER" contentDisplay="CENTER" layoutX="346.0" layoutY="-1.0" prefHeight="31.0" prefWidth="160.0" text="~SOCIAL LOOP~" textFill="WHITE">
                           <font>
                              <Font name="Felix Titling" size="20.0" />
                           </font>
                        </Label>
                     </children></Pane>
                  <HBox layoutX="198.0" layoutY="31.0" prefHeight="45.0" prefWidth="654.0" AnchorPane.topAnchor="31.0" />
                  <Label fx:id="Menu" layoutX="7.0" layoutY="34.0" prefHeight="38.0" prefWidth="74.0" text="MENU" textAlignment="CENTER">
                     <font>
                        <Font size="14.0" />
                     </font>
                     <graphic>
                        <ImageView fitHeight="27.0" fitWidth="27.0">
                           <image>
                              <Image url="D:\ANUL 2\MAP\The Social Loop\src\main\img\meniu.png" />
                           </image>
                        </ImageView>
                     </graphic></Label>
                  <Label fx:id="MenuBack" layoutX="7.0" layoutY="34.0" prefHeight="38.0" prefWidth="74.0" text="MENU" textAlignment="CENTER">
                     <font>
                        <Font size="14.0" />
                     </font>
                     <graphic>
                        <ImageView fitHeight="27.0" fitWidth="27.0">
                           <image>
                              <Image url="D:\ANUL 2\MAP\The Social Loop\src\main\img\meniu.png" />
                           </image>
                        </ImageView>
                     </graphic>
                  </Label>
               </children>
            </AnchorPane>
         </top>
         <left>
            <AnchorPane fx:id="slider" BorderPane.alignment="CENTER">
               <children>
                  <VBox prefHeight="477.0" prefWidth="214.0" style="-fx-background-color: #A063E1;">
                     <children>
                        <Button mnemonicParsing="false" prefHeight="40.0" prefWidth="214.0" style="-fx-background-color: #A063E1;" styleClass="sidebarItem" text="Home" textFill="WHITE">
                           <font>
                              <Font name="System Bold" size="14.0" />
                           </font>
                           <cursor>
                              <Cursor fx:constant="CLOSED_HAND" />
                           </cursor>
                           <graphic>
                              <ImageView fitHeight="17.0" fitWidth="29.0" pickOnBounds="true" preserveRatio="true">
                                 <image>
                                    <Image url="D:\ANUL 2\MAP\The Social Loop\src\main\img\home.png" />
                                 </image>
                              </ImageView>
                           </graphic>
                           <VBox.margin>
                              <Insets right="40.0" />
                           </VBox.margin>
                        </Button>
                        <Button mnemonicParsing="false" prefHeight="40.0" prefWidth="267.0" style="-fx-background-color: #A063E1;" styleClass="sidebarItem" text="Search" textFill="WHITE">
                           <VBox.margin>
                              <Insets right="38.0" top="20.0" />
                           </VBox.margin>
                           <font>
                              <Font name="System Bold" size="14.0" />
                           </font>
                           <cursor>
                              <Cursor fx:constant="CLOSED_HAND" />
                           </cursor>
                           <graphic>
                              <ImageView fitHeight="27.0" fitWidth="30.0" pickOnBounds="true" preserveRatio="true">
                                 <image>
                                    <Image url="D:\ANUL 2\MAP\The Social Loop\src\main\img\search11.png" />
                                 </image>
                              </ImageView>
                           </graphic>
                        </Button>
                        <Button mnemonicParsing="false" prefHeight="40.0" prefWidth="227.0" style="-fx-background-color: #A063E1;" styleClass="sidebarItem" text="Friends" textFill="WHITE">
                           <VBox.margin>
                              <Insets right="33.0" top="20.0" />
                           </VBox.margin>
                           <font>
                              <Font name="System Bold" size="14.0" />
                           </font>
                           <cursor>
                              <Cursor fx:constant="CLOSED_HAND" />
                           </cursor>
                           <graphic>
                              <ImageView fitHeight="27.0" fitWidth="30.0" pickOnBounds="true" preserveRatio="true">
                                 <image>
                                    <Image url="D:\ANUL 2\MAP\The Social Loop\src\main\img\friends11.png" />
                                 </image>
                              </ImageView>
                           </graphic>
                        </Button>
                        <Button mnemonicParsing="false" prefHeight="40.0" prefWidth="230.0" style="-fx-background-color: #A063E1;" styleClass="sidebarItem" text="Notifications" textFill="WHITE">
                           <VBox.margin>
                              <Insets top="20.0" />
                           </VBox.margin>
                           <font>
                              <Font name="System Bold" size="14.0" />
                           </font>
                           <cursor>
                              <Cursor fx:constant="CLOSED_HAND" />
                           </cursor>
                           <graphic>
                              <ImageView fitHeight="27.0" fitWidth="30.0" pickOnBounds="true" preserveRatio="true">
                                 <image>
                                    <Image url="D:\ANUL 2\MAP\The Social Loop\src\main\img\notifications.png" />
                                 </image>
                              </ImageView>
                           </graphic>
                        </Button>
                        <Button alignment="BOTTOM_CENTER" contentDisplay="CENTER" mnemonicParsing="false" onAction="#backToLogIn" prefHeight="26.0" prefWidth="83.0" style="-fx-background-color: #3D0067;" text="Log out" textFill="WHITE">
                           <font>
                              <Font name="System Bold" size="12.0" />
                           </font>
                           <VBox.margin>
                              <Insets left="68.0" top="210.0" />
                           </VBox.margin>
                        </Button>
                     </children>
                  </VBox>
               </children>
            </AnchorPane>
         </left>
      </BorderPane>
   </children>
</AnchorPane>

     */

}