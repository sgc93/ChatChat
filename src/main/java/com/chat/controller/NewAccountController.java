package com.chat.controller;

import java.io.IOException;

import com.chat.Model.Profile;
import com.chat.utilities.Password;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewAccountController {
    private Stage stage;
    @FXML
    private Button close_btn;

    @FXML
    private PasswordField cpw_field;

    @FXML
    private Label error_label;

    @FXML
    private SVGPath eye;

    @FXML
    private TextField name_field;

    @FXML
    private PasswordField pass_field;

    @FXML
    private Button show_btn;

    @FXML
    private Button sign_btn;

    public NewAccountController() {
    }

    @FXML
    void showPassword(ActionEvent event) {
        Password.showPassword(pass_field, eye);
    }

    @FXML
    void closeStage(ActionEvent event) throws Exception {
        stage.close();
    }
    
    @FXML
    void getIn(ActionEvent event) throws Exception {
        if(!isEmpty() && isValid() && isLengthen() && isPasswordConfirmed()){
            Stage stage = (Stage) sign_btn.getScene().getWindow();
            saveProfileData();
            stage.close();
            HomeController homeController = new HomeController(stage);
            homeController.showHomePage();
        } 
    }
    
    private boolean isValid() {
        if(name_field.getText().contains("\'") || name_field.getText().contains("\"") || pass_field.getText().contains("\'") || pass_field.getText().contains("\"" )){
            error_label.setText("*Using Quotation is not allowed!");
            return false;
        }
        return true;
    }

    private boolean isLengthen() {
        if(pass_field.getText().length() < 5){
            error_label.setText("*Password should have at least 5 characters.");
            return false;
        } else if(pass_field.getText().length() > 50){
            error_label.setText("*Password length must be less than 50");
        } else if(name_field.getText().length() > 20){
            error_label.setText("*Username length must be less than 20");
        }
        return true;
    }

    private boolean isEmpty() {
        if(name_field.getText().isBlank() || pass_field.getText().isBlank() || cpw_field.getText().isBlank()) {
            error_label.setText("*You have Empty Fields!");
            return true;
        }
        return false;
    }

    private boolean isPasswordConfirmed() {
        if(!pass_field.getText().equals(cpw_field.getText())){
            error_label.setText("Password is Uncormirmed!");
            return false;
        }
        return true;
    }

    private void saveProfileData() {
        Profile profile = new Profile(name_field.getText(), pass_field.getText());
        profile.saveClientData();
    }

    public void showNewAccountPage(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/chat/fxml/NewAccount.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

        initializeControls(root);
    }

    private void initializeControls(Parent root) {
        VBox parent = (VBox) root;
        HBox hbox = (HBox) parent.getChildren().get(1);
        VBox vbox = (VBox) hbox.getChildren().get(2);
        name_field = (TextField) vbox.getChildren().get(0);
        HBox  box = (HBox) vbox.getChildren().get(1);
        pass_field = (PasswordField) box.getChildren().get(0);
        cpw_field = (PasswordField) vbox.getChildren().get(2);
        name_field.requestFocus();
    }
}