package com.t.p.gy.myrestaurantapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class LoginActivity extends AppCompatActivity {
    //DataProcessor myDataProcessor = DataProcessor.getInstance();
    DataProcessor myDataProcessor;
    //Gson gson = new GsonBuilder().setLenient().create(); //???
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    //Retrofit retrofit = RetrofitClient.getInstance(); //retrofit library
    //BackendAPI myAPI = retrofit.create(BackendAPI.class); //interface deklarálás a BackendAPI felé

    //UI
    Button loginButton, regButton, regButtonFinish;
    EditText emailEditText, passwordEditText,regETItems[];
    TextView forgotPasswordTextView, regTVItems[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        //settings.edit().clear().apply();

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        ImageView mainLogoImageView = (ImageView) findViewById(R.id.mainLogoImageView);
        if(isConnected){
            myDataProcessor = DataProcessor.getInstance();
            mainLogoImageView.setImageBitmap(DataProcessor.getInstance().getImage("UI","logo"));
            Log.i("myLog", "van net! :)");
            initUI();
            //felhasználó ellenőrzése
            myDataProcessor.initSP(LoginActivity.this);
            tryLogin("Activity");
        }
        else{
            Log.i("myLog", "nincs net! :(");
            mainLogoImageView.setImageResource(R.drawable.nointernet);
            Toast.makeText(this, "Az alkalmazás használatához internetkapcsolat szükséges!", Toast.LENGTH_LONG).show();
        }


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

        loginButton.setOnClickListener(v -> {
            if (areFieldsFilled(emailEditText, passwordEditText))
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


    private void startMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
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

    private void loginUser(final String email, final String password){
        myDataProcessor.loginUserV2(email, password);
        tryLogin("loginUser");
    }

    private void registerUser(final String email,
                              final String password,
                              final String name,
                              final String address,
                              final String phone){
        Log.i("myLog", "registerUser running...");
        Log.i("myLog", "Adatok: " + emailEditText.getText().toString() + " " + passwordEditText.getText().toString() + " " + regETItems[0].getText().toString()+ " " + regETItems[1].getText().toString()+ " " + regETItems[2].getText().toString());
        myDataProcessor.registerUserV2(email, password, name, address, phone);
        myDataProcessor.loginUserV2(email, password);
        tryLogin("registerUser");
    }

    private void tryLogin(String _source){
        if (myDataProcessor.checkUserToken()) startMainActivity();
        else Log.i("myLog", "A(z) " + _source + " nem talál érvényes felhasználót!");
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

}