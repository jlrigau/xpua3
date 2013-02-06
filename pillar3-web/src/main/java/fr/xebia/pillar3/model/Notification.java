package fr.xebia.pillar3.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: olivier
 * Date: 06/02/13
 * Time: 11:06
 * To change this template use File | Settings | File Templates.
 */
public class Notification {
    Date date;
    String message;

    public Notification(Date date, String message) {
        this.date = date;
        this.message = message;
    }
}
