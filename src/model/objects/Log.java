package model.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import controller.LoginController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

public class Log {
    protected Date timestamp;
    protected String action;
    protected int userId;
    public static ObservableList<Log> loginLogs = FXCollections.observableArrayList();
    public static ObservableList<Log> productLogs = FXCollections.observableArrayList();
    public static ObservableList<Log> catalogueLogs = FXCollections.observableArrayList();
    public static ObservableList<Log> supplierLogs = FXCollections.observableArrayList();

    public Log(String timestamp, String action, String userId) throws ParseException {
        this.timestamp = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy").parse(timestamp);
        this.action = action;
        this.userId = Integer.parseInt(userId);
    }

    public Log(String action) {
        this.timestamp = new Date();
        this.action = action;
        this.userId = LoginController.getInstance().getUserId();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        Predicate<User> userPredicate = user -> user.getUserId() == this.getUserId();
        return User.users.filtered(userPredicate).get(0).username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s", timestamp.toString(), action, String.valueOf(userId));
    }
}
