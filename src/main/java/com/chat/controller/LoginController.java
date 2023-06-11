package com.chat.controller;

import java.io.IOException;
import java.sql.*;

import com.chat.Model.Database;
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

public class LoginController {
    @FXML
    private Label error_label;

    @FXML
    private Button close_btn;

    @FXML
    private Button unlock_btn;

    @FXML
    private SVGPath eye;

    @FXML
    private PasswordField pass_field;
    
    @FXML
    private VBox root;
    
    @FXML
    void closeLoginStage(ActionEvent event) {
        Stage stage = (Stage) unlock_btn.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void unlock(ActionEvent event) throws Exception {
        VBox vbox = (VBox) unlock_btn.getParent();
        HBox box = (HBox) vbox.getChildren().get(3);
        TextField passfield = (TextField) box.getChildren().get(0);
        if(!isEmpty(passfield) && !isBlank(passfield) && isvalid(passfield)){
            Stage stage = (Stage) unlock_btn.getScene().getWindow();
            stage.close();
            HomeController homeController = new HomeController(stage);
            homeController.showHomePage();
        }
    }
    
    private boolean isEmpty(TextField passfield) {
        if(passfield.getText().isEmpty()){
            error_label.setText("Passcode field is Empty!");
            return true;
        }
        return false;
    }

    private boolean isBlank(TextField passfield) {
        if(passfield.getText().isBlank()){
            error_label.setText("Only Spaces cannot be passcode!");
            return true;
        }
        return false;
    }
    
    private boolean isvalid(TextField field) {
        String sql = "SELECT * FROM client";
        try(Statement st = Database.connect().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String password = rs.getString("password");
            if(field.getText().equals(password)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        error_label.setText("Invalid Password!");
        return false;
    }
    
    @FXML
    void showPassword(ActionEvent event) {
        Password.showPassword(pass_field, eye);
    }

    public void showLoginPage(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/chat/fxml/Login.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }
}