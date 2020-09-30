package com.t.p.gy.myrestaurantapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.t.p.gy.myrestaurantapp.connection.ProductsBackend;
import com.t.p.gy.myrestaurantapp.data.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Gson gson = new GsonBuilder().setLenient().create();

    private Spinner menuSpinner; //spinner objektum
    static final private ArrayList<String> spinnerList = new ArrayList<String>(); //adatbázisból lehúyni

    public ArrayList<String> getSpinnerList(){
        return spinnerList;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);

        Button adminMaintenanceButton = findViewById(R.id.admin_maintenancebutton);
        Button adminOrdersButton = findViewById(R.id.admin_ordersbutton);

        spinnerList.add("MENÜ");
        spinnerList.add("Ételek");
        spinnerList.add("Italok");
        spinnerList.add("Cart");

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        final User user = gson.fromJson(settings.getString("user","{}"), User.class);

        if (user.getEmail() == null) {
            logout();
        }

//Grafikus elemek példányosítása
        ImageView logo_imageView = findViewById(R.id.logo);
        ImageView actual_story_imageView = findViewById(R.id.actual_story);
        ImageView menu_imageView = findViewById(R.id.menu);
        ImageView gallery_imageView = findViewById(R.id.gallery);
        ImageView contact_imageView = findViewById(R.id.contact);
//Grafikus elemek forrásának beállítása
        logo_imageView.setImageResource(R.drawable.logo);
        actual_story_imageView.setImageResource(R.drawable.actual_story2);
        menu_imageView.setImageResource(R.drawable.menu);
        gallery_imageView.setImageResource(R.drawable.gallery);
        contact_imageView.setImageResource(R.drawable.contact);


        //initSpinner();
        initButtons(menu_imageView, gallery_imageView, contact_imageView);

        //ADMIN feature
        if(user.getIs_admin() == 1) {
            adminMaintenanceButton.setVisibility(View.VISIBLE);
            adminOrdersButton.setVisibility(View.VISIBLE);
            adminMaintenanceButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    // Start Admin
                    Intent myIntent = new Intent(MainActivity.this,
                            AdminMaintenanceActivity.class);
                    startActivity(myIntent);
                }
            });
            adminOrdersButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    // Start Admin
                    Intent myIntent = new Intent(MainActivity.this,
                            AdminOrdersActivity.class);
                    startActivity(myIntent);
                }
            });
        }
        else {

            adminMaintenanceButton.setVisibility(View.GONE);
            adminOrdersButton.setVisibility(View.GONE);

        }


        //Ellenőrző lépések, csak teszt miatt
        Log.v("MyLog", "Main: finish");
     }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cart:
                Intent myIntent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(myIntent);
                return true;
            case R.id.logout:
                Toast.makeText(this, "Kilépés", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initButtons(ImageView menu_imageView, ImageView gallery_imageView, ImageView contact_imageView) {
        //  MENU megnyitasa
        menu_imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        MenuActivity.class);
                startActivity(myIntent);
            }
        });

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
                Intent myIntent = new Intent(MainActivity.this,
                        ContactActivity.class);
                startActivity(myIntent);
            }
        });
    }
    /*
    private void initSpinner() {
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
                            myIntent = new Intent(MainActivity.this, DrinkActivity.class);
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
    }
    */



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
            foods.add(new SingleMenuItem(24, "Normál hamburger", "150gr marhahús pogácsa, paradicsom, uborka, sajt", 1600, R.drawable.hamburger));
            foods.add(new SingleMenuItem(25, "Extra hamburger", "300gr marhahús pogácsa, paradicsom, uborka, sajt", 2100, R.drawable.extrahamburger));
            foods.add(new SingleMenuItem(26, "Dupla hamburger", "2x300gr marhahús pogácsa, paradicsom, uborka, sajt", 3300, R.drawable.duplahamburger));
            foods.add(new SingleMenuItem(27, "Döner Kebab", "borjúhús, paradicsom, uborka, káposzta, öntet, házi pitában", 900, R.drawable.donerkebab));
            foods.add(new SingleMenuItem(28, "Dürüm", "borjúhús, paradicsom, uborka, káposzta, öntet, tortillalapban", 900, R.drawable.durum));
            foods.add(new SingleMenuItem(29, "Dürüm2", "csirkehús, paradicsom, uborka, káposzta, öntet, tortillalapban", 900, R.drawable.durum));
            foods.add(new SingleMenuItem(30, "Dürüm3", "borjúhús, hagyma, paradicsom, uborka, káposzta, öntet, tortillalapban", 900, R.drawable.durum));
            foods.add(new SingleMenuItem(31, "Dürüm4", "csirkehús, hagyma, paradicsom, uborka, káposzta, öntet, tortillalapban", 900, R.drawable.durum));
            foods.add(new SingleMenuItem(32, "Kakaós tekercs", "sima, egyszerű kakaóscsiga - yetiknek", 240, R.drawable.csiga));

            drinks.add(new SingleMenuItem(2, "Fanta", "szensavas", 250, R.drawable.fanta));
            drinks.add(new SingleMenuItem(3, "Sprite", "szensavas", 250, R.drawable.sprite));
            drinks.add(new SingleMenuItem(4, "Hell", "energiaital", 300, R.drawable.hell));
            drinks.add(new SingleMenuItem(5, "Birra Moretti", "olasz lotty", 450, R.drawable.birramoretti));
            drinks.add(new SingleMenuItem(9, "Corona", "kukoricasor", 660, R.drawable.corona));
            drinks.add(new SingleMenuItem(11, "Kilkenny", "vegre egy sor", 730, R.drawable.kilkenny));
            drinks.add(new SingleMenuItem(13, "Stella Artois", "egynek jo", 550, R.drawable.stella));
            drinks.add(new SingleMenuItem(14, "Wizard", "ismeretlen", 890, R.drawable.wizard));
             */


/*

2020.06: user

2020.02-03: adatbázis első verzió, drink, food

2019.03: spinner, activity v1


2018.11.04-11 Cart design és algoritmus
2018.11.12 - cart v1 algoritmus (felülír, hozzáad)


2018.10.14 - dataprocesor probléma, közös list használata globálisan? többszörös öröklődés hiánya miatt
2018.10.24 - cart layout
2018.10.25 - layout átalakítás, recyclerview implementálás kezdés
2018.10.26 - frameview próba, nem vált be, contact redesign

2018.10.06-08 layout változtatás gombok hozzáadása



 */



