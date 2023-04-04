package guifx;

import application.controller.Controller;
import application.model.Fad;
import application.model.Produkt;
import application.model.WhiskyBatch;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
    private Produkt produkt;

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

        ChangeListener<Produkt> listener1 = (ov, oldProdukt, newProdukt) -> this.selectedProduktChanged();
        lvwProdukter.getSelectionModel().selectedItemProperty().addListener(listener1);

        Button btnHistorie = new Button("Se Historie");
        this.add(btnHistorie, 6, 5);
        btnHistorie.setOnAction(event -> this.historieAction());


        Button btnOpret = new Button("Opret Batch");
        btnOpret.setOnAction(event -> this.createAction());

        Button btnSlet = new Button("Slet Batch");
        btnSlet.setOnAction(event -> this.sletAction());

        Button btnTapning = new Button("Tap på flasker");
        btnTapning.setOnAction(event -> this.tapAction());

        HBox hBoxBtn = new HBox(25);
        hBoxBtn.getChildren().addAll(btnOpret, btnSlet, btnTapning);
        this.add(hBoxBtn, 0, 5);

    }

    private void historieAction() {
        if (produkt != null) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Whisky Historie");

            Label label = new Label("The exception stacktrace was:");

            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);


            ButtonType buttonTypeOne = new ButtonType("One");
            ButtonType buttonTypeTwo = new ButtonType("Two");
            ButtonType buttonTypeThree = new ButtonType("Three");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                // ... user chose "One"
            } else if (result.get() == buttonTypeTwo) {
                // ... user chose "Two"
            } else if (result.get() == buttonTypeThree) {
                // ... user chose "Three"
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }

    private void selectedProduktChanged() {
        produkt = lvwProdukter.getSelectionModel().getSelectedItem();
    }


    private void sletAction() {

    }

    private void tapAction() {
        if (whiskyBatch != null) {
            TapFlaskerWindow dia = new TapFlaskerWindow("Tap Flasker", whiskyBatch);
            dia.showAndWait();
            // Wait for the modal dialog to close
            lvwBatches.getItems().setAll(controller.getWhiskyBatches());
            int index = lvwBatches.getItems().size() - 1;
            lvwBatches.getSelectionModel().select(index);

            lvwProdukter.getItems().setAll(whiskyBatch.getProdukter());
        }
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
            for (Fad fad : fade) {
                int mængde = whiskyBatch.getFade().get(fad);
                sb1.append("FadID: ").append(fad.getID()).append(" | Mængde tilføjet: ").append(mængde).append("L").append("\n");
            }
            txfFade.setText(String.valueOf(sb1));

            selectedProduktChanged();

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

    public void updateControls() {


    }



}
