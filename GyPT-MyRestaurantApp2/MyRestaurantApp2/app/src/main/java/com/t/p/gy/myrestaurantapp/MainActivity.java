package com.t.p.gy.myrestaurantapp;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private Spinner menuSpinner; //spinner objektum

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataProcessor dp = new DataProcessor();

//  lenyitható lista peldanyositasa(spinner), feltoltese egy ArrayList objektumból, viselkedes beallitas
        menuSpinner  = findViewById(R.id.menu_spinner);
        ArrayAdapter menuSpinnerArrayAdapter = new ArrayAdapter(MainActivity.this, R.layout.spinner_item, dp.getSpinnerList());
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


//Ellenőrző lépések
        Log.v("Main", "OK");
        for (SingleMenuItem x : dp.getFoodsList()){
            Log.v("Food tartalma", x.getName());
        }
        for (SingleMenuItem x : dp.getDrinksList()){
            Log.v("Drinks tartalma", x.getName());
        }
        for (SingleMenuItem x : dp.getCart()){
            Log.v("Cart tartalma", x.getName());
        }
    }
    //konstruktor vége
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



