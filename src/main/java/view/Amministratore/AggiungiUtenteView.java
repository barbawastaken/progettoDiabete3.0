package view.Amministratore;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AggiungiUtenteView {

    public TextField nomeField = new TextField();
    public TextField cognomeField = new TextField();
    public TextField passwordField = new TextField();
    public DatePicker datePicker = new DatePicker();
    public TextField taxCodeField = new TextField();
    public TextField emailField = new TextField();
    public TextField addressField = new TextField();
    public TextField numberField = new TextField();
    public TextField cityField = new TextField();
    public TextField capField = new TextField();
    public ComboBox<String> genderField = new ComboBox<>();
    public TextField telephoneField = new TextField();
    public Button resetButton = new Button("Reset");
    public Button sendButton = new Button("Aggiungi Utente");
    public ToggleGroup toggleGroup = new ToggleGroup();
    public Text diabetologoSelectionText = new Text("Medico curante");
    public ComboBox<String> diabetologoSelection = new ComboBox<>();


    public RadioButton admin = new RadioButton("AMMINISTRATORE");
    public RadioButton diab = new RadioButton("DIABETOLOGO");
    public RadioButton utente = new RadioButton("PAZIENTE");
    public RadioButton primario = new RadioButton("PRIMARIO");

    private VBox layout = new VBox(10);
    private HBox auxLayout = new HBox(10);
    private StackPane stackPane = new StackPane();

    public void start(Stage stage) {

        diabetologoSelection.setVisible(false);             //inizialmente i bottoni per scegliere il diabetologo sono
        diabetologoSelection.setManaged(false);             //disattivati, appariranno eventualmente grazie al listener
        diabetologoSelectionText.setVisible(false);
        diabetologoSelection.setManaged(false);
        Text nomeText = new Text("Nome");
        nomeText.setFont(Font.font(16));

        Text cognomeText = new Text("Cognome");
        cognomeText.setFont(Font.font(16));


        Text dateText = new Text("Data di Nascita");
        dateText.setFont(Font.font(16));

        Text taxCodeText = new Text("Codice Fiscale");
        taxCodeText.setFont(Font.font(16));

        Text emailText = new Text("Email");
        emailText.setFont(Font.font(16));

        Text addressText = new Text("Indirizzo");
        addressText.setFont(Font.font(16));

        Text numberText = new Text("Numero");
        numberText.setFont(Font.font(16));

        Text cityText = new Text("Città");
        cityText.setFont(Font.font(16));

        Text capText = new Text("CAP");
        capText.setFont(Font.font(16));

        genderField.getItems().addAll("Maschio", "Femmina");
        Text genderText = new Text("Sesso");
        genderText.setFont(Font.font(16));


        Text telephoneText = new Text("Telefono");
        telephoneText.setFont(Font.font(16));

        Text passwordText = new Text("Password");
        passwordText.setFont(Font.font(16));

        admin.setToggleGroup(toggleGroup);
        diab.setToggleGroup(toggleGroup);
        utente.setToggleGroup(toggleGroup);
        primario.setToggleGroup(toggleGroup);

        diab.setSelected(true);

        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(new Insets(50, 80, 80, 80));

        layout.getChildren().addAll(
                nomeText, nomeField,
                cognomeText, cognomeField,
                genderText, genderField,
                dateText, datePicker,
                taxCodeText, taxCodeField,
                passwordText, passwordField,
                addressText, addressField,
                numberText, numberField,
                cityText, cityField,
                capText, capField,
                telephoneText, telephoneField,
                emailText, emailField,
                admin, diab, utente, primario,
                diabetologoSelectionText, diabetologoSelection,
                resetButton, sendButton
        );

        layout.setMaxWidth(300);

        ScrollPane scroll = new ScrollPane(layout);

        stackPane.getChildren().add(scroll);
        stackPane.setAlignment(Pos.CENTER);


        /*
        for(TextField tf : layout){
            tf.setMinWidth(150);
            tf.setMaxWidth(150);
        }
*/
        Scene scene = new Scene(stackPane, 500, 700);
        stage.setMinWidth(500);
        stage.setMinHeight(800);
        stage.setScene(scene);
        stage.setTitle("Aggiungi Utente");
        stage.show();
    }
}
