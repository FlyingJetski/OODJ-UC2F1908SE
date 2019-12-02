package model;

import java.util.ArrayList;

public class RegularExpressionPattern {
    private static ArrayList<String> contactNumberPatternStringArrayList = new ArrayList<>();
    private static String emailAddressPatternString = "([a-zA-Z0-9]+)@([a-zA-Z0-9]+)\\.([a-zA-Z]+)";

    public static ArrayList<String> getContactNumberPatternStringArrayList () {
        contactNumberPatternStringArrayList.add("\\+([0-9&&[^a-zA-Z]]+)-([0-9&&[^a-zA-Z]]+)");
        contactNumberPatternStringArrayList.add("([0-9&&[^a-zA-Z]]+)-([0-9&&[^a-zA-Z]]+)");
        contactNumberPatternStringArrayList.add("\\+([0-9&&[^a-zA-Z]]+)");
        contactNumberPatternStringArrayList.add("([0-9&&[^a-zA-Z]]+)");
        return contactNumberPatternStringArrayList;
    }

    public static String getEmailAddressPatternString () {
        return emailAddressPatternString;
    }
}
