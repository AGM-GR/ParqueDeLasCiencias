package rafalex.pdm.ugr.parquedelasciencias;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TicketInfo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView text;
    String qrString;
    String fecha;
    String biodomo;
    String planetario;

    private AlertDialog menuDialog;
    private AlertDialog.Builder helpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        text = (TextView) findViewById(R.id.qrText);

        //Crea los dialogos
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.help_dialog, null);

        helpDialog = new AlertDialog.Builder(this, R.style.DialogTheme)
                .setView(dialogView)
                .setTitle(R.string.menu_help)
                .setNeutralButton(R.string.ok_button,null);

        menuDialog = helpDialog.create();
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

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.info) {
           fragmentManager.beginTransaction().replace(R.id.contenedor, new InformacionGeneralFragment()).commit();
        } else if (id == R.id.expo) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ExposicionesTemporalesFragment()).commit();
        } else if (id == R.id.horarios) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new HorariosFragment()).commit();
        } else if (id == R.id.mapa) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new MapaFragment()).commit();
        } else if (id == R.id.escanear) {
            //Borra la entrada guardada
            SharedPreferences.Editor entrada_escaneada = getSharedPreferences("Entrada", Context.MODE_PRIVATE).edit();
            entrada_escaneada.putString("codigo", "");
            entrada_escaneada.commit();

            //Lanza el escaneo
            Intent i = new Intent(TicketInfo.this, QRScanner.class);
            startActivity(i);

        } else if (id == R.id.ayuda) {

            menuDialog.show();
            return true;
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
        //text.setText(R.string.fecha_entrada + ": " + fecha + " Biodomo: " + biodomo + " " + R.string.planetario + ": " + planetario);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, new InformacionGeneralFragment()).commit();

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
