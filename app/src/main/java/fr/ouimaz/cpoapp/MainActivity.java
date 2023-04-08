package fr.ouimaz.cpoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ImageView logoImageView = findViewById(R.id.login_page_logo);
        ConstraintLayout constraintlayout = findViewById(R.id.login_page_constraintlayout);

        logoImageView.setOnClickListener(v -> Utils.hideKeyboard( context,  v));
        constraintlayout.setOnClickListener(v -> Utils.hideKeyboard( context,  v));
    }

    public void homepage_login(View view) {

    }

    public void homepage_signup(View view) {

    }

}
