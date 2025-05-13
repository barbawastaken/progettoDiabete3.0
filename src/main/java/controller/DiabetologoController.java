package controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import model.DiabetologoModel;
import view.DiabetologoView;
import javafx.stage.Stage;

public class DiabetologoController {

    private DiabetologoModel diabetologoModel;
    private DiabetologoView diabetologoView;

    public DiabetologoController(DiabetologoModel diabetologoModel, DiabetologoView diabetologoView, Stage loginStage){

        this.diabetologoModel = diabetologoModel;
        this.diabetologoView = diabetologoView;

        Stage diabetologoStage = new Stage();
        diabetologoStage.setScene(diabetologoView.getScene());
        diabetologoStage.setHeight(loginStage.getHeight());
        diabetologoStage.setWidth(loginStage.getWidth());
        diabetologoStage.setX(loginStage.getX());
        diabetologoStage.setY(loginStage.getY());
        diabetologoStage.alwaysOnTopProperty();
        diabetologoStage.setMinHeight(320);
        diabetologoStage.setMinWidth(240);
        diabetologoStage.setTitle("Homepage diabetologo");
        loginStage.close();
        diabetologoStage.show();
    }

}
