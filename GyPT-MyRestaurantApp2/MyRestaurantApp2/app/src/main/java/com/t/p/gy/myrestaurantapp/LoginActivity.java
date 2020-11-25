package com.t.p.gy.myrestaurantapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import io.reactivex.disposables.CompositeDisposable;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;


public class LoginActivity extends AppCompatActivity {
    DataProcessor myDataProcessor;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    //UI
    Button loginButton, regButton, regButtonFinish;
    EditText emailEditText, passwordEditText,regETItems[];
    TextView forgotPasswordTextView, regTVItems[];
    ImageView mainLogoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(checkInternet()){
            myDataProcessor = DataProcessor.getInstance();
            Log.i("myLog", "van net! :)");
            initUI(true);
            //felhasználó ellenőrzése
            myDataProcessor.initSP(LoginActivity.this);
            tryLogin("LoginActivity");
        }
        else{
            Log.i("myLog", "nincs net! :(");
            initUI(false);
            Toast.makeText(this, "Az alkalmazás használatához internetkapcsolat szükséges!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    private boolean checkInternet(){
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private void initUI(boolean _env){
        mainLogoImageView = (ImageView) findViewById(R.id.mainLogoImageView);
        loginButton = (Button) findViewById(R.id.loginButton);
        regButton = findViewById(R.id.regButton);
        regButtonFinish = findViewById(R.id.regButtonFinish);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        forgotPasswordTextView.setVisibility(View.GONE);
        regTVItems = new TextView[]{findViewById(R.id.regNameText), findViewById(R.id.regAddressText), findViewById(R.id.regPhoneNumberText)};
        regETItems = new EditText[]{findViewById(R.id.regNameEdittext), findViewById(R.id.regAddressEdittext), findViewById(R.id.regPhoneNumberEdittext)};
        for (TextView tv : regTVItems) {
            tv.setVisibility(View.INVISIBLE);
        }
        for (EditText et : regETItems) {
            et.setVisibility(View.INVISIBLE);
        }
        regButtonFinish.setVisibility(View.INVISIBLE);

        if(_env) {
            mainLogoImageView.setImageBitmap(DataProcessor.getInstance().getImage("UI","logo"));

            loginButton.setOnClickListener(v -> {
                if (areFieldsFilled(emailEditText, passwordEditText))
                    loginUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
            });
            regButton.setOnClickListener(v -> {

                if (areFieldsFilled(emailEditText, passwordEditText)) { //megnézi nem üres-e valamelyik mező
                    Toast.makeText(LoginActivity.this, "Add meg a további kötelező adatokat a regisztrációhoz!", Toast.LENGTH_LONG).show();
                    for (TextView tv : regTVItems) {
                        tv.setVisibility(View.VISIBLE);
                    }
                    for (EditText et : regETItems) {
                        et.setVisibility(View.VISIBLE);

                    }
                    regButtonFinish.setVisibility(View.VISIBLE);
                    regButtonFinish.setOnClickListener(view -> {
                        if (areRegFieldsFilled(regETItems)) {
                            registerUser(emailEditText.getText().toString(), passwordEditText.getText().toString(), regETItems[0].getText().toString(), regETItems[1].getText().toString(), regETItems[2].getText().toString());
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Tölts ki minden mezőt!", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            });
        }
        else{
            mainLogoImageView.setImageResource(R.drawable.nointernet);
            emailEditText.setEnabled(false);
            passwordEditText.setEnabled(false);
            loginButton.setClickable(false);
            regButton.setClickable(false);
        }
    }

    private boolean areFieldsFilled(EditText editText1, EditText editText2){
        Log.i("myLog", "Metódus: areFieldsFilled running...");
        if (editText1.getText().toString().equals("") || editText2.getText().toString().equals("")) {
            StringBuilder tmpStr = new StringBuilder();
            if (editText1.getText().toString().equals("")) tmpStr.append("A név");
            if (editText2.getText().toString().equals("") && tmpStr.toString().equals("")) tmpStr.append("A jelszó");
            else if (editText2.getText().toString().equals("") && !tmpStr.toString().equals("")) tmpStr.append(" és a jelszó");
            tmpStr.append(" üres! Töltse ki a hiányzó mezőt!");
            if (editText1.getText().toString().equals("") && editText2.getText().toString().equals("")) tmpStr.insert(48, "ke");
            Toast.makeText(LoginActivity.this, tmpStr.toString(), Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean areRegFieldsFilled(EditText editTextArray[]){
        if (editTextArray[0].getText().toString().equals("") || editTextArray[1].getText().toString().equals("")|| editTextArray[2].getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "Valamelyik adat még hiányzik", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private void loginUser(final String email, final String password){
        myDataProcessor.loginUserV2(this, email, password);
        Handler h = new Handler();
        Runnable r = () -> {
            tryLogin("loginUser()");
        };
        h.postDelayed(r, 500);
    }

    private void registerUser(final String _email,
                              final String _password,
                              final String _username,
                              final String _address,
                              final String _phonenumber){
        Log.i("myLog", "registerUser running...");
        Log.i("myLog", "Adatok: " + emailEditText.getText().toString() + " " + passwordEditText.getText().toString() + " " + regETItems[0].getText().toString()+ " " + regETItems[1].getText().toString()+ " " + regETItems[2].getText().toString());
        myDataProcessor.registerUserV2(this, _email, _password, _username, _address, _phonenumber);
        /*
        Handler h = new Handler();
        Runnable r = () -> {
            tryLogin("registerUser()");
        };
        h.postDelayed(r, 500);

         */
    }

    private void tryLogin(String _source){
        if (myDataProcessor.checkUserToken()) startMainActivity();
        else Log.i("myLog", "A(z) " + _source + " nem talál érvényes felhasználót!");
    }

    private void startMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}













    /*
    private void loginUser_original(final String email, final String password){
        myAPI = retrofit.create(BackendAPI.class); //retrofit library-t adja a productbackand api-hoz??
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        findViewById(R.id.loginButton).setEnabled(false);
        findViewById(R.id.loginButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.regButton).setEnabled(false);
        findViewById(R.id.regButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        //ha nem lenne túl gyors, eltűnének a gombok és megjelenne egy haladásjelző?!

        compositeDisposable.add(myAPI.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        String token = response.body().get("token").getAsString();
                        User user = new User();
                        JWT jwt = new JWT(token);
                        user.setEmail(jwt.getClaim("email").asString());
                        user.setIs_admin(jwt.getClaim("is_admin").asInt());
                        settings.edit().putString("token", token).apply();
                        settings.edit().putString("user", gson.toJson(user)).apply();
                        startMainActivity();
                    } else {
                        Toast.makeText(LoginActivity.this, response.code() + " " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                        findViewById(R.id.loginButton).setEnabled(true);
                        findViewById(R.id.loginButton).setVisibility(View.VISIBLE);
                        findViewById(R.id.regButton).setEnabled(true);
                        findViewById(R.id.regButton).setVisibility(View.VISIBLE);
                    }
                }));
    }

    private void registerUser_original(final String email,
                              final String password,
                              final String name,
                              final String address,
                              final String phone){
        Log.i("myLog", "registerUser running...");
        Log.i("myLog", "Adatok: " + emailEditText.getText().toString() + " " + passwordEditText.getText().toString() + " " + regETItems[0].getText().toString()+ " " + regETItems[1].getText().toString()+ " " + regETItems[2].getText().toString());

        myAPI = retrofit.create(BackendAPI.class); //retrofit library-t adja a productbackand api-hoz??
        compositeDisposable.add(myAPI.registration(email, password, name, address, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Toast.makeText(LoginActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, response.code() + " " + response.errorBody().string(), Toast.LENGTH_LONG).show();
                    }
                }));
    }
    */
        /*
    private void registerUser(final String email, final String password){
        Log.i("myLog", "registerUser running...");
        compositeDisposable.add(myAPI.registration(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() >= 200 && response.code() < 300) {
                        Toast.makeText(LoginActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, response.code() + " " + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }
    */
