package guifx;

import application.controller.Controller;
import application.model.Fad;
import application.model.Hylde;
import application.model.Lager;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class LagerPane extends GridPane {
    private Controller controller;
    private Lager lager;
    private Hylde hylde;

    private ListView<Lager> lvwLagere;
    private ListView<Hylde> lvwHylder;
    private TextArea txtFade;

    public LagerPane() {
        controller = Controller.getController();
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        initContent();
    }

    private void initContent() {
        Label lblLagere = new Label("Lagere:");
        this.add(lblLagere, 0, 0);

        lvwLagere = new ListView<>();
        this.add(lvwLagere, 0, 1);
        lvwLagere.setPrefWidth(250);
        lvwLagere.setMaxHeight(350);
        lvwLagere.getItems().setAll(controller.getLagere());

        ChangeListener<Lager> listener = (ov, oldLager, newLager) -> this.selectedLagerChanged();
        lvwLagere.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblHylder = new Label("Hylder:");
        this.add(lblHylder, 2, 0);

        lvwHylder = new ListView<>();
        this.add(lvwHylder, 2, 1);
        lvwHylder.setPrefWidth(250);
        lvwHylder.setMaxHeight(350);

        ChangeListener<Hylde> listener1 = (ov, oldHylde, newHylde) -> this.selectedHyldeChanged();
        lvwHylder.getSelectionModel().selectedItemProperty().addListener(listener1);

        txtFade = new TextArea();
        this.add(txtFade, 4, 1);
        txtFade.setPrefWidth(250);
        txtFade.setMaxHeight(350);

        Button btnCreateLager = new Button("Opret Lager");
        btnCreateLager.setOnAction(event -> this.createLagerAction());
        Button btnCreateHylde = new Button("Opret hylde");
        btnCreateHylde.setOnAction(event -> this.createHyldeAction());

        this.add(btnCreateLager, 0, 4);
        this.add(btnCreateHylde, 2, 4);


    }

    private void createHyldeAction() {
        lager = lvwLagere.getSelectionModel().getSelectedItem();
        if (lager != null) {
            OpretHyldeWindow dia = new OpretHyldeWindow("Opret lager", lager);
            dia.showAndWait();
            // Wait for the modal dialog to close
            lvwHylder.getItems().setAll(lager.getHylder());
            int index = lvwHylder.getItems().size() - 1;
            lvwHylder.getSelectionModel().select(index);
        }
    }

    private void createLagerAction() {
        OpretLagerWindow dia = new OpretLagerWindow("Opret lager", lager);
        dia.showAndWait();
        // Wait for the modal dialog to close
        lvwLagere.getItems().setAll(controller.getLagere());
        int index = lvwLagere.getItems().size() - 1;
        lvwLagere.getSelectionModel().select(index);
    }

    private void selectedLagerChanged() {
        lager = lvwLagere.getSelectionModel().getSelectedItem();
        if (lager != null) {
            lvwHylder.getItems().setAll(lager.getHylder());
        }
    }

    private void selectedHyldeChanged() {
        hylde = lvwHylder.getSelectionModel().getSelectedItem();
        if (hylde != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= hylde.getFadeMap().size(); i++) {
                String id = "";
                Fad fad1 = hylde.getFadeMap().get(i);
                if (fad1 == null) {
                    id = "Ledig";
                } else {
                    id = "FadID: " + fad1.getID();
                }
                sb.append("Plads: ").append(i + "   ").append("   =   ").append(id).append("\r\n");
            }
            txtFade.setText(sb.toString());
        }
    }


    public void updateControls() {


    }

}
