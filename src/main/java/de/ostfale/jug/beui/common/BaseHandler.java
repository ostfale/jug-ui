package de.ostfale.jug.beui.common;

import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

abstract public class BaseHandler<T, U> {

    private static final Logger log = LoggerFactory.getLogger(BaseHandler.class);

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    protected T uiController;
    protected U uiRoot;

    protected abstract String getFXMLPath();

    protected void initHandler() {
        initLoader();
        uiController = getController();
        uiRoot = getUiRoot();
    }

    public T getController() {
        return uiController;
    }

    public U getUiRoot() {
        return uiRoot;
    }

    private void initLoader() {
        final URL resource = ClassLoader.getSystemResource(getFXMLPath());
        fxmlLoader.setLocation(resource);
        try {
            uiRoot = fxmlLoader.load();
            uiController = fxmlLoader.getController();
        } catch (IOException e) {
            log.error("Could not load fxml control");
        }
    }
}
