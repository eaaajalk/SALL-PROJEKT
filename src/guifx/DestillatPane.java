package guifx;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.Påfyldning;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DestillatPane extends GridPane {

    private Controller controller;
    private ListView<Destillat> lvwDestillater;
    private TextField txfID1, txfStartDato, txfSlutDato, txfMængde1, txfAlkoholProcent, txfMaltBatch, txfKornSort, txfTørv, txfVandtype, txfMedarbejder, txfKommentar;
    private TextArea txaTønder;
    private Destillat destillat;

    public DestillatPane() {
        controller = Controller.getController();
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        initContent();
    }
    private void initContent() {
        Label lblID = new Label("     ID");
        Label lblFra = new Label("Fra");
        Label lblTil = new Label("Til");
        Label lblMængde = new Label("Mængde");

        HBox hBox = new HBox(80);
        hBox.getChildren().add(lblID);
        hBox.getChildren().add(lblFra);
        hBox.getChildren().add(lblTil);
        hBox.getChildren().add(lblMængde);

        this.add(hBox, 0, 1);

        lvwDestillater = new ListView<>();
        this.add(lvwDestillater, 0, 2, 2, 2);
        lvwDestillater.setMaxWidth(350);
        lvwDestillater.setMaxHeight(520);
        lvwDestillater.getItems().setAll(controller.getDestillater());

        ChangeListener<Destillat> listener = (ov, oldDestillat, newDestillat) -> this.selectedDestillatChanged();
        lvwDestillater.getSelectionModel().selectedItemProperty().addListener(listener);

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setMaxHeight(200);
        separator.setPadding(new Insets(0,10,0,10));
        this.add(separator, 1, 2);

        Label lblID1 = new Label("ID:");
        Label lblStartDato = new Label("Start dato:");
        Label lblSlutDato = new Label("Slut dato:");
        Label lblMængde1 = new Label("Mængde:");
        Label lblAlholProcent = new Label("Alkoholprocent");
        Label lblMaltBatch = new Label("MaltBatch:");
        Label lblKornSort = new Label("Kornsort:");
        Label lblTørv = new Label("Tørv:");
        Label lblVandtype = new Label("Vandtype:");
        Label lblMedarbejder = new Label("Medarbejder:");
        Label lblKommentar = new Label("Kommentar:");
        Label lblTønder = new Label("Tønder:");

        VBox vBox = new VBox(25);
        vBox.getChildren().addAll(lblID1, lblStartDato, lblSlutDato,lblMængde1,lblAlholProcent,lblMaltBatch, lblKornSort, lblTørv, lblVandtype, lblMedarbejder, lblKommentar, lblTønder);
        this.add(vBox, 2, 2);

        txfID1 = new TextField();
        txfStartDato = new TextField();
        txfSlutDato = new TextField();
        txfMængde1 = new TextField();
        txfAlkoholProcent = new TextField();
        txfMaltBatch = new TextField();
        txfKornSort = new TextField();
        txfTørv = new TextField();
        txfVandtype = new TextField();
        txfMedarbejder = new TextField();
        txfKommentar = new TextField();

        txaTønder = new TextArea();
        txaTønder.setMaxHeight(100);
        txaTønder.setMaxWidth(350);


        VBox vBox1 = new VBox(15);
        vBox1.getChildren().addAll(txfID1, txfStartDato, txfSlutDato, txfMængde1, txfAlkoholProcent, txfMaltBatch, txfKornSort, txfTørv, txfVandtype, txfMedarbejder, txfKommentar, txaTønder);
        this.add(vBox1, 3, 2);

        Button btnOpret = new Button("Opret Fad");
        btnOpret.setOnAction(event -> this.createAction());

        Button btnSlet = new Button("Slet Fad");
        btnSlet.setOnAction(event -> this.sletAction());

        Button btnPåfyld = new Button("Hæld på fad");
        btnPåfyld.setOnAction(event -> this.påfyldAction());

        HBox hBoxBtn = new HBox(67);
        hBoxBtn.getChildren().addAll(btnOpret, btnSlet, btnPåfyld);
        this.add(hBoxBtn, 0, 4);
    }

    private void påfyldAction() {
    }

    private void sletAction() {
    }

    private void createAction() {
    }

    private void selectedDestillatChanged() {
        this.updateControls();
    }
    public void updateControls() {
        destillat = lvwDestillater.getSelectionModel().getSelectedItem();
        if (destillat != null) {
            txfID1.setText(destillat.getID());
            txfStartDato.setText(String.valueOf(destillat.getStartDato()));
            txfSlutDato.setText(String.valueOf(destillat.getSlutDato()));
            txfMængde1.setText(String.valueOf(destillat.getMængde()));
            txfAlkoholProcent.setText(String.valueOf(destillat.getAlkoholProcent()));
            txfMaltBatch.setText(destillat.getMaltBatch().getBatchNr());
            txfKornSort.setText(destillat.getMaltBatch().getKornSort());
            txfTørv.setText(destillat.getMaltBatch().getTørv());
            txfVandtype.setText(destillat.getVandType());
            txfMedarbejder.setText(destillat.getMedarbejder().toString());
            txfKommentar.setText(destillat.getKommentar());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < destillat.getPåfyldninger().size(); i++) {
                Påfyldning påfyldning = destillat.getPåfyldninger().get(i);
                for (int j = 0; j < controller.getFade().size(); j++) {
                    if (controller.getFade().get(i).getPåfyldninger().equals(påfyldning)) {
                        sb.append("Fad ID: ").append(controller.getFade().get(i).getID()).append(" Destillat mængde: ").append(påfyldning.getMængde()).append(("\r\n"));
                    }
                }
            }
            txaTønder.setText(sb.toString());
        } else {
            txfID1.clear();
            txfStartDato.clear();
            txfSlutDato.clear();
            txfMængde1.clear();
            txfAlkoholProcent.clear();
            txfMaltBatch.clear();
            txfKornSort.clear();
            txfTørv.clear();
            txfVandtype.clear();
            txfKornSort.clear();
            txfMedarbejder.clear();
            txfKommentar.clear();
            txaTønder.clear();
        }

    }

}


