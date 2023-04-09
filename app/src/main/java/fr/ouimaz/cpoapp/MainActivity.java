package fr.ouimaz.cpoapp;

import static fr.ouimaz.cpoapp.Utils.hideKeyboard;
import static fr.ouimaz.cpoapp.Utils.isValidPassword;
import static fr.ouimaz.cpoapp.Utils.isValidUsername;
import static fr.ouimaz.cpoapp.Utils.saveLoginState;
import static fr.ouimaz.cpoapp.Utils.showInvalidUsernameMessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorBoundsInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import fr.ouimaz.cpoapp.Utils;

public class MainActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ImageView logoImageView = findViewById(R.id.login_page_logo);
        ConstraintLayout constraintlayout = findViewById(R.id.login_page_constraintlayout);

        logoImageView.setOnClickListener(v -> hideKeyboard(context, v));
        constraintlayout.setOnClickListener(v -> hideKeyboard(context, v));

    }

    public void homepage_login(View view) {

        EditText edt_login = findViewById(R.id.edt_logpage_username);
        EditText edt_password = findViewById(R.id.edt_logpage_password);

        String username = edt_login.getText().toString();
        if (!isValidUsername(username,context)) return;

        String password = edt_password.getText().toString();
        if (!isValidPassword(password, context)) return;

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Login successful
                    // You can perform actions such as starting a new activity or displaying a toast message here
                    // Assume the user has successfully logged in
                    saveLoginState(context, true);
                    // Start the HomeActivity
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    // Finish the LoginActivity so that the user cannot navigate back to it using the back button
                    finish();

                } else {
                    // Login failed
                    Toast.makeText(context, "Login failed. Please check your username and password and try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void homepage_signup(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }


}
