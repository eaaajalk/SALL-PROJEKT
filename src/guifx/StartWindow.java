package guifx;

import application.controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartWindow extends Application {

    @Override
    public void init() {
        Controller controller = Controller.getController();
        controller.init();
    }


    @Override
    public void start(Stage stage) {
        stage.setTitle("Sall");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    // -------------------------------------------------------------------------

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        Tab tabFade = new Tab("Fade");
        tabPane.getTabs().add(tabFade);

        FadPane fadPane = new FadPane();
        tabFade.setContent(fadPane);
        tabFade.setOnSelectionChanged(event -> fadPane.updateControls());

        Tab tabLager = new Tab("Lager");
        tabPane.getTabs().add(tabLager);

        LagerPane lagerPane = new LagerPane();
        tabLager.setContent(lagerPane);
        tabLager.setOnSelectionChanged(event -> lagerPane.updateControls());

        Tab tabDestillat = new Tab("Destillater");
        tabPane.getTabs().add(tabDestillat);

        DestillatPane destillatPane = new DestillatPane();
        tabDestillat.setContent(destillatPane);
        tabDestillat.setOnSelectionChanged(event -> destillatPane.updateControls());
    }
}
