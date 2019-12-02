package model;

public class IllegalInputFormatException extends Exception {
    // Exception to signify:
    // - input is in a wrong format (e.g. contact number, email address)
    public IllegalInputFormatException() {
        super();
    }

    public static class ContactNumber extends IllegalInputFormatException {
        public ContactNumber() {
            super();
        }
    }

    public static class EmailAddress extends IllegalInputFormatException {
        public EmailAddress() {
            super();
        }
    }
}
