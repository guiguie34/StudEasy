package com.github.studeasy.logic.utils;
import java.util.regex.*;
public class regexUtils {

    private static Pattern pattern;
    private static Matcher matcher;

    /**
     * Regex for mail
     * test.example@gmail.com works
     * test@gmail.com works
     * test@yo don't works
     */
    private static final String email_regex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    /**
     * Regex for the password
     * At least one digit
     * At least one lower case letter
     * At least one upper case letter
     * At least one special character in @#$%^&+=*
     * No whitespace
     * At least 8 character
     */
    private static final String password_regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{8,}$";

    public static boolean matches_mail(String input){
        pattern = Pattern.compile(email_regex);
        matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static boolean matches_password(String input){
        pattern = Pattern.compile(password_regex);
        matcher = pattern.matcher(input);
        return matcher.find();
    }
}