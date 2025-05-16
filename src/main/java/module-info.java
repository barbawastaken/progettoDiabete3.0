module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens HomePages to javafx.fxml;
    exports HomePages;
}