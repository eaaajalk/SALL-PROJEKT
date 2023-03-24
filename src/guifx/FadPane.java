package guifx;

import application.controller.Controller;
import application.model.Fad;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class FadPane extends GridPane {
    private TextField txfName, txfHours;
    private TextArea txaEmps;
    private ListView<Fad> lvwFade;
    private ComboBox comboBox;

    public FadPane() {
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblComp = new Label("  Sorter efter");
        this.add(lblComp, 0, 0);
        comboBox = new ComboBox<>();

        this.add(comboBox, 0, 1);
        for (int i = 0; i < Controller.getLagere().size(); i++) {
            comboBox.getItems().add("" + Controller.getLagere().get(i));
        }
        comboBox.getItems().add("Ikke placerede fade");
        comboBox.getItems().add("Alle fade");

        comboBox.setOnAction(event -> this.sortAction());

        Label lblID = new Label("     ID");
        Label lblHylde = new Label("Hylde");
        Label lblPlads = new Label("Plads");

        HBox hBox = new HBox(80);
        hBox.getChildren().add(lblID);
        hBox.getChildren().add(lblHylde);
        hBox.getChildren().add(lblPlads);

        this.add(hBox, 0, 3);

        lvwFade = new ListView<>();
        this.add(lvwFade, 0, 4, 2, 4);
        lvwFade.setPrefWidth(300);
        lvwFade.setPrefHeight(400);
        lvwFade.getItems().setAll(Controller.getFade());
    }


    private void sortAction() {

    }


    // -------------------------------------------------------------------------

    private void selectedCompanyChanged() {
        this.updateControls();
    }

    public void updateControls() {

    }



}
