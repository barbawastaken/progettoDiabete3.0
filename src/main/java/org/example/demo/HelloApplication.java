package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        String url = "jdbc:sqlite:mydatabase.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS utenti (id INTEGER PRIMARY KEY, nome TEXT)");
            stmt.execute("INSERT INTO utenti (nome) VALUES ('Mario')");
            ResultSet rs = stmt.executeQuery("SELECT * FROM utenti");

            while (rs.next()) {
                System.out.println("Utente: " + rs.getString("nome"));
            }
        }


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}