package guifx;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class PåfyldWindow extends Stage {
    Controller controller;
    Destillat destillat;

    public PåfyldWindow(String title, Destillat destillat) {
        controller = Controller.getController();
        this.destillat = destillat;

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    public PåfyldWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------


    private TextField txfMægnde, txfMedarbejder;
    private Fad fad;
    private ComboBox<Medarbejder> comboBox;
    private Label lblError;
    private DatePicker datePicker;
    private ListView<Fad> lvwFade;
    private Medarbejder medarbejder;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblComp = new Label("Vælg fad");
        pane.add(lblComp, 0, 0);
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

        Label lblMængde = new Label("Mægnde:");
        pane.add(lblMængde, 0, 3);

        txfMægnde = new TextField();
        pane.add(txfMægnde, 0, 4);

        Label lblPladser = new Label("Påfyldningsdato:");
        pane.add(lblPladser, 0, 5);

        datePicker = new DatePicker();
        pane.add(datePicker, 0, 6);

        Label lblMedarbejder = new Label("Medarbejder:");
        pane.add(lblMedarbejder, 0, 7);

        comboBox = new ComboBox<>();
        pane.add(comboBox, 0, 8);
        comboBox.getItems().addAll(controller.getMedarbejdere());
        comboBox.setOnAction(event -> this.MedarbejderAction());

        lblError = new Label(" ");
        pane.add(lblError, 0, 9);

        Button btnOk = new Button("Påfyld");
        pane.add(btnOk, 0, 10);
        GridPane.setHalignment(btnOk, HPos.RIGHT);
        btnOk.setOnAction(event -> this.okAction());

        Button btnCancel = new Button("Annuller");
        pane.add(btnCancel, 0, 10);
        btnCancel.setOnAction(event -> this.cancelAction());


    }

    private void MedarbejderAction() {
        medarbejder = comboBox.getValue();
    }

    private void selectedFadChanged() {
        fad = lvwFade.getSelectionModel().getSelectedItem();
    }


    private void okAction() {
        fad = lvwFade.getSelectionModel().getSelectedItem();
        LocalDate dato = datePicker.getValue();
        int mængde = Integer.parseInt(txfMægnde.getText().trim());
        if (destillat != null) {
            if (dato == null) {
                lblError.setText("Vælg dato");
            } if (mængde < 0) {
                lblError.setText("Indtast mængde i L");
            } if (fad == null) {
                lblError.setText("Marker ønskede fad");
            } if (medarbejder == null) {
                lblError.setText("Vælg medarbejder");
            }  if (mængde > (fad.getStr() - fad.getIndholdsMængde())) {
                    lblError.setText("Der er ikke nok plads i fadet");
                }
                destillat.createPåfyldning(mængde, fad, medarbejder, destillat, dato);
                this.hide();

    }
    }

    private void cancelAction() {
        this.hide();
    }

    // -------------------------------------------------------------------------

}
