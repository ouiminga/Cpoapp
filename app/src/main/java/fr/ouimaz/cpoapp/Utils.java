package fr.ouimaz.cpoapp;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {

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
}
