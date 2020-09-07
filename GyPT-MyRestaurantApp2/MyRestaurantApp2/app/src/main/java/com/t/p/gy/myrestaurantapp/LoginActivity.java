package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import com.auth0.android.jwt.JWT;
import com.t.p.gy.myrestaurantapp.connection.ProductsBackend;
import com.t.p.gy.myrestaurantapp.connection.RetrofitClient;

public class LoginActivity extends AppCompatActivity {
    Gson gson = new GsonBuilder().setLenient().create(); //???
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    ProductsBackend myAPI; //interface deklarálás a ProductsBackend felé
    Retrofit retrofit = RetrofitClient.getInstance(); //retrofit library

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //elemek inicializálása
        Button loginButton = findViewById(R.id.loginButton);
        Button regButton = findViewById(R.id.regButton);
        final EditText emailEditText = findViewById(R.id.emailEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        final TextView forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        //elemek inicializálása vége

        myAPI = retrofit.create(ProductsBackend.class); //retrofit library-t adja a productbackand api-hoz??

        //        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
//        settings.edit().clear().apply();
        //token törlése (ezt mikor (logout felső menübe))?
        if (isTokenUserStored()) {
            startMainActivity();
        } //ha van tárolt token, továbbugrik a main-re

        initButtons(loginButton, regButton, emailEditText, passwordEditText);

    }

    private void initButtons(Button loginButton, Button regButton, EditText emailEditText, EditText passwordEditText) {
        loginButton.setOnClickListener(v -> {
            if (areFieldsFilled(emailEditText, passwordEditText)) //megnézi nem üres-e valamelyik mező
                loginUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
        });

        regButton.setOnClickListener(v -> {
            if (areFieldsFilled(emailEditText, passwordEditText)) //megnézi nem üres-e valamelyik mező
                registerUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
        });
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
    private boolean isTokenUserStored(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        final String token = settings.getString("token", "not found");
        //final String userString = settings.getString("token", "not found");//kétszer van a "token" key, helyette:
        final String userString = settings.getString("user", "not found");

        return !token.equals("not found") || !userString.equals("not found");
    }
    private boolean areFieldsFilled(EditText editText1, EditText editText2){
        Log.i("myLog", "Metódus: areFieldsFilled running...");
        if (editText1.getText().toString().equals("") || editText2.getText().toString().equals("")) {
            StringBuilder tmpStr = new StringBuilder();
            Log.i("myLog", tmpStr.toString());
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
    private void loginUser(final String email, final String password){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        findViewById(R.id.loginButton).setEnabled(false);
        findViewById(R.id.loginButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.regButton).setEnabled(false);
        findViewById(R.id.regButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        //ha nem lenne túl gyors, eltűnének a gombok és megjelenne egy haladásjelző?!

        compositeDisposable.add(myAPI.login(email, password) //myAPI, tehát a productsbackend felé adja az adatokat
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
    private void registerUser(final String email, final String password){
        Log.i("myLog", "Metódus: registerUser running...");
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
    private void startMainActivity(){
        //LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}

//új user-nél crash
