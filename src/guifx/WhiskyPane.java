package guifx;

import application.controller.Controller;
import application.model.Fad;
import application.model.Produkt;
import application.model.Påfyldning;
import application.model.WhiskyBatch;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Optional;

public class WhiskyPane extends GridPane {
    private ListView<WhiskyBatch> lvwBatches;
    private TextField txfID, txfFortynding, txfModning, txfBatchDato, txfBeskrivelse, txfBatchMængde;
    private TextArea txfFade;
    private ListView<Produkt> lvwProdukter;
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
        Label lblOverskift = new Label("Whisky Batches:");
        this.add(lblOverskift, 0, 0);
        lvwBatches = new ListView<>();
        this.add(lvwBatches, 0, 1, 2, 4);
        lvwBatches.setMaxWidth(300);
        lvwBatches.setMaxHeight(350);
        lvwBatches.getItems().setAll(controller.getWhiskyBatches());

        ChangeListener<WhiskyBatch> listener = (ov, oldWhiskyBatch, newWhiskyBatch) -> this.selectedBatchChanged();
        lvwBatches.getSelectionModel().selectedItemProperty().addListener(listener);

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setMaxHeight(200);
        separator.setPadding(new Insets(0,20,0,20));
        this.add(separator, 2, 4);

        Label lblID2 = new Label("ID:");
        Label lblFortynding = new Label("Fortyndingsmængde(L):");
        Label lblModning = new Label("Modningstid:");
        Label lblBatchDato = new Label("Batch dato:");
        Label lblBatchMængde = new Label("BatchMængde:");
        Label lblBeskrivelse = new Label("Beskrivelse:");
        Label lblFade = new Label("Fra fad(e):");

        VBox vBox = new VBox(25);
        vBox.getChildren().addAll(lblID2, lblFortynding, lblModning,lblBatchDato,lblBatchMængde, lblBeskrivelse,lblFade);
        this.add(vBox, 3, 4);

        txfID = new TextField();
        txfFortynding = new TextField();
        txfModning = new TextField();
        txfBatchDato = new TextField();
        txfBatchMængde = new TextField();
        txfBeskrivelse = new TextField();
        txfFade = new TextArea();
        txfFade.setMaxWidth(350);
        txfFade.setMaxHeight(100);


        VBox vBox1 = new VBox(15);
        vBox1.getChildren().addAll(txfID, txfFortynding, txfModning, txfBatchDato, txfBatchMængde, txfBeskrivelse, txfFade);
        this.add(vBox1, 4, 4);

        Separator separator1 = new Separator(Orientation.VERTICAL);
        separator1.setMaxHeight(200);
        separator1.setPadding(new Insets(0,20,0,20));
        this.add(separator1, 5, 4);

        lvwProdukter = new ListView<>();
        this.add(lvwProdukter, 6, 4);
        lvwProdukter.setPrefWidth(300);
        lvwProdukter.setMaxHeight(350);



        Button btnOpret = new Button("Opret Batch");
        btnOpret.setOnAction(event -> this.createAction());

        Button btnSlet = new Button("Slet Batch");
        btnSlet.setOnAction(event -> this.sletAction());

        Button btnTapning = new Button("Tap på flasker");
        btnTapning.setOnAction(event -> this.placerAction());

        HBox hBoxBtn = new HBox(25);
        hBoxBtn.getChildren().addAll(btnOpret, btnSlet, btnTapning);
        this.add(hBoxBtn, 0, 5);

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
        OpretWhiskyBatchWindow dia = new OpretWhiskyBatchWindow("Opret Batch", whiskyBatch);
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
            txfFortynding.setText(String.valueOf(whiskyBatch.getFortyndningsMængde()));
            txfModning.setText(String.valueOf(whiskyBatch.getModningstid()));
            txfBatchDato.setText(String.valueOf(whiskyBatch.getBatchDato()));
            txfBeskrivelse.setText(whiskyBatch.getBeskrivelse());
            txfBatchMængde.setText(String.valueOf(whiskyBatch.getBatchMængde()));

            StringBuilder sb1 = new StringBuilder();
            ArrayList<Fad> fade = new ArrayList<>(whiskyBatch.getFade().keySet());
            for (int i = 0; i < fade.size(); i++) {
               Fad fad = fade.get(i);
               int mængde = whiskyBatch.getFade().get(fad);
               sb1.append("FadID: ").append(fad.getID()).append(" | Mængde tilføjet: ").append(mængde).append("L").append("\n");
            }
            txfFade.setText(String.valueOf(sb1));

        } else {
            txfID.clear();
            txfFortynding.clear();
            txfModning.clear();
            txfBatchDato.clear();
            txfBeskrivelse.clear();
            txfBatchMængde.clear();
            txfFade.clear();
            lvwProdukter.getSelectionModel().clearSelection();
        }

    }



}
