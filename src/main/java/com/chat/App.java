package com.chat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.chat.Model.Database;
import com.chat.controller.HomeController;
import com.chat.controller.LoginController;
import com.chat.controller.NewAccountController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        if(doesAccountExit()){
            LoginController.stage = stage;
            Parent root = FXMLLoader.load(App.class.getResource("Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } else {
            NewAccountController.stage = stage;
            Parent root = FXMLLoader.load(App.class.getResource("NewAccount.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            // loginStage.setTitleBar();
        }

    }

    private boolean doesAccountExit() {
        String sql = "SELECT * FROM client";
        try(Statement st = Database.connect().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            if(!rs.getString("username").isEmpty()){
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
