package model;

public class DuplicateException extends Exception {
    // Exception to signify:
    // - duplicate values inserted into the tableview in CatalogueController
    // - duplicate records with the same name/username
    public DuplicateException() {
        super();
    }
}
