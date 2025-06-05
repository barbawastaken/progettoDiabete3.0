module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jbcrypt;
    opens controller to javafx.fxml;
    opens controller.Paziente to javafx.fxml;
    opens controller.Paziente.RilevazioneGlicemia to javafx.fxml;
    opens controller.Paziente.AssunzioneFarmaco to javafx.fxml;
    opens controller.Paziente.PatologieConcomitanti to javafx.fxml;
    opens controller.Amministratore to javafx.fxml;
    opens HomePages to javafx.fxml;
    opens model.Amministratore to javafx.base;
    exports controller.Diabetologo to javafx.fxml;
    opens controller.Diabetologo to javafx.fxml;
    opens controller.Paziente.AggiuntaSintomi to javafx.fxml;


    exports HomePages;
}