package guifx;

import application.controller.Controller;
import application.model.Fad;
import application.model.Omhældning;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class OmhældningsWindow extends Stage {

    private Fad fraFad, tilFad;
    private TextField txfID, txfStr, txfLager, txfHylde, txfKommentar, txfLagerDato, txfFadHistorik;

    Controller controller;
    public OmhældningsWindow(String title, Fad fad) {
        controller = Controller.getController();

        this.fraFad = fad;

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    public OmhældningsWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------

    private TextField txfMængde;
    private DatePicker datePicker;
    private Label lblError;
    private ListView<Fad> lvwFade;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblVælg = new Label("Vælg et fad du vil omhælde til.");
        pane.add(lblVælg, 0, 0);

        Label lblID = new Label("     ID");
        Label lblLager = new Label("   Lager");
        Label lblmodning = new Label("Modningstid (År)");
        Label lblIndhold = new Label("Indhold(L)");

        HBox hBox = new HBox(50);
        hBox.getChildren().add(lblID);
        hBox.getChildren().add(lblIndhold);
        hBox.getChildren().add(lblmodning);
        hBox.getChildren().add(lblLager);
        pane.add(hBox, 0, 1);

        lvwFade = new ListView<>();
        pane.add(lvwFade, 0, 2);
        lvwFade.setPrefWidth(400);
        lvwFade.setMaxHeight(350);
        lvwFade.getItems().setAll(controller.getFade());
        lvwFade.getItems().remove(fraFad);



        ChangeListener<Fad> listener = (ov, oldFad, newFad) -> this.selectedFadChanged();
        lvwFade.getSelectionModel().selectedItemProperty().addListener(listener);
        this.initControls();

        Label lblMængde = new Label("Mængde");
        pane.add(lblMængde, 0, 3);

        txfMængde = new TextField();
        pane.add(txfMængde, 0, 4);

        Label lblDato = new Label("Omhældnings dato");
        pane.add(lblDato, 0, 5);

        datePicker = new DatePicker();
        pane.add(datePicker, 0, 6);


        lblError = new Label(" ");
        pane.add(lblError, 0, 9);

        Button btnOk = new Button("Omhæld");
        pane.add(btnOk, 0, 10);
        GridPane.setHalignment(btnOk, HPos.RIGHT);
        btnOk.setOnAction(event -> this.okAction());

        Button btnCancel = new Button("Annuller");
        pane.add(btnCancel, 0, 10);
        btnCancel.setOnAction(event -> this.cancelAction());
    }

    private void selectedFadChanged() {
        tilFad = lvwFade.getSelectionModel().getSelectedItem();
    }

    private void okAction() {
        int mængde = Integer.parseInt(txfMængde.getText().trim());
        if (tilFad == null) {
            lblError.setText("Vælg et fad");
        }

        if (mængde > tilFad.getResterendePlads()){
            lblError.setText("Det er fucking ikke plads til alt det whisky i det valgte fad");
        }

        if (mængde < 1) {
            lblError.setText("Indtast mængde");
        }
        LocalDate dato = datePicker.getValue();
        if (dato == null) {
            lblError.setText("Vælg nu forhelvede en dato");

        }
        // Call controller methods
        fraFad.createOmhældning(mængde, dato, tilFad);


        this.hide();

    }

    private void cancelAction() {
        this.hide();
    }

    private void initControls() {

    }

    // -------------------------------------------------------------------------



}
