package com.chat.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.chat.Model.Database;
import com.chat.Model.network.ChatChatClient;
import com.chat.utilities.Client;
import com.chat.utilities.Password;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController {
    public ChatChatClient ChatClient;
    public String username = "Sewlesew";
    public String lastseen;
    private InputStream ppInputStream;
    private File ppFile;
    
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
    private ImageView pp;


    private String reply_to;

    private ArrayList<String> usernames = new ArrayList<>();

    @FXML
    private VBox mainMess_box;

    @FXML
    private SVGPath accSvg_path;

    @FXML
    private VBox acc_box;

    @FXML
    private Button acc_btn;

    @FXML
    private VBox acc_list;

    @FXML
    private Button addAcc_btn;

    @FXML
    private VBox ask_box;

    @FXML
    private TextArea ask_field;

    @FXML
    private Button attach_btn;

    @FXML
    private Button changePass_btn;

    @FXML
    private Button change_btn;

    @FXML
    private Label chatPP_label;

    @FXML
    private ScrollPane chatScroll_pane;

    @FXML
    private HBox self_chat;

    @FXML
    private HBox group_chat;

    @FXML
    private VBox selfChat_box;

    @FXML
    private VBox chat_vbox;

    @FXML
    private Button closeLeft_btn;

    @FXML
    private Button close_btn;

    @FXML
    private PasswordField cnpass_field;

    @FXML
    private Button down_btn;

    @FXML
    private Button editPP_btn;

    @FXML
    private Button exit_btn;

    @FXML
    private ScrollPane gDetail_box;

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
    private SVGPath mode_svg;

    @FXML
    private AnchorPane newMess_box;

    @FXML
    private Label newMess_label;

    @FXML
    private PasswordField newPass_field;

    @FXML
    private Label note_label;

    @FXML
    private PasswordField oldPass_field;

    @FXML
    private ImageView pImg;

    @FXML
    private VBox password_box;

    @FXML
    private ImageView pp_img;

    @FXML
    private Label pp_label;

    @FXML
    private VBox profile_box;

    @FXML
    private Label psNote_label;

    @FXML
    private Button savePPchanges_btn;

    @FXML
    private HBox search_box;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_field;

    @FXML
    private Button sendQ_btn;

    @FXML
    private Button send_btn;

    @FXML
    private VBox setting_box;

    @FXML
    private Button setting_btn;

    @FXML
    private Button ask_btn;

    @FXML
    private Button showCNPass;

    @FXML
    private Button showNewPass;

    @FXML
    private Button showOldPass;

    @FXML
    private Button theme_btn;

    @FXML
    private Label time_label;

    @FXML
    private Label titlebar;

    @FXML
    private Label uname_label;

    @FXML
    private Label user_name;

    @FXML
    private Label checker;

    @FXML
    private Label gChecker;

    @FXML
    private TextField username_field;

    public boolean isDark = true;
    private boolean isGDetailDisplayed = false;

    public HomeController(Stage stage) {
        this.homeStage = stage;
    }
    
    public HomeController(){}

    @FXML
    private void showGroupDetail(ActionEvent event){
        if(isGDetailDisplayed){
            gDetail_box.setVisible(false);
            isGDetailDisplayed = false;
        } else {
            gDetail_box.setVisible(true);
            isGDetailDisplayed = true;
        }
    }

    @FXML
    private void showGDetail(MouseEvent event){
        if(isGDetailDisplayed){
            gDetail_box.setVisible(false);
            isGDetailDisplayed = false;
        } else {
            gDetail_box.setVisible(true);
            isGDetailDisplayed = true;
        }
    }
    @FXML
    void showAskBox(ActionEvent event){
        Button btn = (Button) event.getSource();
        ask_box.setVisible(true);
        setCheck(btn);
    }

    @FXML
    void openSetting(ActionEvent event){
        Button btn = (Button) event.getSource();
        setting_box.setVisible(true);
        setCheck(btn);
    }

    @FXML
    void closePasswordBox(MouseEvent evetn){
        password_box.setVisible(false);
    }

    @FXML
    void ShowOldPassword(ActionEvent event) {
        Button btn = (Button) (event.getSource());
        SVGPath eye = (SVGPath) btn.getGraphic();
        Password.showPassword(oldPass_field, eye);
    }


    @FXML
    void showConfirmedPass(ActionEvent event) {
        Button btn = (Button) (event.getSource());
        SVGPath eye = (SVGPath) btn.getGraphic();
        Password.showPassword(cnpass_field, eye);
    }

    @FXML
    void showNewPass(ActionEvent event) {
        Button btn = (Button) (event.getSource());
        SVGPath eye = (SVGPath) btn.getGraphic();
        Password.showPassword(newPass_field, eye);
    }
    
    private void setCheck(Button btn) {
        setUnCheck(btn);
        btn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.22);");
    }

    private void setUnCheck(Button btn){
        for(int i=3; i<7;i++){
            Button button = (Button) menu_box.getChildren().get(i);
            if(button == btn){
                continue;
            } else {
                button.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0605)");
            }
        }
    }

    @FXML
    void changePassword(ActionEvent event) {
        Button btn = (Button) event.getSource();
        password_box.setVisible(true);
        setCheck(btn);
    }

    @FXML
    void changeThemeColor(ActionEvent event) {
        String toggle_on = "M17,7H7A5,5,0,0,0,7,17H17A5,5,0,0,0,17,7Zm0,8a3,3,0,1,1,3-3A3,3,0,0,1,17,15Z";
        String toggle_off = "M17,7H7A5,5,0,0,0,7,17H17A5,5,0,0,0,17,7ZM7,15a3,3,0,1,1,3-3A3,3,0,0,1,7,15Z";
        Button btn = (Button) event.getSource();
        Scene scene = (Scene) btn.getScene();
        mode_svg = (SVGPath) btn.getGraphic();
        if(isDark == true){
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/com/chat/css/blackHome.css").toExternalForm());
            mode_svg.setContent(toggle_off);
            isDark = false;
        } else {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/com/chat/css/home.css").toExternalForm());
            mode_svg.setContent(toggle_on);
            isDark = true;
        }
    }

    @FXML
    void closeGroupDetail(ActionEvent event) {
        gDetail_box.setVisible(false);
    }

    @FXML
    void saveProfileChanges(ActionEvent event) {

    }

    @FXML
    void sendQuestion(ActionEvent event) {

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

    @FXML
    void showSelfChat(MouseEvent eve){
        gChecker.setVisible(false);
        checker.setVisible(true);
        mainMess_box.setVisible(false);
        selfChat_box.setVisible(true);
    }

    @FXML
    void showGroupChat(MouseEvent eve){
        gChecker.setVisible(true);
        checker.setVisible(false);
        selfChat_box.setVisible(false);
        mainMess_box.setVisible(true);
    }

    // message from the server
    public void showMessage(String message){
        // String delimiter = ":";
        // String[] parts = message.split(delimiter);
        // String uname = parts[0];
        // String mess = parts[1];

        nameLabel = new Label("jedi");
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
        Image Img = new Image("/com/chat/icons/group.png");
        pp = new ImageView(Img);
        pp_btn = new Button();

        HBox hbox = new HBox(messVBox, pp);
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
        pp_btn = new Button();
        
        
        Image img = ppImgView.getImage();
        ImageView imgview = new ImageView(img);
        imgview.setFitHeight(50);
        imgview.setFitWidth(50);
        
        pp_btn.setGraphic(imgview);
        HBox hbox = new HBox(pp_btn, messVBox);
        hbox.setSpacing(10);
        
        hbox.setAlignment(Pos.BOTTOM_LEFT);
        hbox.setPadding(new Insets(0, 0, 0, 10));
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
        
        hbox.getStylesheets().add(getClass().getResource("/com/chat/css/styleDynamics.css").toExternalForm());
    }

    public void showHomePage() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/chat/fxml/Home.fxml"));
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/chat/css/home.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        homeStage.setScene(scene);
        homeStage.setTitle("chatchat");
        homeStage.show();

        initializeView(root);
        makeMoveable();

        fetchUserData();
        initialize();
        reLoadMessages();
    }
    
    @FXML
    void showMenu(ActionEvent event) {
        self_chat.setVisible(false);
        menu_btn.setVisible(false);
        menu_box.setVisible(true);
        uname_label.setText(username);
    }

    @FXML
    void exitMenu(ActionEvent event){
        self_chat.setVisible(true);
        menu_btn.setVisible(true);
        menu_box.setVisible(false);
        acc_box.setVisible(false);
        profile_box.setVisible(false);
        setting_box.setVisible(false);
        password_box.setVisible(false);
        ask_box.setVisible(false);
        Button btn = new Button();
        setUnCheck(btn);
    }

    @FXML
    void closeSideBox(MouseEvent event) {
        acc_box.setVisible(false);
        profile_box.setVisible(false);
        setting_box.setVisible(false);
        password_box.setVisible(false);
        ask_box.setVisible(false);
        Button btn = new Button();
        setUnCheck(btn);
    }

    
    private void initializeView(Parent root) {
        AnchorPane anchorPane = (AnchorPane) root;
        BorderPane borderPane = (BorderPane) anchorPane.getChildren().get(0);
        gDetail_box = (ScrollPane) anchorPane.getChildren().get(1);

        header = (HBox) borderPane.getTop();
        menu_btn = (Button) header.getChildren().get(0);

        AnchorPane left =(AnchorPane) borderPane.getLeft();
        chatScroll_pane = (ScrollPane) left.getChildren().get(1);
        chat_vbox = (VBox) chatScroll_pane.getContent();
        self_chat = (HBox) chat_vbox.getChildren().get(0);
        checker = (Label) self_chat.getChildren().get(0);
        
        group_chat = (HBox) chat_vbox.getChildren().get(1);
        gChecker = (Label) self_chat.getChildren().get(0);

        menu_box = (VBox) left.getChildren().get(2);
        uname_label = (Label) menu_box.getChildren().get(2);
        pImg = (ImageView) menu_box.getChildren().get(1);
        pp_btn = (Button) menu_box.getChildren().get(4);
        pp_btn.setOnAction(e -> {
            editProfile();
        });

        AnchorPane center = (AnchorPane) borderPane.getCenter();
        mainMess_box = (VBox) center.getChildren().get(0);
        profile_box = (VBox) center.getChildren().get(2);
        HBox bbox = (HBox) profile_box.getChildren().get(1);
        note_label = (Label) profile_box.getChildren().get(0);
        pp_img = (ImageView) bbox.getChildren().get(0);
        change_btn = (Button) bbox.getChildren().get(1);
        change_btn.setOnAction(event -> {
            try {
                ppChooser();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        username_field = (TextField) profile_box.getChildren().get(2);
        savePPchanges_btn = (Button) profile_box.getChildren().get(4);
        savePPchanges_btn.setOnAction(event -> {
            changeProfile();
        });

        password_box = (VBox) center.getChildren().get(4);
        psNote_label = (Label) password_box.getChildren().get(0);
        HBox Phbox1 = (HBox) password_box.getChildren().get(1);
        oldPass_field = (PasswordField) Phbox1.getChildren().get(0);
        showOldPass = (Button) Phbox1.getChildren().get(1);
        HBox Phbox2 = (HBox) password_box.getChildren().get(2);
        newPass_field = (PasswordField) Phbox2.getChildren().get(0);
        showNewPass = (Button) Phbox2.getChildren().get(1);
        HBox Phbox3 = (HBox) password_box.getChildren().get(3);
        cnpass_field = (PasswordField) Phbox3.getChildren().get(0);
        showCNPass = (Button) Phbox3.getChildren().get(1);
        changePass_btn = (Button) password_box.getChildren().get(4);
        changePass_btn.setOnAction(passEvent -> {
            changePassword();
        });

        ask_box = (VBox) center.getChildren().get(6);
        
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

        selfChat_box = (VBox) center.getChildren().get(7);

        menu_btn.setOnAction(ev -> {
            showMenuBox(ev);
        });
    }

    private void showMenuBox(ActionEvent ev) {
        // chat_box.setVisible(false);
        menu_btn.setVisible(false);
        menu_box.setVisible(true);
        uname_label.setText(username);
        pImg.setImage(ppImg);
        System.out.println("Username1: " + username);
        System.out.println("Usermane2: " + uname_label.getText());
    }

    void editProfile(){
        setCheck(pp_btn);
        profile_box.setVisible(true);
        pp_img.setImage(ppImg);
        username_field.setText(username);
        System.out.println("Username: " + username);
    }

    public void ppChooser() throws FileNotFoundException {
        try {
            FileChooser fileChoser = new FileChooser();
            fileChoser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            ppFile = fileChoser.showOpenDialog(null);
            FileInputStream fileInputStream = new FileInputStream(ppFile);
            ppInputStream = fileInputStream;
            if (ppFile != null) {
                ppImg = new Image(ppFile.toURI().toString());
                ppImgView = new ImageView(ppImg);
                pp_img.setImage(ppImg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeProfile() {
        String note1 = "Profile Updating ...";
        String new_username = username_field.getText();
        if(ppInputStream != null){
            try {
                username = new_username;
                byte[] ppImgData = ppInputStream.readAllBytes();
                Database.changeProfileInDatabase(ppImgData, username);
                String note2 = note1 + "\n" + "    Profile Updated successfully.";
                setNote(note1, note2, note_label);
                pImg.setImage(ppImg);
                uname_label.setText(username);
                ppInputStream = null;
            } catch (Exception e) {
                setNote(note1, note1 + "\n" + "    Something is off!, profile is note updated!", note_label);
            }
        } else if(!username.equals(new_username)){
            Database.changeUsername(username);
            String note2 = note1 + "\n" + "    Username Updated successfully.";
            setNote(note1, note2, note_label);
            uname_label.setText(username);
        } else {
            setNote(note1, note1 + "\n" + "    You have no chage to update!", note_label);
        }
    }

    private void changePassword() {
        String note1 = "Password updating ...";
        if( !isEmpty(note1) && isStrong(note1) && isConfirmed(note1) && isValid(note1)){
            Database.setNewPassword(newPass_field.getText());
            setNote(note1, note1 + "\n Password Chaged Successfully!", psNote_label);
            oldPass_field.clear();
            newPass_field.clear();
            cnpass_field.clear();
        }
    }

    private boolean isValid(String note) {
        String sql = "SELECT password FROM client";
        try (
            Connection con = Database.connect();
            Statement st = con.createStatement()){
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                if(!oldPass_field.getText().equals(rs.getString("password"))){
                    setNote(note, note + "\n    Old password is not correct!", psNote_label);
                    return false;
                } else if(oldPass_field.getText().equals(newPass_field.getText())){
                    setNote(note, note + "\n    New and old password are the same!", psNote_label);
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            setNote(note, note + "\n    Database: Unable to get the old password!", psNote_label);
            return false;
        }
        return false;
    }

    private boolean isConfirmed(String note) {
        if(newPass_field.getText().equals(cnpass_field.getText())){
            return true;
        }
        setNote(note, note + "\n    Confirm your new password Correctly!", psNote_label);
        return false;
    }

    private boolean isStrong(String note) {
        if(newPass_field.getText().length() > 5){
            return true;
        }
        setNote(note, note + "\n    Your password must have at least 5 charachters!", psNote_label);
        return false;
    }

    private boolean isEmpty(String note) {
        if(newPass_field.getText().isBlank() || oldPass_field.getText().isBlank() || cnpass_field.getText().isBlank() ){
            setNote(note, note + "\n    You have empty field!", psNote_label);
            return true;
        }
        return false;
    }

    private void setNote(String note1, String note2, Label noteLabel){
        Timeline noteTl = new Timeline(
            new KeyFrame(Duration.seconds(0.0), e -> noteLabel.setText(note2)),
            new KeyFrame(Duration.seconds(2.0), e -> noteLabel.setText(note1))
        );
        noteTl.setCycleCount(1);
        noteTl.play();
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
        Client client = Database.getClientData(sql);
        username = client.getUserName();
        System.out.println("Username1: " + username);
        InputStream st = Database.getImageStreamByUsername(username);
        // pp_path = client.getPpPath();

        ppImg = new Image(st);
        ppImgView = new ImageView(ppImg);
    }
    
    private void reLoadMessages(){
        
    }
    
    public void initialize() {
    String serverAddress = "localhost";
    int serverPort = 41621;
    try {
        Socket socket = new Socket(serverAddress, serverPort);
        ChatClient = new ChatChatClient(socket, username);
        // Send the username to the server
        ChatClient.listenForMessage(this);
        ChatClient.sendUsernameToServer(username);
    } catch (UnknownHostException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
