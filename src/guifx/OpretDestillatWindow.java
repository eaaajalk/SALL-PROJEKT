package guifx;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.MaltBatch;
import application.model.Medarbejder;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class OpretDestillatWindow extends Stage {
    private Destillat destillat;

    Controller controller;
    public OpretDestillatWindow(String title, Destillat destillat) {
        controller = Controller.getController();
        this.destillat = destillat;

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

    public OpretDestillatWindow(String title) {
        this(title, null);

    }

    // -------------------------------------------------------------------------
    private Label lblError;
    private TextField txfID1, txfMængde1, txfAlkoholProcent, txfVandtype, txfKommentar;

    private ComboBox<MaltBatch> comboBox;
    private ComboBox<Medarbejder> comboBox1;
    private DatePicker dateStart, dateSlut;
    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblID1 = new Label("ID:");
        lblID1.setId("Overskrift3");
        Label lblStartDato = new Label("Start dato:");
        lblStartDato.setId("Overskrift3");
        Label lblSlutDato = new Label("Slut dato:");
        lblSlutDato.setId("Overskrift3");
        Label lblMængde1 = new Label("Mængde (L):");
        lblMængde1.setId("Overskrift3");
        Label lblAlholProcent = new Label("Alkoholprocent");
        lblAlholProcent.setId("Overskrift3");
        Label lblMaltBatch = new Label("MaltBatch:");
        lblMaltBatch.setId("Overskrift3");
        Label lblVandtype = new Label("Vandtype:");
        lblVandtype.setId("Overskrift3");
        Label lblMedarbejder = new Label("Medarbejder:");
        lblMedarbejder.setId("Overskrift3");
        Label lblKommentar = new Label("Kommentar:");
        lblKommentar.setId("Overskrift3");

        VBox vBox = new VBox(25);
        vBox.getChildren().addAll(lblID1, lblStartDato, lblSlutDato,lblMængde1,lblAlholProcent,lblMaltBatch, lblVandtype, lblMedarbejder, lblKommentar);
        pane.add(vBox, 0, 0);

        txfID1 = new TextField();
        dateStart = new DatePicker();
        dateSlut = new DatePicker();
        txfMængde1 = new TextField();
        txfAlkoholProcent = new TextField();
        comboBox = new ComboBox<>();
        comboBox.getItems().setAll(controller.getMaltBatch());
        txfVandtype = new TextField();
        comboBox1 = new ComboBox<>();
        comboBox1.getItems().setAll(controller.getMedarbejdere());
        txfKommentar = new TextField();

        VBox vBox1 = new VBox(16);
        vBox1.getChildren().addAll(txfID1, dateStart, dateSlut, txfMængde1, txfAlkoholProcent, comboBox, txfVandtype, comboBox1, txfKommentar);
        pane.add(vBox1, 2, 0);


        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 2);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> this.cancelAction());

        Button btnOK = new Button("Opret");
        pane.add(btnOK, 2, 2);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> this.okAction());

        lblError = new Label();
        pane.add(lblError, 0, 3);
        lblError.setStyle("-fx-text-fill: red");

        this.initControls();
    }

    private void okAction() {
        String id = txfID1.getText().trim();
        if (id.length() == 0) {
            lblError.setText("ID er ikke udfyldt");
        }
        LocalDate startDato = dateStart.getValue();
        if (startDato == null) {
            lblError.setText("Vælg start dato");
        }
        LocalDate slutDato = dateSlut.getValue();
        if (slutDato == null) {
            lblError.setText("Vælg slut dato");
        }
        int mængde = Integer.parseInt(txfMængde1.getText().trim());
        if (mængde < 1) {
            lblError.setText("Angiv mængde (L)");
        }
        int procent = Integer.parseInt(txfAlkoholProcent.getText().trim());
        if (procent < 1 || procent > 100) {
            lblError.setText("Angiv aloholprocent mellem 1 og 100");
        }
        MaltBatch maltBatch1 = comboBox.getValue();
        if (maltBatch1 == null) {
            lblError.setText("Vælg MaltBatch");
        }
        String vand = txfVandtype.getText().trim();
        if (id.length() == 0) {
            lblError.setText("Angiv vandtype");
        }
        Medarbejder medarbejder = comboBox1.getValue();
        if (medarbejder == null) {
            lblError.setText("Vælg medarbejder");
        }
        String kommetnar = txfKommentar.getText().trim();

        controller.createDestillat(startDato, slutDato, mængde, medarbejder, kommetnar, vand, maltBatch1, id, procent);
        this.hide();

    }

    private void cancelAction() {
        this.hide();
    }

    private void initControls() {

    }

    // -------------------------------------------------------------------------

}
