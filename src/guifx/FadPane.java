package guifx;

import application.controller.Controller;
import application.model.Fad;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class FadPane extends GridPane {
    private ListView<Fad> lvwFade;
    private TextField txfID, txfStr, txfLager, txfHylde, txfKommentar, txfLagerDato;
    private TextArea txfFadHistorik;
    private Fad fad;

    private ComboBox<Object> comboBox;

    private Controller controller;

    public FadPane() {
        controller = Controller.getController();
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        initContent();
    }

    private void initContent() {
        Label lblComp = new Label("  Sorter efter");
        this.add(lblComp, 0, 0);
        comboBox = new ComboBox<>();

        this.add(comboBox, 0, 1);
        for (int i = 0; i < controller.getLagere().size(); i++) {
            comboBox.getItems().add("" + controller.getLagere().get(i));
        }

        comboBox.getItems().add("Ikke placerede fade");
        comboBox.getItems().add("Alle fade");

        comboBox.setOnAction(event -> this.sortAction());

        Label lblID = new Label("     ID");
        Label lblHylde = new Label("Hylde");
        Label lblPlads = new Label("Plads");
        Label lblLager = new Label("Lager");

        HBox hBox = new HBox(80);
        hBox.getChildren().add(lblID);
        hBox.getChildren().add(lblHylde);
        hBox.getChildren().add(lblPlads);
        hBox.getChildren().add(lblLager);

        this.add(hBox, 0, 3);

        lvwFade = new ListView<>();
        this.add(lvwFade, 0, 4, 2, 4);
        lvwFade.setPrefWidth(400);
        lvwFade.setMaxHeight(350);
        lvwFade.getItems().setAll(controller.getFade());

        ChangeListener<Fad> listener = (ov, oldFad, newFad) -> this.selectedFadChanged();
        lvwFade.getSelectionModel().selectedItemProperty().addListener(listener);

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setMaxHeight(200);
        separator.setPadding(new Insets(0,20,0,20));
        this.add(separator, 2, 4);

        Label lblID2 = new Label("ID:");
        Label lblStr = new Label("Str:");
        Label lblLager2 = new Label("Lager:");
        Label lblHylde2 = new Label("Hylde:");
        Label lblDato = new Label("Lagerdato:");
        Label lblKommentar = new Label("Kommentar:");
        Label lblHistorik = new Label("Fad historik:      ");

        VBox vBox = new VBox(25);
        vBox.getChildren().addAll(lblID2, lblStr, lblLager2,lblHylde2,lblDato,lblKommentar,lblHistorik);
        this.add(vBox, 3, 4);

        txfID = new TextField();
        txfStr = new TextField();
        txfLager = new TextField();
        txfHylde = new TextField();
        txfLagerDato = new TextField();
        txfKommentar = new TextField();
        txfFadHistorik = new TextArea();
        txfFadHistorik.setMaxWidth(350);
        txfFadHistorik.setMaxHeight(100);

        VBox vBox1 = new VBox(15);
        vBox1.getChildren().addAll(txfID,txfStr,txfLager,txfHylde,txfLagerDato,txfKommentar,txfFadHistorik);
        this.add(vBox1, 4, 4);


        Button btnOpret = new Button("Opret Fad");
        btnOpret.setOnAction(event -> this.createAction());

        Button btnSlet = new Button("Slet Fad");
        btnSlet.setOnAction(event -> this.sletAction());

        Button btnPlacer = new Button("Placer/Flyt Fad");
        btnPlacer.setOnAction(event -> this.placerAction());

        HBox hBoxBtn = new HBox(80);
        hBoxBtn.getChildren().addAll(btnOpret, btnSlet, btnPlacer);
        this.add(hBoxBtn, 0, 8);

    }

    private void sletAction() {
        fad = lvwFade.getSelectionModel().getSelectedItem();
        if (fad != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Slet fad");
                // alert.setContentText("Are you sure?");
                alert.setHeaderText("Er du sikker på at slette fad med ID: " + fad.getID() + " og fjerne det fra lageret?");
                Optional<ButtonType> result = alert.showAndWait();
                if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                    controller.removeFadFromLager(fad);
                    controller.deleteFad(fad);
                    lvwFade.getItems().setAll(controller.getFade());
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
        if (fad != null) {
            boolean placeret = fad.getHylde() != null;

            PlacerFadWindow dia = new PlacerFadWindow("Placer/Flyt fad", fad, placeret);
            dia.showAndWait();

            // Wait for the modal dialog to close
            lvwFade.getItems().setAll(controller.getFade());
        }

    }

    private void createAction() {
        OpretFadWindow dia = new OpretFadWindow("Opret fad");
        dia.showAndWait();
        // Wait for the modal dialog to close
        lvwFade.getItems().setAll(controller.getFade());
        int index = lvwFade.getItems().size() - 1;
        lvwFade.getSelectionModel().select(index);
    }

    private void sortAction() {

    }
    // -------------------------------------------------------------------------

    private void selectedFadChanged() {
        this.updateControls();
    }

    public void updateControls() {
        fad = lvwFade.getSelectionModel().getSelectedItem();
        if (fad != null) {
            txfID.setText(fad.getID());
            txfStr.setText(String.valueOf(fad.getStr()));
            txfKommentar.setText(fad.getKommentar());

            if (fad.getHylde() != null) {
                txfLager.setText(String.valueOf(fad.getHylde().getLager()));
                txfHylde.setText(fad.getHylde().toString());
                txfLagerDato.setText(String.valueOf(fad.getLagerDato()));
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < fad.getFadHistorik().size(); i++) {

                sb.append(fad.getFadHistorik().get(i)).append("\r\n");
            }
            txfFadHistorik.setText(sb.toString());
        } else {
            txfID.clear();
            txfStr.clear();
            txfLager.clear();
            txfHylde.clear();
            txfKommentar.clear();
            txfLagerDato.clear();
            txfFadHistorik.clear();
        }

    }





}
