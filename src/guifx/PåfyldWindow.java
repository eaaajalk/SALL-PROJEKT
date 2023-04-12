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
import java.util.ArrayList;

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
        scene.getStylesheets().add("application/style.css");
    }

    public PåfyldWindow(String title) {
        this(title, null);
    }

    // -------------------------------------------------------------------------


    private TextField txfMægnde;
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
        lblComp.setId("Overskrift2");

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
        pane.add(hBox, 0, 1);
        lblID.setId("Overskrift2");
        lblHylde.setId("Overskrift2");
        lblIndhold.setId("Overskrift2");
        lblModning.setId("Overskrift2");

        lvwFade = new ListView<>();
        pane.add(lvwFade, 0, 2);
        lvwFade.setPrefWidth(500);
        lvwFade.setMaxHeight(450);

        ArrayList<Fad> nullFade = new ArrayList<>();
        for (int i = 0; i < controller.getFade().size(); i++) {
            Fad f1 = controller.getFade().get(i);
            if (f1.getIndholdsMængde() >= f1.getStr()) {
                nullFade.add(f1);
            }
        }
        lvwFade.getItems().setAll(controller.getFade());
        lvwFade.getItems().removeAll(nullFade);

        ChangeListener<Fad> listener = (ov, oldFad, newFad) -> this.selectedFadChanged();
        lvwFade.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblMængde = new Label("Mægnde:");
        lblMængde.setId("Overskrift3");
        pane.add(lblMængde, 0, 3);

        txfMægnde = new TextField();
        pane.add(txfMægnde, 0, 4);

        Label lblPladser = new Label("Påfyldningsdato:");
        lblPladser.setId("Overskrift3");
        pane.add(lblPladser, 0, 5);

        datePicker = new DatePicker();
        pane.add(datePicker, 0, 6);

        Label lblMedarbejder = new Label("Medarbejder:");
        lblMedarbejder.setId("Overskrift3");
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
