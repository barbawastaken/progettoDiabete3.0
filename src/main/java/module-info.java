module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens HomePages to javafx.fxml;
    exports HomePages;
}