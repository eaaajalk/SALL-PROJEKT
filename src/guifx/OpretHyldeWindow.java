package guifx;

import application.controller.Controller;
import application.model.Hylde;
import application.model.Lager;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretHyldeWindow extends Stage {

    private Lager lager;

    private TextField txfHyldeNr, txfAntalPladser;

    Controller controller;
    public OpretHyldeWindow(String title, Lager lager) {
        controller = Controller.getController();

        this.lager = lager;

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    public OpretHyldeWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private Label lblError;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblHyldeNr = new Label("Hylde nr:");
        Label lblAntalPladser = new Label("Antal hyldepladser:");

        VBox vBox = new VBox(25);
        vBox.getChildren().addAll(lblHyldeNr, lblAntalPladser);
        pane.add(vBox, 0, 0);

        txfHyldeNr = new TextField();
        txfAntalPladser = new TextField();

        VBox vBox1 = new VBox(15);
        vBox1.getChildren().addAll(txfHyldeNr, txfAntalPladser);
        pane.add(vBox1, 1, 0);

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 4);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> this.cancelAction());

        Button btnOK = new Button("Opret");
        pane.add(btnOK, 1, 4);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> this.okAction());

        lblError = new Label();
        pane.add(lblError, 0, 5);
        lblError.setStyle("-fx-text-fill: red");

        this.initControls();
    }

    private void okAction() {
        int hyldeNr = Integer.parseInt(txfHyldeNr.getText().trim());
        if (hyldeNr < 1) {
            lblError.setText("Skriv hyldenr");
                if (lager.getBrugteHyldeNumre().contains(hyldeNr)){
                    lblError.setText("Hyldenr eksisterer allerede");
            }
        }
        int pladser = Integer.parseInt(txfAntalPladser.getText().trim());
        if (pladser < 1) {
            lblError.setText("Indtast antal hyldepladser");

        }

        // Call controller methods
        lager.createHylde(hyldeNr, lager, pladser);


        this.hide();

    }

    private void cancelAction() {
        this.hide();
    }

    private void initControls() {

    }

    // -------------------------------------------------------------------------


}
