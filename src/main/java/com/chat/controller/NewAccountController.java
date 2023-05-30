package com.chat.controller;

import com.chat.Model.Profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewAccountController {

    public static Stage stage;

    @FXML
    private Button close_btn;

    @FXML
    private TextField cpw_field;

    @FXML
    private Label error_label;

    @FXML
    private TextField id_field;

    @FXML
    private TextField name_field;

    @FXML
    private Button next_btn;

    @FXML
    private TextField port_field;

    @FXML
    private TextField pw_field;


    @FXML
    void closeStage(ActionEvent event) throws Exception {
        stage.close();
    }
    
    @FXML
    void getIn(ActionEvent event) throws Exception {
        VBox vbox = (VBox) next_btn.getParent();
        if(!isEmpty(vbox) && isValid(vbox) && isStrong(vbox) && isPasswordConfirmed(vbox)){
            Stage stage = (Stage) next_btn.getScene().getWindow();
            saveProfileData(vbox);
            stage.close();
            HomeController homeController = new HomeController(stage);
            homeController.showHomePage();
        } 
    }
    
    private boolean isValid(VBox vbox) {
        return true;
    }

    private boolean isStrong(VBox vbox) {
        TextField pass_field = (TextField) vbox.getChildren().get(4);
        if(pass_field.getText().length() < 5){
            error_label.setText("*Password length must be > 5");
            return false;
        }
        return true;
    }

    private boolean isEmpty(VBox vbox) {
        for(int j=1;j<6;j++){
            TextField field = (TextField) vbox.getChildren().get(j);
            if(field.getText().length() == 0) {
                error_label.setText("*You have Empty Fields!");
                return true;
            } 
            int x = field.getText().length();
            for(int i=0;i < field.getText().length();i++){
                if(field.getText().charAt(i) == ' '){
                    x = x - 1;
                }
            }
            if(x == 0){
                error_label.setText("*Using Only Spaces is not Valid Value!");
                return true;
            }
        }
        return false;
    }

    private boolean isPasswordConfirmed(VBox vbox) {
        TextField pass_field = (TextField) vbox.getChildren().get(4);
        TextField con_field = (TextField) vbox.getChildren().get(5);
        if(!pass_field.getText().equals(con_field.getText())){
            error_label.setText("Password is Uncormirmed!");
            return false;
        }
        return true;
    }

    private void saveProfileData(VBox vbox) {
        String username = ((TextField) vbox.getChildren().get(1)).getText();
        String ip = ((TextField) vbox.getChildren().get(2)).getText();
        String port = ((TextField) vbox.getChildren().get(3)).getText();
        String password = ((TextField) vbox.getChildren().get(4)).getText();
        
        Profile profile = new Profile(username, ip, port, password);
        profile.saveClientData();
    }
}

