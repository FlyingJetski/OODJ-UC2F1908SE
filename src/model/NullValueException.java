package model;

public class NullValueException extends Exception {
    // Exception to signify:
    // - there are required fields that are empty
    public NullValueException() {
        super();
    }

    public static class Date extends NullValueException {
        public Date() {
            super();
        }
    }

    public static class Product extends NullValueException {
        public Product() {
            super();
        }
    }
}
