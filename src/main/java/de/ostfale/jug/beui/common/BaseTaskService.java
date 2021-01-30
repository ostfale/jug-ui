package de.ostfale.jug.beui.common;

import javafx.concurrent.Service;

/**
 * Base class for all service tasks
 * Created :  06.08.2020
 *
 * @author : Uwe Sauerbrei
 */
abstract public class BaseTaskService<T> {

    protected boolean hasBeenStarted = false;

    protected Service<T> service;

    public void startService() {
        if (hasBeenStarted) {
            service.restart();
        } else {
            hasBeenStarted = true;
            service.start();
        }
    }

    public Service<T> getService() {
        return service;
    }
}
