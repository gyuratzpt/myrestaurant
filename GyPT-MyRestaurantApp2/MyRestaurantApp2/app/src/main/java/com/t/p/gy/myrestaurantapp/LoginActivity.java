package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import com.auth0.android.jwt.JWT;

public class LoginActivity extends AppCompatActivity {
    ProductsBackend myAPI;
    Retrofit retrofit = RetrofitClient.getInstance();
    Gson gson = new GsonBuilder().setLenient().create();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myAPI = retrofit.create(ProductsBackend.class);

        Button loginButton = findViewById(R.id.loginButton);
        Button regButton = findViewById(R.id.regButton);

        final EditText emailEditText = findViewById(R.id.emailEditText);
        final EditText passwordEditText = findViewById(R.id.passwordEditText);
        final TextView forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);

        if (isTokenUserStored()) {
            startLoginActivity();
        }

        loginButton.setOnClickListener(v -> {
            if (areFieldsFilled(emailEditText, passwordEditText))
                loginUser(emailEditText.getText().toString(), passwordEditText.getText().toString());
        });

        regButton.setOnClickListener(v -> {
            if (areFieldsFilled(emailEditText, passwordEditText))
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

    private void loginUser(final String email, final String password){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        findViewById(R.id.loginButton).setEnabled(false);
        findViewById(R.id.loginButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.regButton).setEnabled(false);
        findViewById(R.id.regButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

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
                        startLoginActivity();
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

    private void startLoginActivity(){
        LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private boolean areFieldsFilled(EditText editText1, EditText editText2){
        if (editText1.getText().toString().equals("") || editText2.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "Fields cannot be empty!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean isTokenUserStored(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        final String token = settings.getString("token", "not found");
        final String userString = settings.getString("token", "not found");

        return !token.equals("not found") || !userString.equals("not found");
    }

}
