package com.platine.liveresto.restaurant;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.platine.liveresto.R;
import com.platine.liveresto.db.RestaurantDAO;
import com.platine.liveresto.model.Horaire;
import com.platine.liveresto.model.Restaurant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nath on 17/01/2017.
 * RestaurantActivity
 */
public class RestaurantActivity extends AppCompatActivity {

    //Attributs
    private TextView title,type,ouvert,di,lu,ma,me,je,ve,sa,adresse,internet,phoneNum;
    private ImageView imgResto;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        //Init views
        this.title = (TextView) findViewById(R.id.toolbar_title_resto);
        this.imgResto = (ImageView) findViewById(R.id.image_restaurant);
        this.type = (TextView) findViewById(R.id.type_resto);
        this.ouvert = (TextView) findViewById(R.id.ouvert_resto);
        this.di = (TextView) findViewById(R.id.dimanche);
        this.lu = (TextView) findViewById(R.id.lundi);
        this.ma = (TextView) findViewById(R.id.mardi);
        this.me = (TextView) findViewById(R.id.mercredi);
        this.je = (TextView) findViewById(R.id.jeudi);
        this.ve = (TextView) findViewById(R.id.vendredi);
        this.sa = (TextView) findViewById(R.id.samedi);
        this.adresse = (TextView) findViewById(R.id.adresse_resto);
        this.internet = (TextView) findViewById(R.id.internet_site);
        this.phoneNum = (TextView) findViewById(R.id.phone_text);


        //----------- Get Restaurant -------------------
        Intent i = getIntent();
        String title = i.getStringExtra("title");
        RestaurantDAO rdao = new RestaurantDAO(getApplicationContext());
        Restaurant r  = rdao.getRestaurantByName(title);

        //--------------------------------------------------------------

        String name = r.getName();
        String adress = r.getAdress();
        String website = r.getWebsite();
        String img = r.getPicture();
        String phone = r.getPhoneNumber();
        //Vérify open or close
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("HH.mm");
        String s = f.format(d);
        double heureCourante = Double.parseDouble(s);
        boolean flag = false;

        Calendar cal = Calendar.getInstance();
        String jour = "";
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        switch(dayOfWeek){
            case 1 :
                jour = "DI";
                break;
            case 2 :
                jour = "LU";
                break;
            case 3 :
                jour = "MA";
                break;
            case 4 :
                jour = "ME";
                break;
            case 5 :
                jour = "JE";
                break;
            case 6 :
                jour = "VE";
                break;
            case 7 :
                jour = "SA";
                break;
        }

        boolean luB=false,maB=false,meB=false,jeB=false,veB=false,saB=false,diB=false;
        ArrayList<Horaire> schedule = r.getShedule();

        for (Horaire h : schedule) {
            String day = h.getDay();
            double b = h.getBeginHour();
            double e = h.getEndHour();
            if(day.equals(jour)) {
                if ((heureCourante > b) && (e > heureCourante)) {
                    flag = true;
                }
            }
            String begin = h.getSchedule().substring(3,8);
            String end = h.getSchedule().substring(9,14);
            //Format data
            begin = begin.replace('.',':');
            end = end.replace('.',':');

            switch(day){
                case "LU":
                    if(luB){
                        this.lu.setText(this.lu.getText()+" "+begin+"-"+end);
                    } else {
                        this.lu.setText(begin+"-"+end);
                        this.lu.setTextColor(Color.BLACK);
                        this.lu.setTypeface(Typeface.DEFAULT);
                    }
                    luB = true;
                    break;
                case "MA":
                    if(maB){
                        this.ma.setText(this.ma.getText()+" "+begin+"-"+end);
                    } else {
                        this.ma.setText(begin+"-"+end);
                        this.ma.setTextColor(Color.BLACK);
                        this.ma.setTypeface(Typeface.DEFAULT);
                    }
                    maB = true;
                    break;
                case "ME":
                    if(meB){
                        this.me.setText(this.me.getText()+" "+begin+"-"+end);
                    } else {
                        this.me.setText(begin+"-"+end);
                        this.me.setTextColor(Color.BLACK);
                        this.me.setTypeface(Typeface.DEFAULT);
                    }
                    meB = true;
                    break;
                case "JE":
                    if(jeB){
                        this.je.setText(this.je.getText()+" "+begin+"-"+end);
                    } else {
                        this.je.setText(begin+"-"+end);
                        this.je.setTextColor(Color.BLACK);
                        this.je.setTypeface(Typeface.DEFAULT);
                    }
                    jeB = true;
                    break;
                case "VE":
                    if(veB){
                        this.ve.setText(this.ve.getText()+" "+begin+"-"+end);
                    } else {
                        this.ve.setText(begin+"-"+end);
                        this.ve.setTextColor(Color.BLACK);
                        this.ve.setTypeface(Typeface.DEFAULT);
                    }
                    veB = true;
                    break;
                case "SA":
                    if(saB){
                        this.sa.setText(this.sa.getText()+" "+begin+"-"+end);
                    } else {
                        this.sa.setText(begin+"-"+end);
                        this.sa.setTextColor(Color.BLACK);
                        this.sa.setTypeface(Typeface.DEFAULT);
                    }
                    saB = true;
                    break;
                case "DI":
                    if(diB){
                        this.di.setText(this.di.getText()+" "+begin+"-"+end);
                    } else {
                        this.di.setText(begin+"-"+end);
                        this.di.setTextColor(Color.BLACK);
                        this.di.setTypeface(Typeface.DEFAULT);
                    }
                    diB = true;
                    break;
            }
        }

        //Init toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_resto);
        setSupportActionBar(myToolbar);
        setTitle("");

        //Listener Toolbar
        backArrow = (ImageView) findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Implement view with Data
        this.title.setText(name);
        this.imgResto.setImageResource(getResources().getIdentifier(img, "drawable", getPackageName()));
        String type = r.getType();
        String[] split = type.split(",");
        String typeFinal="";
        for(String t : split){
            switch(t){
                case "fastfood" :
                    t="Fast-Food";
                    break;
                case "pizzeria" :
                    t= "Pizzeria";
                    break;
                case "halal" :
                    t= "Halal";
                    break;
                case "brasserie" :
                    t= "Brasserie";
                    break;
                case "vegetarien" :
                    t= "Végétarien";
                    break;
                case "gastronomique" :
                    t= "Gastronomique";
                    break;
                case "bio" :
                    t= "Bio";
                    break;
                case "casher" :
                    t= "Casher";
                    break;
                case "italien" :
                    t= "Italien";
                    break;
                case "chinois" :
                    t= "Chinois";
                    break;
            }
            typeFinal+=t+" ";
        }
        this.type.setText(typeFinal);
        if(flag) {
            this.ouvert.setText("Ouvert");
        } else {
            this.ouvert.setText("Fermé");
            this.ouvert.setTextColor(Color.RED);
        }
        this.adresse.setText(adress);
        this.internet.setText(website);
        this.phoneNum.setText(phone);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
