package guifx;

import application.controller.Controller;
import application.model.Fad;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretFadWindow extends Stage {

    private Fad fad;
    private TextField txfID, txfStr, txfKommentar, txfFadHistorik;

    Controller controller;
    public OpretFadWindow(String title, Fad fad) {
        controller = Controller.getController();

        this.fad = fad;

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
        scene.getStylesheets().add("application/style.css");
    }

    public OpretFadWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private Label lblError;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblID2 = new Label("ID:");
        Label lblStr = new Label("Str (L):");
        Label lblKommentar = new Label("Kommentar:");
        Label lblHistorik = new Label("Fad historik:     ");
        lblID2.setId("Overskrift3");
        lblStr.setId("Overskrift3");
        lblKommentar.setId("Overskrift3");
        lblHistorik.setId("Overskrift3");

        VBox vBox = new VBox(25);
        vBox.getChildren().addAll(lblID2, lblStr,lblKommentar,lblHistorik);
        pane.add(vBox, 0, 0);

        txfID = new TextField();
        txfID.setText(Fad.getCountID());
        txfID.setDisable(true);
        txfStr = new TextField();
        txfKommentar = new TextField();
        txfFadHistorik = new TextField();

        VBox vBox1 = new VBox(15);
        vBox1.getChildren().addAll(txfID,txfStr,txfKommentar,txfFadHistorik);
        pane.add(vBox1, 1, 0);

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 2);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> this.cancelAction());

        Button btnOK = new Button("Opret");
        pane.add(btnOK, 1, 2);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> this.okAction());

        lblError = new Label();
        pane.add(lblError, 0, 3);
        lblError.setStyle("-fx-text-fill: red");

        this.initControls();
    }

    private void okAction() {
        int id = Integer.parseInt(txfID.getCharacters().toString());
        if (id <= 0) {
            lblError.setText("ID skal være over 0");
        }
        int str = Integer.parseInt(txfStr.getText().trim());
        if (str < 1) {
            lblError.setText("Indsast en rigtig størrelse");

        }
        String kommetnar = txfKommentar.getText().trim();

        String fadType = txfFadHistorik.getText().trim();

        // Call controller methods
        controller.createFad(fadType, str, kommetnar).setID(id);



        this.hide();

    }

    private void cancelAction() {
        this.hide();
    }

    private void initControls() {

    }

    // -------------------------------------------------------------------------


}
