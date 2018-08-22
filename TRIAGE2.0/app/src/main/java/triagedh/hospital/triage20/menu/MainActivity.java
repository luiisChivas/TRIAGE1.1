package triagedh.hospital.triage20.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import triagedh.hospital.triage20.IncomeTab.IncomeTabActivity;
import triagedh.hospital.triage20.R;
import triagedh.hospital.triage20.about.FragmentAbout;
import triagedh.hospital.triage20.about.YouKnowActivity;
import triagedh.hospital.triage20.book.FragmentShowBookMTS;
import triagedh.hospital.triage20.disease.FragmentListDisease;
import triagedh.hospital.triage20.legend.FragmentListLegend;
import triagedh.hospital.triage20.level.FragmentClasificationLevel;
import triagedh.hospital.triage20.level.FragmentListLevel;
import triagedh.hospital.triage20.login.LoginActivity;
import triagedh.hospital.triage20.patient.FragmentListPatientWait;
import triagedh.hospital.triage20.patient.FragmentRecordPatient;
import triagedh.hospital.triage20.symptom.FragmentSymptom;
import triagedh.hospital.triage20.user.FragmentListUser;
import triagedh.hospital.triage20.user.FragmentUserOnline;
import triagedh.hospital.triage20.video.FragmentVideo;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static int MILISECONDS_WAITING = 1000;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
       DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
               this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       drawer.setDrawerListener(toggle);
       toggle.syncState();

       NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
       navigationView.setNavigationItemSelectedListener(this);

       //LOAD FRAGMENT
       loadFragment(MILISECONDS_WAITING);

   }

    public void loadFragment(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                receiveData();
            }
        }, milisegundos);
    }

    private void receiveData(){
        FragmentManager fm=getSupportFragmentManager();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String id_fragment = extras.getString("fragment");
            if (!id_fragment.equals("")) {
                if (id_fragment.equals("1")) {
                    fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentListUser()).commit();
                }else  if (id_fragment.equals("2")) {
                    fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentListLegend()).commit();
                }else  if (id_fragment.equals("3")) {
                    fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentListLevel()).commit();
                }else  if (id_fragment.equals("4")) {
                    fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentListDisease()).commit();
                }else  if (id_fragment.equals("5")) {
                    fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentRecordPatient()).commit();
                }else  if (id_fragment.equals("6")) {
                    fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentSymptom()).commit();
                }
            }else{
                fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentMain()).commit();
            }
        }else{
            fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentMain()).commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        if (id == R.id.nav_income_tab) {
                    Intent intent = new Intent(MainActivity.this, /*MainActivity*/IncomeTabActivity.class);
             startActivity(intent);
          } else
          if (id == R.id.nav_show_record) {
             fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentRecordPatient()).commit();
         } else if (id == R.id.nav_video) {
              fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentVideo()).commit();
         }else if (id == R.id.nav_list_user) {
              fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentListUser()).commit();
         }else if (id == R.id.nav_list_legend) {
              fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentListLegend()).commit();
         }else if (id == R.id.nav_list_level) {
             fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentListLevel()).commit();
         }else if (id == R.id.nav_list_disease) {
             fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentListDisease()).commit();
         }else if (id == R.id.nav_list_patient) {
             fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentListPatientWait()).commit();
         }else if (id == R.id.nav_about) {
              fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentAbout()).commit();
          }else if (id == R.id.nav_user_online) {
            fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentUserOnline()).commit();
          }else if (id == R.id.nav_show_book) {
            fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentShowBookMTS()).commit();
         }else if (id == R.id.nav_clasification_level) {
                fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentClasificationLevel()).commit();
        }else if (id == R.id.nav_list_symptom) {
           fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentSymptom()).commit();
        }else if (id == R.id.nav_you_know) {
              Intent intent = new Intent(MainActivity.this, /*MainActivity*/YouKnowActivity.class);
              startActivity(intent);
          }else if (id == R.id.nav_close) {
            finish();
            Intent intent = new Intent(MainActivity.this, /*MainActivity*/LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
