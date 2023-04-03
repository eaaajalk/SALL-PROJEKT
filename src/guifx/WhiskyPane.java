package guifx;

import application.controller.Controller;
import application.model.Fad;
import application.model.Påfyldning;
import application.model.WhiskyBatch;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class WhiskyPane extends GridPane {
    private ListView<WhiskyBatch> lvwBatches;
    private TextField txfID, txfStr, txfLager, txfHylde, txfKommentar, txfLagerDato, fadHistorik;
    private TextArea txaDestillater;
    private WhiskyBatch whiskyBatch;
    private ComboBox<Object> comboBox;

    private Controller controller;

    public WhiskyPane() {
        controller = Controller.getController();
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        initContent();
    }

    private void initContent() {
        lvwBatches = new ListView<>();
        this.add(lvwBatches, 0, 1, 2, 4);
        lvwBatches.setPrefWidth(400);
        lvwBatches.setMaxHeight(350);
        lvwBatches.getItems().setAll(controller.getWhiskyBatches());

        ChangeListener<WhiskyBatch> listener = (ov, oldWhiskyBatch, newWhiskyBatch) -> this.selectedBatchChanged();
        lvwBatches.getSelectionModel().selectedItemProperty().addListener(listener);

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setMaxHeight(200);
        separator.setPadding(new Insets(0,10,0,10));
        this.add(separator, 2, 4);

        Label lblID2 = new Label("ID:");
        Label lblStr = new Label("Str (L):");
        Label lblLager2 = new Label("Lager:");
        Label lblHylde2 = new Label("Hylde:");
        Label lblDato = new Label("Lagerdato:");
        Label lblKommentar = new Label("Kommentar:");
        Label lblHistorik = new Label("Fad historik:      ");
        Label lblDestillat = new Label("Indhold:");

        VBox vBox = new VBox(25);
        vBox.getChildren().addAll(lblID2, lblStr, lblLager2,lblHylde2,lblDato,lblKommentar,lblHistorik, lblDestillat);
        this.add(vBox, 3, 4);

        txfID = new TextField();
        txfStr = new TextField();
        txfLager = new TextField();
        txfHylde = new TextField();
        txfLagerDato = new TextField();
        txfKommentar = new TextField();
        fadHistorik = new TextField();
        txaDestillater = new TextArea();
        txaDestillater.setMaxWidth(350);
        txaDestillater.setMaxHeight(100);

        VBox vBox1 = new VBox(15);
        vBox1.getChildren().addAll(txfID,txfStr,txfLager,txfHylde,txfLagerDato,txfKommentar, fadHistorik, txaDestillater);
        this.add(vBox1, 4, 4);


        Button btnOpret = new Button("Opret Fad");
        btnOpret.setOnAction(event -> this.createAction());

        Button btnSlet = new Button("Slet Fad");
        btnSlet.setOnAction(event -> this.sletAction());

        Button btnTapning = new Button("Tap på flasker");
        btnTapning.setOnAction(event -> this.placerAction());

        HBox hBoxBtn = new HBox(50);
        hBoxBtn.getChildren().addAll(btnOpret, btnSlet, btnTapning);
        this.add(hBoxBtn, 0, 8);

    }


    private void sletAction() {
        whiskyBatch = lvwBatches.getSelectionModel().getSelectedItem();
        if (whiskyBatch != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Slet fad");
            // alert.setContentText("Are you sure?");
            alert.setHeaderText("Er du sikker på at slette fad med ID: " + whiskyBatch.getBatchID() + " og fjerne det fra lageret?");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

                lvwBatches.getItems().setAll(controller.getWhiskyBatches());
                this.updateControls();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Slet fad");
            alert.setHeaderText("Du kan ikke slette et fad der er på lageret");
            // wait for the modal dialog to close
            alert.show();

        }

    }

    private void placerAction() {


    }

    private void createAction() {
        OpretFadWindow dia = new OpretFadWindow("Opret Batch");
        dia.showAndWait();
        // Wait for the modal dialog to close
        lvwBatches.getItems().setAll(controller.getWhiskyBatches());
        int index = lvwBatches.getItems().size() - 1;
        lvwBatches.getSelectionModel().select(index);
    }

    private void sortAction() {

    }
    // -------------------------------------------------------------------------

    private void selectedBatchChanged() {
        this.updateControls();
    }

    public void updateControls() {
        whiskyBatch = lvwBatches.getSelectionModel().getSelectedItem();
        if (whiskyBatch != null) {
            txfID.setText(whiskyBatch.getBatchID());



        } else {
            txfID.clear();
            txfStr.clear();
            txfLager.clear();
            txfHylde.clear();
            txfKommentar.clear();
            txfLagerDato.clear();
            fadHistorik.clear();
            txaDestillater.clear();
        }

    }



}
