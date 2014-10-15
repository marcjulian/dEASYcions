package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Gary on 14.10.2014.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread timer = new Thread(){
            public void run(){
                try{
                   sleep(1000);
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
