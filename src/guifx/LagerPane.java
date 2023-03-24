package guifx;

import application.model.Fad;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class LagerPane extends GridPane {

    private ListView<Fad> lvwLagere;

    public LagerPane() {
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);

    }
}
