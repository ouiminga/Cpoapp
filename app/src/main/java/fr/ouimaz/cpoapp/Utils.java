package fr.ouimaz.cpoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Utils {

    private static String invalidFieldMessageError = "";
    private static String invalidFieldMessageError2 = "";
    private static String invalidFieldMessageError3 = "";

    /**
     * Utility method to hide the soft keyboard.
     *
     * @param context the context in which the method is being called.
     * @param view    the view whose keyboard needs to be hidden.
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Checks if the given username is valid.
     *
     * @param username The username to be validated.
     * @return true if the username is valid, false otherwise.
     */
    public static boolean isValidUsername(String username, Context context) {
        // Check if username is not null and has at least 6 characters
        if (username != null && username.length() >= 6) {
            // Check if username contains only letters and numbers
            return username.matches("[a-zA-Z0-9]+");
        } else {
            Toast.makeText(context, "Invalid username format. Usernames must be at least 6 characters long and can only contain letters and numbers.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Checks if the given password is valid based on the following rules:
     * - The password must not be null and must have at least 8 characters.
     * - The password must contain at least one uppercase letter, one lowercase letter, and one digit.
     * If the password is not valid, it shows a Toast message to the user with a warning.
     *
     * @param password The password to be validated.
     * @return true if the password is valid, false otherwise.
     */
    public static boolean isValidPassword(String password, Context context) {
        // Check if password is not null and has at least 8 characters
        if (password != null && password.length() >= 8) {
            // Check if password contains at least one uppercase letter, one lowercase letter, and one digit
            if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")) {
                return true;
            } else {
                Toast.makeText(context, "Password must include at least one uppercase letter, one lowercase letter, and one digit.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(context, "Password must have at least 8 characters.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Shows a message to the user that the username format they have entered is not valid.
     *
     * @param context the context in which the message should be shown
     */
    public static void showInvalidUsernameMessage(Context context) {
        Toast.makeText(context, invalidFieldMessageError, Toast.LENGTH_LONG).show();
        Toast.makeText(context, invalidFieldMessageError2, Toast.LENGTH_LONG).show();
        Toast.makeText(context, invalidFieldMessageError3, Toast.LENGTH_LONG).show();
    }

    public static void saveLoginState(Context context, boolean isLoggedIn) {
        SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }
}
