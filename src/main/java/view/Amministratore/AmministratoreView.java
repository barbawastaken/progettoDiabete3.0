package view.Amministratore;

import controller.Amministratore.AmministratoreController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AmministratoreView {

    private VBox layout;
    private Button addUserButton;
    private Button viewUserListButton;

    public AmministratoreView() {
        layout = new VBox(10); // Imposta un gap tra gli elementi
        layout.setAlignment(Pos.CENTER); // Allinea gli elementi al centro

        // Creazione dell'header
        Rectangle header = new Rectangle(1980, 100);
        header.setFill(Color.DARKBLUE);

        // Caricamento dell'immagine
        ImageView userImageView = null;
        try {
            Image userImage = new Image(getClass().getResource("/images/userImage.png").toExternalForm());
            userImageView = new ImageView(userImage);
            userImageView.setFitWidth(100);
            userImageView.setFitHeight(100);
        } catch (Exception e) {
            System.out.println("Errore nel caricamento dell'immagine: " + e.getMessage());
        }

        // Crea il bottone aggiunta user
        addUserButton = new Button("Aggiungi un nuovo utente");
        addUserButton.setLayoutX(70);  // Imposta la posizione del bottone

        // Crea il bottone visualizza lista utenti
        viewUserListButton = new Button("Visualizza lista utenti");
        viewUserListButton.setLayoutX(160);


        // Aggiungi gli elementi al layout
        layout.getChildren().add(header);
        if (userImageView != null) {
            layout.getChildren().add(userImageView);
        }
        layout.getChildren().add(addUserButton);
        layout.getChildren().add(viewUserListButton); // permette di visualizzare bottone
    }

    public Button getAddUserButton() {
        return addUserButton;
    }

    public Scene getScene() {
        return new Scene(layout);
    }

    public Button getViewUserListButton() {return viewUserListButton;}

    public void start(Stage stage, AmministratoreController controller) {
        stage.setScene(this.getScene());
        stage.setHeight(600); // imposta dimensioni desiderate
        stage.setWidth(800);
        stage.setTitle("Homepage amministratore");
        stage.show();
    }
}
