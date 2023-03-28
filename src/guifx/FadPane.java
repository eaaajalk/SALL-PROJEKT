package guifx;

import application.controller.Controller;
import application.model.Fad;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FadPane extends GridPane {
    private ListView<Fad> lvwFade;

    private TextField txfID, txfStr, txfLager, txfHylde, txfKommentar, txfLagerDato, txfFadHistorik;

    private ComboBox<Object> comboBox;

    private Controller controller;

    public FadPane() {
        controller = Controller.getController();
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        initContent();
    }

    private void initContent() {
        Label lblComp = new Label("  Sorter efter");
        this.add(lblComp, 0, 0);
        comboBox = new ComboBox<>();

        this.add(comboBox, 0, 1);
        for (int i = 0; i < controller.getLagere().size(); i++) {
            comboBox.getItems().add("" + controller.getLagere().get(i));
        }

        comboBox.getItems().add("Ikke placerede fade");
        comboBox.getItems().add("Alle fade");

        comboBox.setOnAction(event -> this.sortAction());

        Label lblID = new Label("     ID");
        Label lblHylde = new Label("Hylde");
        Label lblPlads = new Label("Plads");
        Label lblLager = new Label("Lager");

        HBox hBox = new HBox(80);
        hBox.getChildren().add(lblID);
        hBox.getChildren().add(lblHylde);
        hBox.getChildren().add(lblPlads);
        hBox.getChildren().add(lblLager);

        this.add(hBox, 0, 3);

        lvwFade = new ListView<>();
        this.add(lvwFade, 0, 4, 2, 4);
        lvwFade.setPrefWidth(380);
        lvwFade.setPrefHeight(400);
        lvwFade.getItems().setAll(controller.getFade());

        ChangeListener<Fad> listener = (ov, oldFad, newFad) -> this.selectedFadChanged();
        lvwFade.getSelectionModel().selectedItemProperty().addListener(listener);

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPrefHeight(250);
        this.add(separator, 2, 4);

        Label lblID2 = new Label("ID:");
        Label lblStr = new Label("Str:");
        Label lblLager2 = new Label("Lager:");
        Label lblHylde2 = new Label("Hylde:");
        Label lblDato = new Label("Lagerdato:");
        Label lblKommentar = new Label("Kommentar:");
        Label lblHistorik = new Label("Fad historik:      ");

        VBox vBox = new VBox(25);
        vBox.getChildren().addAll(lblID2, lblStr, lblLager2,lblHylde2,lblDato,lblKommentar,lblHistorik);
        this.add(vBox, 3, 4);

        txfID = new TextField();
        txfStr = new TextField();
        txfLager = new TextField();
        txfHylde = new TextField();
        txfLagerDato = new TextField();
        txfKommentar = new TextField();
        txfFadHistorik = new TextField();

        VBox vBox1 = new VBox(15);
        vBox1.getChildren().addAll(txfID,txfStr,txfLager,txfHylde,txfLagerDato,txfKommentar,txfFadHistorik);
        this.add(vBox1, 4, 4);

    }
    private void sortAction() {

    }
    // -------------------------------------------------------------------------

    private void selectedFadChanged() {
        this.updateControls();
    }

    public void updateControls() {
        Fad fad = lvwFade.getSelectionModel().getSelectedItem();
        if (fad != null) {
            txfID.setText(fad.getID());
            txfStr.setText(String.valueOf(fad.getStr()));
            txfLager.setText(String.valueOf(fad.getHylde().getLager()));
            txfHylde.setText(fad.getHylde().toString());
            txfKommentar.setText(fad.getKommentar());
            txfLagerDato.setText(String.valueOf(fad.getLagerDato()));

            StringBuilder sb = new StringBuilder();
            for (String historik : fad.getFadHistorik()) {
                sb.append(historik + "\n");
            }
            txfFadHistorik.setText(sb.toString());
        } else {
            txfID.clear();
            txfStr.clear();
            txfLager.clear();
            txfHylde.clear();
            txfKommentar.clear();
            txfLagerDato.clear();
            txfFadHistorik.clear();
        }

    }



}
