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
import com.t.p.gy.myrestaurantapp.connection.BackendAPI;
import com.t.p.gy.myrestaurantapp.connection.RetrofitClient;
import com.t.p.gy.myrestaurantapp.data.DataProcessor;
import com.t.p.gy.myrestaurantapp.data.User;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Gson gson = new GsonBuilder().setLenient().create(); //???
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    BackendAPI myAPI; //interface deklarálás a BackendAPI felé
    Retrofit retrofit = RetrofitClient.getInstance(); //retrofit library
    DataProcessor myDataProcessor = DataProcessor.getInstance();

    Button loginButton, regButton, regButtonFinish;
    EditText emailEditText, passwordEditText,regETItems[];
    TextView forgotPasswordTextView, regTVItems[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(myDataProcessor.getDrawableMap().isEmpty()){
            myDataProcessor.setDrawableMap(initDrawableMap());
        }
        initUI();
        initButtons();

        //res/drawable file-ok int id kiolvasása. Más módszer??


        myAPI = retrofit.create(BackendAPI.class); //retrofit library-t adja a productbackand api-hoz??

        //felhasználó ellenőrzése
        myDataProcessor.initSP(LoginActivity.this);
        if (myDataProcessor.checkUserToken()) startMainActivity();

                /*
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        //settings.edit().clear().apply();
        //token törlése (ezt mikor (logout felső menübe))?
                */
    }

    private void initUI(){
        loginButton = (Button) findViewById(R.id.loginButton);
        regButton = findViewById(R.id.regButton);
        regButtonFinish = findViewById(R.id.regButtonFinish);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        regTVItems = new TextView[]{findViewById(R.id.regNameText), findViewById(R.id.regAddressText), findViewById(R.id.regPhoneNumberText)};
        regETItems = new EditText[]{findViewById(R.id.regNameEdittext), findViewById(R.id.regAddressEdittext), findViewById(R.id.regPhoneNumberEdittext)};

        for (TextView tv : regTVItems) {
            tv.setVisibility(View.INVISIBLE);
        }
        for (EditText et : regETItems) {
            et.setVisibility(View.INVISIBLE);
        }
        regButtonFinish.setVisibility(View.INVISIBLE);
    }

    private void initButtons() {
        loginButton.setOnClickListener(v -> {
            if (areFieldsFilled(emailEditText, passwordEditText)) //megnézi nem üres-e valamelyik mező
                loginUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
        });

        regButton.setOnClickListener(v -> {

                    if (areFieldsFilled(emailEditText, passwordEditText)){ //megnézi nem üres-e valamelyik mező
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

                            }
                            registerUser(emailEditText.getText().toString(), passwordEditText.getText().toString(), regETItems[0].getText().toString(), regETItems[1].getText().toString(), regETItems[2].getText().toString());
                        });
                }

        });
    }

    private Map initDrawableMap(){
        Map<String, Integer> drawableMap = new HashMap<>();
        drawableMap.put("birramoretti", this.getResources().getIdentifier("birramoretti","drawable", this.getPackageName()));
        drawableMap.put("cola", this.getResources().getIdentifier("cola","drawable", this.getPackageName()));
        drawableMap.put("corona", this.getResources().getIdentifier("corona","drawable", this.getPackageName()));
        drawableMap.put("csiga", this.getResources().getIdentifier("csiga","drawable", this.getPackageName()));
        drawableMap.put("donerkebab", getApplicationContext().getResources().getIdentifier("donerkebab","drawable", getPackageName()));
        drawableMap.put("duplahamburger", getApplicationContext().getResources().getIdentifier("duplahamburger","drawable", getPackageName()));
        drawableMap.put("durum", getApplicationContext().getResources().getIdentifier("durum","drawable", getPackageName()));
        drawableMap.put("extrahamburger", getApplicationContext().getResources().getIdentifier("extrahamburger","drawable", getPackageName()));
        drawableMap.put("fanta", getApplicationContext().getResources().getIdentifier("fanta","drawable", getPackageName()));
        drawableMap.put("hamburger", getApplicationContext().getResources().getIdentifier("hamburger","drawable", getPackageName()));
        drawableMap.put("hell", getApplicationContext().getResources().getIdentifier("hell","drawable", getPackageName()));
        drawableMap.put("kilkenny", getApplicationContext().getResources().getIdentifier("kilkenny","drawable", getPackageName()));
        drawableMap.put("sprite", getApplicationContext().getResources().getIdentifier("sprite","drawable", getPackageName()));
        drawableMap.put("stella", getApplicationContext().getResources().getIdentifier("stella","drawable", getPackageName()));
        drawableMap.put("wizard", getApplicationContext().getResources().getIdentifier("wizard","drawable", getPackageName()));
        drawableMap.put("noimage", getApplicationContext().getResources().getIdentifier("noimage","drawable", getPackageName()));
        return drawableMap;
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

        //!!!!!!
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
    private boolean areRegFieldsFilled(EditText editTextArray[]){
        if (editTextArray[0].getText().toString().equals("") || editTextArray[1].getText().toString().equals("")|| editTextArray[2].getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "Valamelyik adat még hiányzik", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
    private void startMainActivity(){
        //LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }







    private void loginUser_original(final String email, final String password){
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

    private void loginUser(final String email, final String password){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        findViewById(R.id.loginButton).setEnabled(false);
        findViewById(R.id.loginButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.regButton).setEnabled(false);
        findViewById(R.id.regButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        //ha nem lenne túl gyors, eltűnének a gombok és megjelenne egy haladásjelző?!

        myDataProcessor.loginUser(password);




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
    private void registerUser(final String email,
                              final String password,
                              final String name,
                              final String address,
                              final String phone){
        Log.i("myLog", "registerUser running...");
        Log.i("myLog", "Adatok: " + emailEditText.getText().toString() + " " + passwordEditText.getText().toString() + " " + regETItems[0].getText().toString()+ " " + regETItems[1].getText().toString()+ " " + regETItems[2].getText().toString());

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
}