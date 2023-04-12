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

import java.time.LocalDate;
import java.util.*;

public class OpretWhiskyBatchWindow  extends Stage {
    private WhiskyBatch whiskyBatch;
    private Fad fad;
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

    private TextField txfID2, txfFortynding, txfAlkohol, txfBeskrivelse;
    private DatePicker datePicker;
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

        Label lblID = new Label("  ID");
        Label lblHylde = new Label("    Lager");
        Label lblIndhold = new Label("   Indhold(L)");
        Label lblModning = new Label("Modningstid (År)");

        HBox hBox = new HBox(30);
        hBox.getChildren().add(lblID);
        hBox.getChildren().add(lblIndhold);
        hBox.getChildren().add(lblModning);
        hBox.getChildren().add(lblHylde);
        pane.add(hBox, 0, 1);

        lvwFade = new ListView<>();
        pane.add(lvwFade, 0, 2);
        lvwFade.setPrefWidth(450);
        lvwFade.setMaxHeight(200);

        ArrayList<Fad> nulFade = new ArrayList<>();
        for (int i = 0; i < controller.getFade().size(); i++) {
            if (controller.getFade().get(i).getIndholdsMængde() < 1) {
                nulFade.add(controller.getFade().get(i));
            }
        }
        lvwFade.getItems().setAll(controller.getFade());
        lvwFade.getItems().removeAll(nulFade);


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
        txaValgteFade.setMaxHeight(100);

        Label lblID1 = new Label("ID:");
        txfID2 = new TextField();
        pane.add(lblID1, 0, 6);
        pane.add(txfID2, 0, 7);
        txfID2.setMaxWidth(155);


        Label lblFortynding = new Label("Fortyndingsmængde(L):        ");
        txfFortynding = new TextField();
        pane.add(lblFortynding, 0, 6);
        pane.add(txfFortynding, 0, 7);
        txfFortynding.setMaxWidth(155);
        GridPane.setHalignment(lblFortynding, HPos.CENTER);
        GridPane.setHalignment(txfFortynding, HPos.CENTER);


        Label lblAlkohol = new Label("Alkoholprocent:                         ");
        txfAlkohol = new TextField();
        pane.add(lblAlkohol, 0, 6);
        pane.add(txfAlkohol, 0, 7);
        GridPane.setHalignment(lblAlkohol, HPos.RIGHT);
        GridPane.setHalignment(txfAlkohol, HPos.RIGHT);
        txfAlkohol.setMaxWidth(155);

        Label lblDato = new Label("Oprettelses dato:");
        datePicker = new DatePicker();
        pane.add(lblDato, 0, 8);
        pane.add(datePicker, 0, 9);

        Label lblBeskrivelse = new Label("Kommentar:");
        txfBeskrivelse = new TextField();
        pane.add(lblBeskrivelse, 0, 10);
        pane.add(txfBeskrivelse, 0, 11);

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 12);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> this.cancelAction());

        Button btnOK = new Button("Opret");
        pane.add(btnOK, 0, 12);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> this.okAction());

        lblError = new Label();
        pane.add(lblError, 0, 13);
        lblError.setStyle("-fx-text-fill: red");

        this.initControls();
    }

    private void tilføjAction() {
        if (fad != null ) {
            if (fad.getModningsTid() < 3) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Tilføj fad");
                alert.setHeaderText("Du er ved at tilføje et fad der har modnet i mindre end 3 år");
                alert.setContentText("Er du sikker på at du vil fortsætte?");
                Optional<ButtonType> result1 = alert.showAndWait();
                if ((result1.isPresent()) && (result1.get() == ButtonType.OK)) {
                    TextInputDialog popup = new TextInputDialog();
                    popup.setTitle("Tilføj fad");
                    // alert.setContentText("Are you sure?");
                    popup.setHeaderText("Du er ved at tilføje fad " + fad.getID());
                    popup.setContentText("Indast mængde: ");
                    Optional<String> result = popup.showAndWait();

                    if (result.isPresent()) {
                        int mængde = Integer.parseInt(result.get().trim());
                        if (result.get().trim().length() < 1) {
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("Tilføj fad");
                            alert2.setHeaderText("Indtast mængde for at tilføje fadet!");
                            // wait for the modal dialog to close
                            alert2.show();
                        } else {
                            if (mængde > fad.getIndholdsMængde()) {
                                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                                alert3.setTitle("Tilføj fad");
                                alert3.setHeaderText("Du kan ikke tilføje mere end fadets indhold!");
                                // wait for the modal dialog to close
                                alert3.show();
                            } else {
                                fadeMap.put(fad, mængde);
                                selectedFadChanged();
                            }
                        }
                    }
                } else {
                    alert.hide();
                }
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
        String id = txfID2.getText().trim();
        if (id.length() == 0) {
            lblError.setText("ID er ikke udfyld");
        }
        int fortynding = Integer.parseInt(txfFortynding.getText().trim());
        if (txfFortynding.getText().trim().length() == 0) {
            lblError.setText("Indtast fortyndingsmængde");
        }
        int alkoholprocent = Integer.parseInt(txfAlkohol.getText().trim());
        if (alkoholprocent < 1) {
            lblError.setText("Indtast alkoholprocent");
        }

        LocalDate dato = datePicker.getValue();
        if (dato == null) {
            lblError.setText("Vælg en dato");
        }
        String beskrivelse = txfBeskrivelse.getText().trim();

        ArrayList<Fad> keys = new ArrayList<>(fadeMap.keySet());

        if ( keys.size() == 0) {
            lblError.setText("Vælg fad(e)");
        } else {
            WhiskyBatch whiskyBatch1 = controller.createWhiskyBatch(id, fortynding, alkoholprocent, beskrivelse, dato, fadeMap.get(keys.get(0)), keys.get(0));
            if (keys.size() > 1) {
                for (int i = 1; i < keys.size(); i++) {
                    whiskyBatch1.addFad(keys.get(i), fadeMap.get(keys.get(i)));
                }
            }
        }
        this.hide();

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
