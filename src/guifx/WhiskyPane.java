package guifx;

import application.controller.Controller;
import application.model.Fad;
import application.model.Produkt;
import application.model.WhiskyBatch;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.PrintWriter;
import java.io.StringWriter;
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
        this.setAlignment(Pos.CENTER);
        initContent();
    }

    private void initContent() {
        Label lblOverskrift = new Label("Whisky Oversigt");
        this.add(lblOverskrift, 0, 0);
        lblOverskrift.setId("PaneOverskrift");
        lblOverskrift.setPadding(new Insets(0, 0, 20, 0));


        Label lblOverskift1 = new Label("Whisky Batches:");
        lblOverskift1.setId("Overskrift2");
        this.add(lblOverskift1, 0, 1);
        lvwBatches = new ListView<>();
        this.add(lvwBatches, 0, 2);
        lvwBatches.setMaxWidth(350);
        lvwBatches.setPrefWidth(350);
        lvwBatches.setMaxHeight(450);
        lvwBatches.getItems().setAll(controller.getWhiskyBatches());

        ChangeListener<WhiskyBatch> listener = (ov, oldWhiskyBatch, newWhiskyBatch) -> this.selectedBatchChanged();
        lvwBatches.getSelectionModel().selectedItemProperty().addListener(listener);

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setMaxHeight(200);
        separator.setPadding(new Insets(0,40,0,40));
        this.add(separator, 2, 2);

        Label lblInfo = new Label("Batch information:");
        this.add(lblInfo, 3, 1, 2, 1);
        lblInfo.setId("Overskrift2");

        Label lblID2 = new Label("ID:");
        lblID2.setId("Overskrift3");
        Label lblFortynding = new Label("Fortynding(L):");
        lblFortynding.setId("Overskrift3");
        Label lblModning = new Label("Modningstid:");
        lblModning.setId("Overskrift3");
        Label lblBatchDato = new Label("Batch dato:");
        lblBatchDato.setId("Overskrift3");
        Label lblBatchMængde = new Label("BatchMængde(L):");
        lblBatchMængde.setId("Overskrift3");
        Label lblBeskrivelse = new Label("Kommentar:");
        lblBeskrivelse.setId("Overskrift3");
        Label lblFade = new Label("Fra fad(e):");
        lblFade.setId("Overskrift3");

        VBox vBox = new VBox(25);
        vBox.getChildren().addAll(lblID2, lblFortynding, lblModning,lblBatchDato,lblBatchMængde, lblBeskrivelse,lblFade);
        this.add(vBox, 3, 2);

        txfID = new TextField();
        txfFortynding = new TextField();
        txfModning = new TextField();
        txfBatchDato = new TextField();
        txfBatchMængde = new TextField();
        txfBeskrivelse = new TextField();
        txfFade = new TextArea();
        txfFade.setPrefWidth(500);

        VBox vBox1 = new VBox(17);
        vBox1.getChildren().addAll(txfID, txfFortynding, txfModning, txfBatchDato, txfBatchMængde, txfBeskrivelse, txfFade);
        this.add(vBox1, 4, 2);
        vBox1.setMaxWidth(300);

        Separator separator1 = new Separator(Orientation.VERTICAL);
        separator1.setMaxHeight(200);
        separator1.setPadding(new Insets(0,40,0,40));
        this.add(separator1, 5, 2);



        Label lblWhisky = new Label("Flasker:");
        lblWhisky.setId("Overskrift2");
        this.add(lblWhisky, 6, 1);
        lvwProdukter = new ListView<>();
        this.add(lvwProdukter, 6, 2);
        lvwProdukter.setMaxWidth(350);
        lvwProdukter.setPrefWidth(350);
        lvwProdukter.setMaxHeight(450);

        ChangeListener<Produkt> listener1 = (ov, oldProdukt, newProdukt) -> this.selectedProduktChanged();
        lvwProdukter.getSelectionModel().selectedItemProperty().addListener(listener1);

        Button btnHistorie = new Button("Se Historie");
        this.add(btnHistorie, 6, 3);
        btnHistorie.setOnAction(event -> this.historieAction());


        Button btnOpret = new Button("Opret Batch");
        btnOpret.setOnAction(event -> this.createAction());

        Button btnSlet = new Button("Slet Batch");
        btnSlet.setOnAction(event -> this.sletAction());

        Button btnTapning = new Button("Tap på flasker");
        btnTapning.setOnAction(event -> this.tapAction());

        HBox hBoxBtn = new HBox(25);
        hBoxBtn.getChildren().addAll(btnOpret, btnSlet, btnTapning);
        this.add(hBoxBtn, 0, 3);

    }

    private void historieAction() {
        if (produkt != null) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setResizable(true);
            alert.setTitle("Whisky Historie");


            GridPane expContent = new GridPane();
            ScrollPane scrollPane = new ScrollPane();

            expContent.setMaxWidth(Double.MAX_VALUE);
            scrollPane.setContent(expContent);


            Label lblFlaskeInfo = new Label("Flaske information:");
            lblFlaskeInfo.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            lblFlaskeInfo.setPadding(new Insets(0, 0, 10, 0));
            expContent.add(lblFlaskeInfo, 0, 0);


            Label lblflaske = new Label(produkt.produktHistorie());
            lblflaske.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
            expContent.add(lblflaske, 0, 1);

            Separator separator = new Separator(Orientation.HORIZONTAL);
            separator.setPadding(new Insets(20, 0, 20, 0));
            expContent.add(separator, 0, 2);

            Label lblWhiskyBatch = new Label("Batch information:");
            lblWhiskyBatch.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            lblWhiskyBatch.setPadding(new Insets(0, 0, 10, 0));
            expContent.add(lblWhiskyBatch, 0, 3);

            Label lblbatch = new Label(produkt.getWhiskyBatch().getHistorie());
            lblbatch.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
            expContent.add(lblbatch, 0, 4);

            Separator separator1 = new Separator(Orientation.HORIZONTAL);
            separator1.setPadding(new Insets(20, 0, 20, 0));
            expContent.add(separator1, 0, 5);

            Label lblFad = new Label("Fad information:");
            lblFad.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            expContent.add(lblFad, 0, 6);

            ArrayList<Fad> fade = new ArrayList<>(whiskyBatch.getFade().keySet());
            for (int i = 0; i < whiskyBatch.getFade().size(); i++) {
                Label lblFadi = new Label(fade.get(i).getHistorie());
                lblFadi.setPadding(new Insets(10, 20, 0, 0));
                expContent.add(lblFadi, i, 7);
                lblFadi.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));

            }

            Separator separator2 = new Separator(Orientation.HORIZONTAL);
            separator2.setPadding(new Insets(20, 0, 20, 0));
            expContent.add(separator2, 0, 8);

            Label lblDestillat = new Label("Destillat information:");
            lblDestillat.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            expContent.add(lblDestillat, 0, 9);



            alert.getDialogPane().setContent(expContent);
            alert.getDialogPane().setPrefSize(800, 600); // Angiver en bredde og højde på Alert-boksen

            ButtonType lukButtonType = ButtonType.CLOSE; // Tilføjer en "Luk"-knap
            alert.getDialogPane().getButtonTypes().add(lukButtonType);
            Button lukButton = (Button) alert.getDialogPane().lookupButton(lukButtonType);
            lukButton.setText("Luk"); // Ændrer knapteksten til "Luk"

            alert.showAndWait();
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
            lvwProdukter.getItems().setAll(whiskyBatch.getProdukter());

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
