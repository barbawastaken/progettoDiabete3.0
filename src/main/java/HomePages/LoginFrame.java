package HomePages;

import controller.DiabetologoController;
import controller.PazienteController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.Stage;
import model.DiabetologoModel;
import model.PazienteModel;
import view.DiabetologoView;
import view.PazienteView;

import java.io.IOException;
import java.sql.*;

public class LoginFrame extends Application {

    @Override
    public void start(Stage Loginstage) throws IOException, SQLException {
        String url = "jdbc:sqlite:mydatabase.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            /*stmt.execute("CREATE TABLE IF NOT EXISTS utenti3 (id INTEGER PRIMARY KEY, nome TEXT, password TEXT)");
            stmt.execute("INSERT INTO utenti3 (id, nome, password) VALUES (1, 'Mario', 'mario')");*/       //modificato con commento
            ResultSet rs = stmt.executeQuery("SELECT * FROM loginTable");

            while (rs.next()) {
                System.out.println("username: " + rs.getString("username") + "\t\t\t password: " + rs.getString("password") + "\t\t\t userType: " + rs.getString("userType"));
            }
        }


        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));      eliminato hello-view.fxml  modificato con commento

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
        resetButton.setOnAction(buttonPressed -> {
            usernameField.setText("");
            passwordField.setText("");
        });
        group.getChildren().add(resetButton);

        Button accessButton = new Button("Accedi");
        accessButton.setLayoutX(160);
        accessButton.setLayoutY(210);
        accessButton.setOnAction(new EventHandler <>() {

            public void handle(ActionEvent buttonPressed){

                try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {

                    ResultSet accessSet = stmt.executeQuery("SELECT * FROM loginTable");

                    while(accessSet.next()){
                        if(accessSet.getString("username").equals(usernameField.getText())){
                            if(accessSet.getString("password").equals(passwordField.getText())){

                                if(accessSet.getString("userType").equals("PAZIENTE")){

                                    PazienteModel model = new PazienteModel();
                                    PazienteView view = new PazienteView();
                                    new PazienteController(model, view, Loginstage);

                                    /*PazienteFrame frame = new PazienteFrame();
                                    frame.start(Loginstage);*/
                                } else if(accessSet.getString("userType").equals("DIABETOLOGO")){
                                   /* DiabetologoFrame frame = new DiabetologoFrame();
                                    frame.start(Loginstage);*/

                                    DiabetologoModel model = new DiabetologoModel();
                                    DiabetologoView view = new DiabetologoView();
                                    new DiabetologoController(model, view, Loginstage);

                                } else if(accessSet.getString("userType").equals("AMMINISTRATORE")){
                                    AmministratoreFrame frame = new AmministratoreFrame();
                                    frame.start(Loginstage);
                                }

                                /*      --> //Queste righe che seguono le trovate nei PazienteFrame/DiabetologoFrame/   <--
                                        --> AmministratoreFrame,                                                        <--
                                        --> // un giorno saranno rimosse da qua                                         <-- */

                                /*Stage stage2 = new Stage();
                                stage2.setScene(new Scene(new HBox(new Group()),Loginstage.getWidth(), Loginstage.getHeight()));
                                stage2.setTitle("Prova");
                                Loginstage.close();
                                stage2.show();
                                stage2.alwaysOnTopProperty();
                                stage2.setMinHeight(320);
                                stage2.setMinWidth(240);*/


                            }
                        }
                    }

                    Text errorText = new Text("Username e/o password invalidi");
                    errorText.setFont(Font.font(14));
                    errorText.setX(50);
                    errorText.setY(40);
                    errorText.setFill(Color.RED);
                    group.getChildren().add(errorText);

                    usernameField.setText("");
                    passwordField.setText("");

                } catch (SQLException e) {
                    Loginstage.close();
                    throw new RuntimeException(e);
                }

            }

        });
        group.getChildren().add(accessButton);

        hbox.alignmentProperty().set(Pos.CENTER);
        hbox.getChildren().add(group);

        Loginstage.setMinHeight(320);
        Loginstage.setMinWidth(240);
        Loginstage.setTitle("Login Page");
        Loginstage.setScene(scene);
        Loginstage.show();

       /*
                -->     Comandi di prova per capire come chiudere una scheda e aprirne un'altra     <--
                -->     ci servirà  quando dalla pagina di accesso dovremmo passare alla homepage   <--
                -->     di utente o dottore                                                         <--

        Stage stage2 = new Stage();
        stage2.setScene(new Scene(new HBox(new Group()),320, 240));
        stage2.setTitle("Prova");
        Loginstage.close();
        stage2.show();
        stage2.alwaysOnTopProperty()*/

    }

    public static void main(String[] args) {
        launch();
    }
}