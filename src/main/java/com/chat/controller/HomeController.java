package com.chat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController {
    public Stage homeStage;

    @FXML
    private MenuItem about;

    @FXML
    private Button about_btn;

    @FXML
    private Label app_name;

    @FXML
    private VBox body;

    @FXML
    private VBox chat_list;

    @FXML
    private Button close_btn;

    @FXML
    private ImageView d;

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
    private MenuItem join_new;

    @FXML
    private Button max_btn;

    @FXML
    private MenuBar menubar;

    @FXML
    private TextField message_field;

    @FXML
    private VBox message_list;

    @FXML
    private Button min_btn;

    @FXML
    private MenuItem new_chat;

    @FXML
    private MenuItem new_account;

    @FXML
    private MenuItem new_group;

    @FXML
    private MenuItem profile;

    @FXML
    private HBox search_box;

    @FXML
    private Button search_btn;

    @FXML
    private TextField search_field;

    @FXML
    private Button send_btn;

    @FXML
    private MenuItem setting;

    @FXML
    private VBox sidebar;

    @FXML
    private Label titlebar;

    @FXML
    private Label user_label;

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
    void createNewChat(ActionEvent event) {

    }

    @FXML
    void createNewGroup(ActionEvent event) {

    }

    @FXML
    void joinNewGroup(ActionEvent event) {

    }

    @FXML
    void createNewAccount(ActionEvent event) throws Exception {
        // NewAccountController newAccountController = new NewAccountController();
        // newAccountController.createNewAccount(event);
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
    void showAboutPage(ActionEvent event) {

    }

    @FXML
    void showPersonalProfile(ActionEvent event) {

    }

    @FXML
    void showSetting(ActionEvent event) {

    }

    public void showHomePage() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/chat/Home.fxml"));
        Scene scene = new Scene(root);
        homeStage.setScene(scene);
        homeStage.setTitle("chatchat");
        homeStage.show();
    }

}
