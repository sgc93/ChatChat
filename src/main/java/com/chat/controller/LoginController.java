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
    private TextField con_pass_field;

    @FXML
    private Button login_btn;

    @FXML
    private TextField pass_field;

    @FXML
    private VBox root;

    @FXML
    void closeLoginStage(ActionEvent event) {
        Stage stage = (Stage) login_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void login(ActionEvent event) throws Exception {
        VBox vbox = (VBox) login_btn.getParent();
        TextField passfield = (TextField) vbox.getChildren().get(1);
        TextField confield = (TextField) vbox.getChildren().get(2);
        if(!isEmpty(passfield, confield) && isvalid(passfield) && isConfirmed(passfield, confield)){
            Stage stage = (Stage) login_btn.getScene().getWindow();
            stage.close();
            HomeController homeController = new HomeController(stage);
            homeController.showHomePage();
        }
    }

    private boolean isEmpty(TextField passfield, TextField confield) {
        if(passfield.getText().isBlank() || confield.getText().isBlank()){
            error_label.setText("You have Empyt Fields!");
            return true;
        }
        return false;
    }

    private boolean isConfirmed(TextField passfield, TextField confield) {
        if(passfield.getText().equals(confield.getText())){
            return true;
        }
        error_label.setText("Password is Not Confirmed!");
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

