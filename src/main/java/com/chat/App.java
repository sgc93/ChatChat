package com.chat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.chat.Model.Database;
import com.chat.controller.LoginController;
import com.chat.controller.NewAccountController;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        if(doesAccountExit()){
            LoginController loginController = new LoginController();
            loginController.showLoginPage(primaryStage);
        } else {
            NewAccountController newAccountController = new NewAccountController();
            newAccountController.showNewAccountPage(primaryStage);
        }
    }

    private boolean doesAccountExit() {
        String sql = "SELECT * FROM client";
        try(Statement st = Database.connect().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            if(! (rs.getString("username") == null)){
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Database Error!");
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
