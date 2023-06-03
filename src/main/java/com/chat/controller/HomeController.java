package com.chat.controller;

import java.beans.EventHandler;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import com.chat.Model.Database;
import com.chat.Model.network.ChatChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController {
    public ChatChatClient ChatClient;
    public Stage homeStage;

    @FXML
    private Button about_btn;

    @FXML
    private Label app_name;

    @FXML
    private VBox body;

    @FXML
    private Button close_btn;

    @FXML
    private HBox footer;

    @FXML
    private Button group_detail_btn;

    @FXML
    private HBox group_name_box;

    @FXML
    private Label group_name_label;

    @FXML
    private Label mem_num_label;

    @FXML
    private HBox header;

    @FXML
    private Button max_btn;

    @FXML
    private TextField message_field;

    @FXML
    private VBox message_vbox;

    @FXML
    private VBox chat_vbox;

    @FXML
    private Button min_btn;

    @FXML
    private HBox search_box;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_field;

    @FXML
    private Button send_btn;

    @FXML
    private VBox sidebar;

    @FXML
    private Label titlebar;

    @FXML
    private Label user_label;

    @FXML
    private VBox menu_box;

    @FXML
    private ScrollPane chat_box;

    @FXML
    private Button exit_btn;

    @FXML
    private Button menu_btn;

    public HomeController(Stage stage) {
        this.homeStage = stage;
    }

    public HomeController(){}

    @FXML
    void closeLoginStage(ActionEvent event) {
        Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void maximizeLoginStage(ActionEvent event) {
        Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.setMaximized(true);
    }

    @FXML
    void minimizeLoginPage(ActionEvent event) {
        Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void onMousePressed(MouseEvent event) {

    }

    // message from the server
    public void showMessage(String message){
        System.out.println("Message: " + message);
        Label messLabel = new Label(message);
        HBox messHBox = new HBox(messLabel);
        messHBox.setAlignment(Pos.CENTER_RIGHT);
        styleObjects(messLabel, messHBox);
        message_vbox.getChildren().add(messHBox);
    }

    @FXML
    void sendMessage(ActionEvent event) throws UnknownHostException, IOException {
        HBox messBox = (HBox) send_btn.getParent();
        TextField mField = (TextField) messBox.getChildren().get(1);
        String mess = mField.getText();

        Label messLabel = new Label(mess);
        HBox messHBox = new HBox(messLabel);

        messBox.setAlignment(Pos.CENTER_LEFT);
        styleObjects(messLabel, messHBox);
        message_vbox.getChildren().add(messHBox); 
        
        ChatClient.sendMessage(mess);
        mField.clear();
    }

    
    private void styleObjects(Label messLabel, HBox messHBox) {
        messLabel.getStyleClass().add("text-label");
        messLabel.getStylesheets().add(getClass().getResource("/com/chat/css/login.css").toExternalForm());
    }

    public void showHomePage() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/chat/Home.fxml"));
        Scene scene = new Scene(root);
        homeStage.setScene(scene);
        homeStage.setTitle("chatchat");
        homeStage.show();

        initialize();
    }

    public void initialize() {
        // Connect to the server
        String serverAddress = "localhost";
        int serverPort = 41621;

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            ChatClient = new ChatChatClient(socket, "Username");
            ChatClient.listenForMessage(this);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showMenu(ActionEvent event){
        chat_box.setVisible(false);
        menu_btn.setVisible(false);
        menu_box.setVisible(true);
    }

    @FXML
    void exitMenu(ActionEvent event){
        chat_box.setVisible(true);
        menu_btn.setVisible(true);
        menu_box.setVisible(false);
    }

}
