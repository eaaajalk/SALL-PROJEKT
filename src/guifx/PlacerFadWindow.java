package guifx;

import application.controller.Controller;
import application.model.Fad;
import application.model.Hylde;
import application.model.Lager;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class PlacerFadWindow extends Stage {

    Controller controller;
    Fad fad;
    Boolean placeret;
    public PlacerFadWindow(String title, Fad fad, Boolean placeret) {
        controller = Controller.getController();

        this.fad = fad;
        this.placeret = placeret;

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    public PlacerFadWindow(String title) {
        this(title, null, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfName, txfHours;
    private Label lblError;
    private ComboBox<Lager> comboBox;
    private ComboBox<Hylde> comboBox1;
    private ComboBox<Integer> comboBox2;
    private TextField txaDato;
    private Lager lager;
    private Hylde hylde;
    private int plads;
    private DatePicker datePicker;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblComp = new Label("Vælg lager:");
        pane.add(lblComp, 0, 0);
        comboBox = new ComboBox<>();

        pane.add(comboBox, 0, 1);
        for (int i = 0; i < controller.getLagere().size(); i++) {
            comboBox.getItems().add(controller.getLagere().get(i));
        }
        comboBox.setOnAction(event -> this.lagerAction());

        Label lblHylder = new Label("Hylder:");
        pane.add(lblHylder, 0, 2);

        comboBox1 = new ComboBox<>();
        pane.add(comboBox1, 0, 3);
        comboBox1.setOnAction(event -> this.hyldeAction());

        Label lblPladser = new Label("Pladser:");
        pane.add(lblPladser, 0, 4);

        comboBox2 = new ComboBox<>();
        pane.add(comboBox2, 0, 5);


        Label lblDato = new Label("PlaceringsDato");
        pane.add(lblDato, 0, 6);
        datePicker = new DatePicker();
        pane.add(datePicker, 0, 7);


        Button btnOk = new Button("Placer");
        pane.add(btnOk, 0, 8);
        GridPane.setHalignment(btnOk, HPos.RIGHT);
        btnOk.setOnAction(event -> this.okAction());

        Button btnCancel = new Button("Annuller");
        pane.add(btnCancel, 0, 8);
        btnCancel.setOnAction(event -> this.cancelAction());


    }

    private void hyldeAction() {
        hylde = comboBox1.getValue();
        if (hylde != null ) {
            for (int i = 1; i <= hylde.getAntalPladser(); i++) {
                if (hylde.getFadeMap().get(i) == null) {
                    comboBox2.getItems().add(i);
                }
            }
        }
    }

    private void lagerAction() {
        lager = comboBox.getValue();
        if (lager != null) {
            for (int i = 0; i < lager.getHylder().size(); i++) {
                comboBox1.getItems().add(lager.getHylder().get(i));
            }
        }
    }

    private void okAction() {
        plads = comboBox2.getValue();
        LocalDate dato = datePicker.getValue();

        if (!placeret) {
            if (lager != null & hylde != null && dato != null && plads > 0) {
                controller.placerFad(hylde, plads, fad, dato);
            }
        } else {
            if (lager != null & hylde != null && dato != null && plads > 0) {
                controller.flytFad(fad, hylde, plads, dato);
            }
        }

        this.hide();

    }

    private void cancelAction() {
            this.hide();
    }

    // -------------------------------------------------------------------------


}



