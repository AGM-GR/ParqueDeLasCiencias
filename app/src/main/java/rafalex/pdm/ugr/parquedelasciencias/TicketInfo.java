package rafalex.pdm.ugr.parquedelasciencias;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TicketInfo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView text;
    String qrString;
    String fecha;
    String biodomo;
    String planetario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        text = (TextView) findViewById(R.id.qrText);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ticket_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.app.FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();

        qrString = getIntent().getExtras().getString("QRResult");
        String[] horarios = qrString.split("[-]+");
        fecha = horarios[0];
        biodomo = horarios[1];
        planetario = horarios[2];
        text.setText(R.string.fecha_entrada + ": " + fecha + " Biodomo: " + biodomo + " " + R.string.planetario + ": " + planetario);

        //Guarda la entrada escaneada
        SharedPreferences.Editor entrada_escaneada = getSharedPreferences("Entrada", Context.MODE_PRIVATE).edit();
        entrada_escaneada.putString("codigo", qrString);
        entrada_escaneada.commit();
    }

    //Intent intentoLanzar = new Intent(getBaseContext(), Temporizador.class); 
    // PendingIntent pIntent=PendingIntent.getBroadcast(this, 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);   
    // Calendar cal = Calendar.getInstance(); 
    // cal.setTimeInMillis(System.currentTimeMillis()); 
    // cal.set (Calendar.HOUR_OF_DAY, timePicker.getCurrentHour()); 
    // cal.set (Calendar.MINUTE, timePicker.getCurrentMinute()); 
    // cal.set (Calendar.SECOND, 0);  
    // AlarmManager aMan = (AlarmManager)getSystemService(ALARM_SERVICE); 
    // aMan.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
    //    } 
}
