package guifx;

import application.controller.Controller;
import application.model.Fad;
import application.model.WhiskyBatch;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.*;

public class OpretWhiskyBatchWindow  extends Stage {
    private WhiskyBatch whiskyBatch;
    private Fad fad;
    private TextField txfID, txfStr, txfLager, txfHylde, txfKommentar, txfLagerDato, txfFadHistorik;
    Controller controller;
    public OpretWhiskyBatchWindow(String title, WhiskyBatch whiskyBatch) {
        controller = Controller.getController();

        this.whiskyBatch = whiskyBatch;

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    public OpretWhiskyBatchWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfName, txfHours;
    private Label lblError;
    private ListView<Fad> lvwFade;

    private TextArea txaValgteFade;
    private Map<Fad, Integer> fadeMap = new HashMap<>();
    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblOverskift = new Label("Tilføj fade til dit nye whiskyBatch:");
        pane.add(lblOverskift, 0, 0);

        Label lblID = new Label("     ID");
        Label lblHylde = new Label("        Lager");
        Label lblIndhold = new Label("Indhold(L)");
        Label lblModning = new Label("Modningstid (År)");


        HBox hBox = new HBox(50);
        hBox.getChildren().add(lblID);
        hBox.getChildren().add(lblIndhold);
        hBox.getChildren().add(lblModning);
        hBox.getChildren().add(lblHylde);
        pane.add(hBox, 0, 1);

        lvwFade = new ListView<>();
        pane.add(lvwFade, 0, 2);
        lvwFade.setPrefWidth(450);
        lvwFade.setMaxHeight(350);
        lvwFade.getItems().setAll(controller.getFade());


        ChangeListener<Fad> listener = (ov, oldFad, newFad) -> this.selectedFadChanged();
        lvwFade.getSelectionModel().selectedItemProperty().addListener(listener);

        Button btnTilføjFad = new Button("Tilføj fad");
        pane.add(btnTilføjFad, 0, 3);
        GridPane.setHalignment(btnTilføjFad, HPos.RIGHT);
        btnTilføjFad.setOnAction(event -> this.tilføjAction());

        Label lblFade = new Label("Tilføjede fade");
        pane.add(lblFade, 0, 4);

        txaValgteFade = new TextArea();
        pane.add(txaValgteFade, 0, 5);


        this.initControls();
    }

    private void tilføjAction() {
        TextInputDialog popup = new TextInputDialog();
        popup.setTitle("Tilføj fad");
        // alert.setContentText("Are you sure?");
        popup.setHeaderText("Du er ved at tilføje fad " + fad.getID());
        Optional<String> result = popup.showAndWait();
        if (result.isPresent()) {
            int mængde = Integer.parseInt(result.get().trim());
            if (result.get().trim().length() > 0) {
                if (mængde <= fad.getIndholdsMængde()) {
                    fadeMap.put(fad, mængde);
                    selectedFadChanged();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Tilføj fad");
                    alert.setHeaderText("Du kan ikke tilføje mere end fadets indhold!");
                    // wait for the modal dialog to close
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tilføj fad");
                alert.setHeaderText("Indtast mængde for at tilføje fadet!");
                // wait for the modal dialog to close
                alert.show();
            }
        }

    }

    private void selectedFadChanged() {
        ArrayList<Fad> keys = new ArrayList<>(fadeMap.keySet());
        fad = lvwFade.getSelectionModel().getSelectedItem();

        lvwFade.getItems().removeAll(keys);
        initControls();
    }

    private void okAction() {

    }

    private void cancelAction() {
        this.hide();
    }

    private void initControls() {
        ArrayList<Fad> keys = new ArrayList<>(fadeMap.keySet());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fadeMap.size(); i++) {
            sb.append("Fad ID: ").append(keys.get(i).getID()).append(" | Mængde tilføjet: ").append(fadeMap.get(keys.get(i))).append("\n");
        }
        txaValgteFade.setText(String.valueOf(sb));
    }

    // -------------------------------------------------------------------------




}
