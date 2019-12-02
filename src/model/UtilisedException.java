package model;

public class UtilisedException extends Exception {
    // Exception to signify:
    // - record is being used in other records
    public UtilisedException() {
        super();
    }
}
