package guifx;

import application.model.Destillat;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class DestillatPane extends GridPane {

    private ListView<Destillat> lvwDestillater;

    public DestillatPane() {
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        initContent();
    }
    private void initContent() {

    }

    private void selectedDestillatChanged() {
        this.updateControls();
    }

    public void updateControls() {

    }

}


