package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {
    ProductsBackend myAPI;
    Gson gson = new GsonBuilder().setLenient().create();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Spinner menuSpinner; //spinner objektum
    static final private ArrayList<String> spinnerList = new ArrayList<String>();

    public ArrayList<String> getSpinnerList(){
        return spinnerList;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button adminButton = findViewById(R.id.admin_button);
        spinnerList.add("MENÜ");
        spinnerList.add("Ételek");
        spinnerList.add("Italok");
        spinnerList.add("Cart");

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        final User user = gson.fromJson(settings.getString("user","{}"), User.class);

        if (user.getEmail() == null) {
            logout();
        }

//  spinner (lenyitható lista) peldanyositasa, feltoltese egy ArrayList objektumból, viselkedes beallitas
        menuSpinner  = findViewById(R.id.menu_spinner);
        ArrayAdapter menuSpinnerArrayAdapter = new ArrayAdapter(MainActivity.this, R.layout.spinner_item, getSpinnerList());
        menuSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        menuSpinner.setAdapter(menuSpinnerArrayAdapter);
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                menuSpinner.setSelection(0);
                if (i!=0) {
                    Intent myIntent;
                    switch (i) {
                        case 1:
                            myIntent = new Intent(MainActivity.this, FoodActivity.class);
                            startActivity(myIntent);
                            break;
                        case 2:
                            myIntent = new Intent(MainActivity.this, DrinksActivity.class);
                            startActivity(myIntent);
                            break;
                        case 3:
                            myIntent = new Intent(MainActivity.this, CartActivity.class);
                            startActivity(myIntent);
                            break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
//spinner vege





//Grafikus elemek példányosítása
        ImageView logo_imageView = findViewById(R.id.logo);
        ImageView actual_story_imageView = findViewById(R.id.actual_story);
        ImageView gallery_imageView = findViewById(R.id.gallery);
        ImageView contact_imageView = findViewById(R.id.contact);

//Grafikus elemek forrásának beállítása
        logo_imageView.setImageResource(R.drawable.logo);
        actual_story_imageView.setImageResource(R.drawable.actual_story2);
        gallery_imageView.setImageResource(R.drawable.gallery);
        contact_imageView.setImageResource(R.drawable.contact);






//Akciók implementálása
//  GALLERY megnyitasa
        gallery_imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        GalleryActivity.class);
                startActivity(myIntent);
            }
        });

//  CONTACT megnyitasa
        contact_imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        ContactActivity.class);
                startActivity(myIntent);
            }
        });



        //ADMIN feature
        if(user.getIs_admin() == 1) {
            adminButton.setVisibility(View.VISIBLE);
            adminButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    // Start Admin
                    Intent myIntent = new Intent(MainActivity.this,
                            AdminActivity.class);
                    startActivity(myIntent);
                }
            });
        }
        else {
            adminButton.setVisibility(View.GONE);
        }

//Ellenőrző lépések, csak teszt miatt
        Log.v("MyLog", "Main: finish");
     }
    //konstruktor vége
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void logout() {
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivity(myIntent);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        settings.edit().remove("token").apply();
        settings.edit().remove("user").apply();

        finish();
    }
}

/*
valtoztatni:
- listak osztalybol atemelese, nem beegetve X
- kosar activity X
- elforgatás letiltás X



- inditaskor adatok ellenorzese, ha van ujabb, csere (verziókvetés módszer)
- egy kozos activity minden menuhoz!
- kosár menu elem link
- adatok megadása activity
- áttekintő, véglegesítő activity



- adatbazis csatlakozas
    Adatbázis tartalma:
    egyéb: atuális akció képe
    termékek: uniqe ID, image, name, description, price, category
    felhasználók: name, password, phone nuber, email adress, devlivery address, invoice address






- facebook oldalra link




//https://www.freakyjolly.com/how-to-add-back-arrow-in-android-activity/

*/

/*


2108.10.06-08 layout változtatás gombok hozzáadása

2018.10.14 - dataprocesor probléma, közös list használata globálisan? többszörös öröklődés hiánya miatt
2018.10.24 - cart layout
2018.10.25 - layout átalakítás, recyclerview implementálás kezdés
2018.10.26 - frameview próba, nem vált be, contact redesign



2018.11.04-11 Cart design és algoritmus
2018.11.12 - cart v1 algoritmus (felülír, hozzáad)

 */



