package guifx;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.Omhældning;
import application.model.Påfyldning;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class FadPane extends GridPane {
    private ListView<Fad> lvwFade;
    private TextField txfID, txfStr, txfLager, txfHylde, txfKommentar, txfLagerDato, fadHistorik;
    private TextArea txaDestillater;
    private Fad fad;
    private ComboBox<Object> comboBox;
    private Controller controller;

    public FadPane() {
        controller = Controller.getController();
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setAlignment(Pos.CENTER);
        initContent();

    }

    private void initContent() {
        Label lblOverskrift = new Label("Fad Oversigt");
        this.add(lblOverskrift, 0, 0);
        lblOverskrift.setId("PaneOverskrift");
        lblOverskrift.setPadding(new Insets(0, 0, 20, 0));

        Label lblComp = new Label("Sorter efter");
        this.add(lblComp, 0, 1);
        comboBox = new ComboBox<>();
        lblComp.setId("Overskrift2");

        this.add(comboBox, 0, 2);
        for (int i = 0; i < controller.getLagere().size(); i++) {
            comboBox.getItems().add("" + controller.getLagere().get(i));
        }
        comboBox.getItems().add("Ikke placerede fade");
        comboBox.getItems().add("Alle fade");


        comboBox.setOnAction(event -> this.sortAction());

        Label lblID = new Label(" ID");
        lblID.setPadding(new Insets(10, 0, 0, 0));
        Label lblHylde = new Label("        Lager");
        lblHylde.setPadding(new Insets(10, 0, 0, 0));
        Label lblIndhold = new Label(" Indhold(L)");
        lblIndhold.setPadding(new Insets(10, 0, 0, 0));
        Label lblModning = new Label("Modningstid (År)");
        lblModning.setPadding(new Insets(10, 0, 0, 0));
        HBox hBox = new HBox(30);
        hBox.getChildren().add(lblID);
        hBox.getChildren().add(lblIndhold);
        hBox.getChildren().add(lblModning);
        hBox.getChildren().add(lblHylde);
        this.add(hBox, 0, 3);
        lblID.setId("Overskrift2");
        lblHylde.setId("Overskrift2");
        lblIndhold.setId("Overskrift2");
        lblModning.setId("Overskrift2");

        lvwFade = new ListView<>();
        this.add(lvwFade, 0, 4);
        lvwFade.setPrefWidth(450);
        lvwFade.setMaxHeight(450);
        lvwFade.getItems().setAll(controller.getFade());

        ChangeListener<Fad> listener = (ov, oldFad, newFad) -> this.selectedFadChanged();
        lvwFade.getSelectionModel().selectedItemProperty().addListener(listener);

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setMaxHeight(200);
        separator.setPadding(new Insets(0,40,0,40));
        this.add(separator, 2, 4);

        Label lblID2 = new Label("ID:");
        lblID2.setId("Overskrift3");
        Label lblStr = new Label("Str (L):");
        lblStr.setId("Overskrift3");
        Label lblLager2 = new Label("Lager:");
        lblLager2.setId("Overskrift3");
        Label lblHylde2 = new Label("Hylde:");
        lblHylde2.setId("Overskrift3");
        Label lblDato = new Label("Lagrings Dato:");
        lblDato.setId("Overskrift3");
        Label lblKommentar = new Label("Kommentar:");
        lblKommentar.setId("Overskrift3");
        Label lblHistorik = new Label("Fad historik:      ");
        lblHistorik.setId("Overskrift3");
        Label lblDestillat = new Label("Indhold:");
        lblDestillat.setId("Overskrift3");

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

        VBox vBox1 = new VBox(16);
        vBox1.getChildren().addAll(txfID,txfStr,txfLager,txfHylde,txfLagerDato,txfKommentar, fadHistorik, txaDestillater);
        this.add(vBox1, 4, 4);

        Label lblinf = new Label("Fad information:");
        this.add(lblinf, 3, 3, 2, 1);
        lblinf.setId("Overskrift2");


        Button btnOpret = new Button("Opret Fad");
        btnOpret.setOnAction(event -> this.createAction());

        Button btnSlet = new Button("Slet Fad");
        btnSlet.setOnAction(event -> this.sletAction());

        Button btnPlacer = new Button("Placer/Flyt Fad");
        btnPlacer.setOnAction(event -> this.placerAction());

        Button btnOmhæld = new Button("Omhæld Fad");
        btnOmhæld.setOnAction(event -> this.omhældAction());

        HBox hBoxBtn = new HBox(50);
        hBoxBtn.getChildren().addAll(btnOpret, btnSlet, btnPlacer, btnOmhæld);
        this.add(hBoxBtn, 0, 5);

    }

    private void omhældAction() {
        if (fad != null) {
            OmhældningsWindow dia = new OmhældningsWindow("Omhæld Fad", fad);
            dia.showAndWait();

            // Wait for the modal dialog to close
            lvwFade.getItems().setAll(controller.getFade());
        }

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
                alert.setHeaderText("Vælg et fad");
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
            txfID.setText(String.valueOf(fad.getID()));
            txfStr.setText(String.valueOf(fad.getStr()));
            txfKommentar.setText(fad.getKommentar());

            if (fad.getHylde() != null) {
                txfLager.setText(String.valueOf(fad.getHylde().getLager()));
                txfHylde.setText(fad.getHylde().toString());
                txfLagerDato.setText(String.valueOf(fad.getLagringsDato()));
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < fad.getFadHistorik().size(); i++) {
                sb.append(fad.getFadHistorik().get(i)).append("   |   ");
            }
            fadHistorik.setText(sb.toString());

            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();

            for (int i = 0; i < fad.getPåfyldninger().size(); i++) {
                Påfyldning påfyldning = fad.getPåfyldninger().get(i);
                sb1.append("Den ").append(påfyldning.getPåfyldningsDato()).append(" er påfyldt ").append
                        (påfyldning.getMængde()).append("L destillatID: ").append(påfyldning.getDestillat().getID()).append("\n");
            }
            for (int i = 0; i < fad.getOmhældninger().size(); i++) {
                Omhældning omhældning = fad.getOmhældninger().get(i);
                sb2.append("Den ").append(omhældning.getOmhældningsDato()).append(" er omhældt ").append
                        (omhældning.getMængde()).append("L fra fad ID: ").append(omhældning.getFraFad().getID()).append("\n");


            }
            txaDestillater.setText("Påfyldninger: \n" + sb1.toString() + "\n \nOmhældninger: \n" + sb2.toString());


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
