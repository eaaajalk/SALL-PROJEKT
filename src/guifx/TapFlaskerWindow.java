package guifx;

import application.controller.Controller;
import application.model.WhiskyBatch;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class TapFlaskerWindow extends Stage {
    private WhiskyBatch whiskyBatch;
    private TextField txfPris, txfAntalFlasker, txfStr;
    private DatePicker datePicker;
    Controller controller;
    public TapFlaskerWindow(String title, WhiskyBatch whiskyBatch) {
        controller = Controller.getController();
        this.whiskyBatch = whiskyBatch;
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);
        Scene scene = new Scene(pane);
        this.setScene(scene);
    }
    public TapFlaskerWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------
    private Label lblError;
    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);
        Label lblPris = new Label("Pris pr flaske:");
        txfPris = new TextField();
        pane.add(lblPris, 0, 1);
        pane.add(txfPris, 0, 2);
        Label lblStr = new Label("Indtast flaskestørrelse(L)");
        txfStr = new TextField();
        pane.add(lblStr, 0, 3);
        pane.add(txfStr, 0, 4);
        Button btnBeregn = new Button("Beregn max flasker");
        pane.add(btnBeregn, 0, 5);
        btnBeregn.setOnAction(event -> this.beregnAction());
        Label lblAntal = new Label("Antal flasker");
        txfAntalFlasker = new TextField();
        pane.add(lblAntal, 0, 6);
        pane.add(txfAntalFlasker, 0, 7);
        Label lblDato = new Label("Tapnings dato");
        datePicker = new DatePicker();
        pane.add(lblDato, 0, 8);
        pane.add(datePicker, 0, 9);
        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 10);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> this.cancelAction());
        Button btnOK = new Button("Tap");
        pane.add(btnOK, 0, 10);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> this.okAction());
        lblError = new Label();
        pane.add(lblError, 0, 11);
        lblError.setStyle("-fx-text-fill: red");
    }
    private void beregnAction() {
        if (whiskyBatch != null && txfStr.getText().trim().length() > 0) {
            txfAntalFlasker.setText(String.valueOf(whiskyBatch.beregnAntalFlasker(whiskyBatch.getBatchMængde(), Double.parseDouble(txfStr.getText().trim()))));
        } else {
            lblError.setText("Angiv flaskestørrelse");
        }
    }
    private void okAction() {
        if (whiskyBatch != null) {
            int pris = Integer.parseInt(txfPris.getText().trim());
            if (txfPris.getText().trim().length() == 0) {
                lblError.setText("Ángiv pris");
            }
            LocalDate dato = datePicker.getValue();
            if (datePicker == null) {
                lblError.setText("Vælg tapningsdato");
            }
            int antal = Integer.parseInt(txfAntalFlasker.getText().trim());
            if (txfAntalFlasker.getText().trim().length() == 0) {
                lblError.setText("Angiv antal flasker");
            }
            double str = Double.parseDouble(txfStr.getText().trim());
            if (txfStr.getText().trim().length() == 0) {
                lblError.setText("Angiv flaske størrelse");
            }
            if (antal > whiskyBatch.beregnAntalFlasker(whiskyBatch.getBatchMængde(), str)) {
                lblError.setText("Du kan ikke oprette så mange flasker af dette whisky batch");
            }
            // Call controller methods
            controller.tapPåFlasker(pris, dato, antal, str, whiskyBatch);
            this.hide();
        }
    }
    private void cancelAction() {
        this.hide();
    }
    // -------------------------------------------------------------------------
}
