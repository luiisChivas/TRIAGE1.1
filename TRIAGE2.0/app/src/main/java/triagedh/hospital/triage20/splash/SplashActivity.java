package triagedh.hospital.triage20.splash;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import triagedh.hospital.triage20.R;
import triagedh.hospital.triage20.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends AppCompatActivity {

    private  static  final  long SPLASE_SCREEN_DELAY= 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Aplicar orientacion del imagen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //ocultar barra de titulo
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        TimerTask task =new TimerTask() {
            @Override
            public void run() {
                //iniciar mainActivity
                finish();
                Intent mainIntent= new Intent().setClass(SplashActivity.this, LoginActivity.class);
                startActivity(mainIntent);
            }
        };
        //simula la carga de un proceso en la aplicacion
        Timer timer=new Timer();
        timer.schedule(task,SPLASE_SCREEN_DELAY);
    }
}

