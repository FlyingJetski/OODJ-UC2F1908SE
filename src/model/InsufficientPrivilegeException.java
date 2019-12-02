package model;

public class InsufficientPrivilegeException extends Exception {
    // Exception to signify:
    // - admin's inability to reset another admin's password
    public InsufficientPrivilegeException() {
        super();
    }
}
