package com.chat.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.chat.Model.Database;
import com.chat.Model.network.ChatChatClient;
import com.chat.utilities.Client;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController {
    public ChatChatClient ChatClient;
    public String username;
    public String lastseen;
    public String pp_path;
    
    public Stage homeStage;
    private Scene scene;
    private double xOffset;
    private double yOffset;

    private Label nameLabel ;
    private Label replyLabel;
    private Button reply_btn;
    private HBox nameBox;
    private Label messLabel;
    private Label timeLabel;
    private VBox messVBox;

    private Image ppImg;
    private ImageView ppImgView;
    private Button pp_btn;

    private String reply_to;

    @FXML
    private Button attach_btn;

    @FXML
    private ScrollPane chatScroll_pane;

    @FXML
    private VBox chat_vbox;

    @FXML
    private Button close_btn;

    @FXML
    private Button exit_btn;

    @FXML
    private Button gDetail_btn;

    @FXML
    private HBox gName_box;

    @FXML
    private Label gName_label;

    @FXML
    private Label gPP_label;

    @FXML
    private Label glastMess_label;

    @FXML
    private Label gname_label;

    @FXML
    private Label gtime_label;

    @FXML
    private HBox header;

    @FXML
    private Label lastMess_label;

    @FXML
    private Label mNum_label;

    @FXML
    private Button max_btn;

    @FXML
    private VBox menu_box;

    @FXML
    private Button menu_btn;

    @FXML
    private ScrollPane messScroll_pane;

    @FXML
    private TextField message_field;

    @FXML
    private VBox message_vbox;

    @FXML
    private Button min_btn;

    @FXML
    private Label pp_label;
    @FXML
    private Label chatPP_label;

    @FXML
    private HBox search_box;

    @FXML
    private HBox chat_box;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_field;

    @FXML
    private Button send_btn;

    @FXML
    private Button setting_btn;

    @FXML
    private Label time_label;

    @FXML
    private Label titlebar;

    @FXML
    private Label user_name;

    public HomeController(Stage stage) {
        this.homeStage = stage;
    }

    public HomeController(){}

    @FXML
    void changeToLightMode(ActionEvent event) {
        Button btn = (Button) event.getSource();
        Scene scene = (Scene) btn.getScene();
        scene.getStylesheets().add(getClass().getResource("/com/chat/css/blackHome.css").toExternalForm());
    }

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

        nameLabel = new Label("Sgc");
        // if(reply_to != null){
        //     replyLabel = new Label("  >>> " +  reply_to);
        // } else {
        //     replyLabel = new Label();
        // }
        reply_btn = new Button("reply");
        reply_btn.setOnAction(eve -> {
            reply(eve, "other");
        });
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        nameBox = new HBox(reply_btn, spacer ,nameLabel);
        // nameBox.setPrefWidth(messLabel.getWidth());
        nameBox.setAlignment(Pos.CENTER_RIGHT);
        nameBox.setPadding(new Insets(0, 0, 0, 10));     

        messLabel = new Label(message);
        timeLabel = new Label(getNow());
        
        messVBox = new VBox(nameBox, messLabel, timeLabel);
        messVBox.setStyle("-fx-background-radius: 25px 25px 0 25px;");
        ppImg = new Image("/com/chat/icons/group.png");
        ppImgView = new ImageView(ppImg);
        pp_btn = new Button();
        pp_btn.setGraphic(ppImgView);

        HBox hbox = new HBox(messVBox, pp_btn);
        hbox.setSpacing(10);

        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        styleObjects(hbox);

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                message_vbox.getChildren().add(hbox);
            }
        });
    }

    void sendMessage(ActionEvent event) throws UnknownHostException, IOException {
        HBox messBox = (HBox) send_btn.getParent();
        TextField mField = (TextField) messBox.getChildren().get(1);
        String mess = mField.getText();

        nameLabel = new Label(username);
        System.out.println(nameLabel.getText());
        if(reply_to != null){
            replyLabel = new Label("  >>> " +  reply_to);
        } else {
            replyLabel = new Label();
        }
        reply_btn = new Button("reply");
        reply_btn.setOnAction(eve -> {
            reply(eve, "self");
        });
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        nameBox = new HBox(nameLabel, replyLabel,spacer, reply_btn);
        nameBox.setPrefWidth(nameLabel.getWidth());
        nameBox.setAlignment(Pos.CENTER_LEFT);
        nameBox.setPadding(new Insets(0, 10, 0, 0));     

        messLabel = new Label(mess);
        timeLabel = new Label(getNow());
        
        messVBox = new VBox(nameBox, messLabel, timeLabel);
        messVBox.setStyle("-fx-background-radius: 25px 25px 25px 0;");
        ppImg = new Image("/com/chat/icons/person.png");
        ppImgView = new ImageView(ppImg);
        pp_btn = new Button();
        pp_btn.setGraphic(ppImgView);

        HBox hbox = new HBox(pp_btn, messVBox);
        hbox.setSpacing(10);

        hbox.setAlignment(Pos.BOTTOM_LEFT);
        styleObjects(hbox);
        message_vbox.getChildren().add(hbox); 
        reply_to = null;
        ChatClient.sendMessage(mess);
        mField.clear();

    }

    
    private void reply(ActionEvent event, String forwho) {
        Button rbtn = (Button) event.getSource();
        HBox namehbox = (HBox) rbtn.getParent();
        System.out.println(namehbox.getChildren());
        Label uname = new Label();
        if(forwho.equals("self")){
            uname = (Label) namehbox.getChildren().get(0);
        }else if(forwho.equals("other")){
            uname = (Label) namehbox.getChildren().get(2);
        }
        reply_to = uname.getText();
        Timeline tm = new Timeline(new KeyFrame(Duration.seconds(0), new KeyValue(messScroll_pane.vvalueProperty(), 0.0)), new KeyFrame(Duration.seconds(2), new KeyValue(messScroll_pane.vvalueProperty(), 1.0)));
        tm.play();
    }

    private String getNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime now = LocalTime.now();
        return now.format(formatter);
    }

    private void styleObjects(HBox hbox) {   
        nameLabel.getStyleClass().add("name-label");
        replyLabel.getStyleClass().add("reply-label");
        reply_btn.setId("reply_btn");
        messLabel.getStyleClass().add("mess-label");
        timeLabel.getStyleClass().add("time-label");
        messVBox.getStyleClass().add("mess-box");
        messVBox.setAlignment(Pos.CENTER_RIGHT);
        pp_btn.setId("pp_btn");
        ppImgView.setId("pp_view");
        
        hbox.getStylesheets().add(getClass().getResource("/com/chat/css/styleDynamics.css").toExternalForm());
    }

    public void showHomePage() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/chat/fxml/Home.fxml"));
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/com/chat/css/login.css").toExternalForm());
        homeStage.setScene(scene);
        homeStage.setTitle("chatchat");
        homeStage.show();

        initializeView(root);
        makeMoveable();

        fetchUserData();
        reLoadMessages();
        initialize();
    }
    
    @FXML
    void showMenu(ActionEvent event) {
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

    
    private void initializeView(Parent root) {
        AnchorPane anchorPane = (AnchorPane) root;
        BorderPane borderPane = (BorderPane) anchorPane.getChildren().get(0);

        header = (HBox) borderPane.getTop();
        menu_btn = (Button) header.getChildren().get(0);

        AnchorPane left =(AnchorPane) borderPane.getLeft();
        chatScroll_pane = (ScrollPane) left.getChildren().get(1);
        menu_box = (VBox) left.getChildren().get(2);
        System.out.println(left.getChildren());

        AnchorPane center = (AnchorPane) borderPane.getCenter();
        VBox mainMess_box = (VBox) center.getChildren().get(0);
        
        messScroll_pane = (ScrollPane) mainMess_box.getChildren().get(1);
        message_vbox = (VBox) messScroll_pane.getContent();
        HBox messBox = (HBox) mainMess_box.getChildren().get(2);

        send_btn = (Button) messBox.getChildren().get(2);
        send_btn.setOnAction(event -> {
            try {
                sendMessage(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    private void makeMoveable(){
        header.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        header.setOnMouseDragged(e -> {
            homeStage.setX(e.getScreenX() - xOffset);
            homeStage.setY(e.getScreenY() - yOffset);
        });
    }

    private void fetchUserData() {
        String sql = "SELECT * FROM client WHERE current_acc = 1";
        ArrayList<Client> clients = Database.getClientData(sql);

        System.out.println(username);
        Client client = clients.get(0);
        username = client.getUserName();
        pp_path = client.getPpPath();
    }
    
    private void reLoadMessages(){
        
    }
    
    public void initialize() {
        // Connect to the server
        System.out.println(username);
        
        String serverAddress = "localhost";
        int serverPort = 41621;
        
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            ChatClient = new ChatChatClient(socket, username);
            ChatClient.listenForMessage(this);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
