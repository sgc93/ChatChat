package com.chat.controller;

import java.sql.*;

import com.chat.Model.Database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {
    public static Stage stage;

    @FXML
    private Label error_label;

    @FXML
    private Button close_btn;

    @FXML
    private Button unlock_btn;

    @FXML
    private TextField pass_field;

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
        TextField passfield = (TextField) vbox.getChildren().get(3);
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
}

