package fr.ouimaz.cpoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    Context context;
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final Locale LOCALE = Locale.getDefault();
    private static final Pattern STREET_PATTERN = Pattern.compile("^\\d+\\s[A-z]+\\s[A-z]+");
    private static final Pattern CITY_PATTERN = Pattern.compile("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$");
    private static final Pattern STATE_PATTERN = Pattern.compile("^([A-Za-z]{2})$|^([A-Za-z]+(?:\\s[A-Za-z]+)*)$");
    private static final Pattern COUNTRY_PATTERN = Pattern.compile("^[A-Za-z\\s]+$");
    private static final Pattern ZIP_PATTERN = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?$");

    private String firstname, lastname, username, email, password, passwordConfirm, dateOfBirth, gender, street, city, state, country, zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        context = this;
    }

    public void signupPageGoTohomepage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void signup(View view) {

        if (!allFieldsAreTheyValid()) return;

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.put("firstname", firstname);
        user.put("lastname", lastname);
        user.put("dateOfBirth", dateOfBirth);
        user.put("gender", gender);
        user.put("street", street);
        user.put("city", city);
        user.put("state", state);
        user.put("country", country);
        user.put("zip", zip);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Sign up successful

                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Sign up failed
                    Log.e("Sign up", "Sign up failed: " + e.getMessage(), e);
                    Toast.makeText(getApplicationContext(), "Sign up failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean allFieldsAreTheyValid() {
        TextInputEditText editText;
        editText = findViewById(R.id.signup_page_first_name_edit_text);
        firstname = editText.getText().toString();
        if (!isItValid(firstname)) {
            toastInvalidField("Invalid first name");
            return false;
        }

        editText = findViewById(R.id.signup_page_last_name_edit_text);
        lastname = editText.getText().toString();
        if (!isItValid(lastname)) {
            toastInvalidField("Invalid last name");
            return false;
        }

        editText = findViewById(R.id.signup_page_edit_text_name);
        username = editText.getText().toString();
        if (!Utils.isValidUsername(username, context)) {
            toastInvalidField("Invalid username");
            return false;
        }

        editText = findViewById(R.id.signup_page_edit_text_email);
        email = editText.getText().toString();
        if (!isValidEmail(email)) {
            toastInvalidField("Invalid email");
            return false;
        }

        editText = findViewById(R.id.signup_page_edit_text_password);
        password = editText.getText().toString();
        if (!Utils.isValidPassword(password, context)) {
            toastInvalidField("Invalid password");
            return false;
        }

        editText = findViewById(R.id.signup_page_edit_text_confirm_password);
        passwordConfirm = editText.getText().toString();
        if (!password.equals(passwordConfirm)) {
            toastInvalidField("Password confirm doesn't match");
            return false;
        }

        editText = findViewById(R.id.signup_page_dob_edit_text);
        dateOfBirth = editText.getText().toString();
        if (!isValidDate(dateOfBirth)) {
            toastInvalidField("Invalid date");
            return false;
        }

        RadioGroup genderRadioGroup = findViewById(R.id.signup_page_gender_radio_group);
        switch (genderRadioGroup.getCheckedRadioButtonId()) {
            case R.id.signup_page_male_radio_button:
                gender = "male";
                break;
            case R.id.signup_page_female_radio_button:
                gender = "female";
                break;
            case R.id.signup_page_not_indicated_radio_button:
                gender = "not indicated";
                break;
            default:
                gender = "";
                break;
        }

        editText = findViewById(R.id.signup_page_street);
        street = editText.getText().toString();
        if (!isValidStreet(street)) {
            toastInvalidField("Invalid street name");
            return false;
        }

        editText = findViewById(R.id.signup_page_city);
        city = editText.getText().toString();
        if (!isValidCity(city)) {
            toastInvalidField("Invalid city name");
            return false;
        }

        editText = findViewById(R.id.signup_page_state);
        state = editText.getText().toString();
        if (!isValidState(state)) {
            toastInvalidField("Invalid state name");
            return false;
        }

        editText = findViewById(R.id.signup_page_country);
        country = editText.getText().toString();
        if (!isValidCountry(country)) {
            toastInvalidField("Invalid country name");
            return false;
        }

        editText = findViewById(R.id.signup_page_zip);
        zip = editText.getText().toString();
        if (!isValidZip(zip)) {
            toastInvalidField("Invalid zip code");
            return false;
        }

        return true;
    }

    private boolean isItValid(String name) {

        // Check if the first name is null or empty
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        // Check if the first name contains only letters and spaces
        if (!name.matches("[a-zA-Z ]+")) {
            return false;
        }

        return true;
    }

    private boolean arePasswordsMatching(String password1, String password2) {
        if (password1 == null || password2 == null) {
            // If either password field is null, return false
            return false;
        }
        // Compare the two password fields and return true if they match
        return password1.equals(password2);
    }

    public boolean isValidEmail(String email) {
        // Check if the email is null or empty
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Regular expression to match an email address
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email.trim());

        // Check if the email matches the regular expression
        return matcher.matches();
    }

    public void showDatePickerDialog(View view) {
        // Create a new instance of DatePickerDialog and set the date picker listener
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
            // Set the selected date in the EditText field
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, LOCALE);
            String dateString = dateFormat.format(calendar.getTime());
            EditText dobEditText = findViewById(R.id.signup_page_dob_edit_text);
            dobEditText.setText(dateString);
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        // Set the maximum date to today's date
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        // Show the date picker dialog
        datePickerDialog.show();
    }

    private boolean isValidDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, LOCALE);
        format.setLenient(false);
        try {
            Date date = format.parse(dateStr);
            return true;
        } catch (java.text.ParseException e) {
            return false;
        }
    }

    public boolean isValidStreet(String street) {
        if (street == null || street.trim().isEmpty()) {
            return false;
        }
        Matcher matcher = STREET_PATTERN.matcher(street.trim());
        return matcher.matches();
    }

    public boolean isValidCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            return false;
        }
        Matcher matcher = CITY_PATTERN.matcher(city.trim());
        return matcher.matches();
    }

    public boolean isValidState(String state) {
        if (state == null || state.trim().isEmpty()) {
            return false;
        }
        Matcher matcher = STATE_PATTERN.matcher(state.trim());
        return matcher.matches();
    }

    public boolean isValidCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            return false;
        }
        Matcher matcher = COUNTRY_PATTERN.matcher(country.trim());
        return matcher.matches();
    }

    public boolean isValidZip(String zip) {
        if (zip == null || zip.trim().isEmpty()) {
            return false;
        }
        Matcher matcher = ZIP_PATTERN.matcher(zip.trim());
        return matcher.matches();
    }

    public void toastInvalidField(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}