package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import de.deasycions.data.CategoryStorage;
import de.deasycions.data.SharedData;

/**
 * Splash Screen of dEASYcions app. Loading data from shared preferences and displaying the app icon.
 *
 * @author Gary Grossgarten
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        SharedData sharedData = new SharedData(this);
        sharedData.loadData();
        Thread timer = new Thread(){
            public void run(){
                try{
                   sleep(4000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally{
                    //Starting the Startpage
                    Intent intent = new Intent("de.deasycions.StartPage");
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
