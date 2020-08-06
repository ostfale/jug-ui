package de.ostfale.jug.beui.ui.person;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * Provides layout for person processing.
 * Created :  06.08.2020
 *
 * @author : Uwe Sauerbrei
 */
public class PersonLayout {

    private final Pane layoutPane;

    public PersonLayout() {
        layoutPane = initLayout();
    }

    public Pane getLayoutPane() {
        return layoutPane;
    }

    private Pane initLayout() {
        final URL resource = ClassLoader.getSystemResource("./fxml/person_master_detail.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(resource);
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
