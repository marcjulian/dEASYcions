package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import de.deasycions.data.CategoryStorage;
import de.deasycions.data.SharedData;

/**
 * @author Gary Grossgarten
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        SharedData sharedData = new SharedData(this);
        sharedData.loadData();
        sharedData.clearSharedPreferences();
        Thread timer = new Thread(){
            public void run(){
                try{
                   sleep(4000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally{

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
