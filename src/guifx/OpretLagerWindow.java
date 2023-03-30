package guifx;

import application.controller.Controller;
import application.model.Fad;
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

public class OpretLagerWindow extends Stage {

    private Lager lager;
    private TextField txfNavn, txfAdresse;
    Controller controller;
    public OpretLagerWindow (String title, Lager lager) {
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

    public OpretLagerWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private Label lblError;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblNavn = new Label("Navn:");
        Label lblAdresse = new Label("Adresse:");


        VBox vBox = new VBox(25);
        vBox.getChildren().addAll(lblNavn, lblAdresse);
        pane.add(vBox, 0, 0);

        txfNavn = new TextField();
        txfAdresse = new TextField();


        VBox vBox1 = new VBox(15);
        vBox1.getChildren().addAll(txfNavn,txfAdresse);
        pane.add(vBox1, 1, 0);

        Button btnCancel = new Button("Annuller");
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
        String navn = txfNavn.getText().trim();
        if (navn.length() == 0) {
            lblError.setText("Navn er ikke udfyld");
        }
        String adresse = txfAdresse.getText().trim();
        if (adresse.length() == 1) {
            lblError.setText("Indsast adresse");

        }
   
        // Call controller methods
        controller.createLager(navn, adresse);


        this.hide();

    }

    private void cancelAction() {
        this.hide();
    }

    private void initControls() {

    }

    // -------------------------------------------------------------------------

}
