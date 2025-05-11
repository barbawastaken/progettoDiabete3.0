package org.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        String url = "jdbc:sqlite:mydatabase.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            /*stmt.execute("CREATE TABLE IF NOT EXISTS utenti3 (id INTEGER PRIMARY KEY, nome TEXT, password TEXT)");
            stmt.execute("INSERT INTO utenti3 (id, nome, password) VALUES (1, 'Mario', 'mario')");*/       //modificato con commento
            ResultSet rs = stmt.executeQuery("SELECT * FROM utenti3");

            while (rs.next()) {
                System.out.println("id " + rs.getString("id") + " Utente: " + rs.getString("nome") + " Password: " + rs.getString("password"));
            }
        }


        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));        modificato con commento

        Group group = new Group();
        HBox hbox = new HBox();
        Scene scene = new Scene(hbox, 320, 240);

        Text usernameText = new Text("Username");
        usernameText.setX(70);
        usernameText.setY(70);
        usernameText.setFont(Font.font(16));
        group.getChildren().add(usernameText);

        TextField usernameField = new TextField();
        usernameField.setLayoutX(70);
        usernameField.setLayoutY(80);
        group.getChildren().add(usernameField);

        Text passwordText = new Text("Password");
        passwordText.setX(70);
        passwordText.setY(140);
        passwordText.setFont(Font.font(16));
        group.getChildren().add(passwordText);

        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(70);
        passwordField.setLayoutY(150);
        group.getChildren().add(passwordField);

        Button resetButton = new Button("Reset");
        resetButton.setLayoutX(70);
        resetButton.setLayoutY(210);
        resetButton.setOnAction(new EventHandler <ActionEvent>() {
            public void handle(ActionEvent buttonPressed){
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        group.getChildren().add(resetButton);

        Button accessButton = new Button("Accedi");
        accessButton.setLayoutX(160);
        accessButton.setLayoutY(210);
        accessButton.setOnAction(new EventHandler <ActionEvent>() {

            public void handle(ActionEvent buttonPressed){

                try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {

                    ResultSet accessSet = stmt.executeQuery("SELECT * FROM utenti3");

                    while(accessSet.next()){
                        if(accessSet.getString("nome").equals(usernameField.getText())){
                            if(accessSet.getString("password").equals(passwordField.getText())){

                                Stage stage2 = new Stage();
                                stage2.setScene(new Scene(new HBox(new Group()),stage.getWidth(), stage.getHeight()));
                                stage2.setTitle("Prova");
                                stage.close();
                                stage2.show();
                                stage2.alwaysOnTopProperty();
                                stage2.setMinHeight(320);
                                stage2.setMinWidth(240);


                            }
                        }
                    }

                    Text errorText = new Text("Username o password invalidi");
                    errorText.setFont(Font.font(14));
                    errorText.setX(60);
                    errorText.setY(40);
                    errorText.setFill(Color.RED);
                    group.getChildren().add(errorText);

                    usernameField.setText("");
                    passwordField.setText("");

                } catch (SQLException e) {
                    stage.close();
                    throw new RuntimeException(e);
                }

            }

        });
        group.getChildren().add(accessButton);

        hbox.alignmentProperty().set(Pos.CENTER);
        hbox.getChildren().add(group);

        stage.setMinHeight(320);
        stage.setMinWidth(240);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();

       /*
                -->     Comandi di prova per capire come chiudere una scheda e aprirne un'altra     <--
                -->     ci servirà  quando dalla pagina di accesso dovremmo passare alla homepage   <--
                -->     di utente o dottore                                                         <--

        Stage stage2 = new Stage();
        stage2.setScene(new Scene(new HBox(new Group()),320, 240));
        stage2.setTitle("Prova");
        stage.close();
        stage2.show();
        stage2.alwaysOnTopProperty()*/

    }

    public static void main(String[] args) {
        launch();
    }
}