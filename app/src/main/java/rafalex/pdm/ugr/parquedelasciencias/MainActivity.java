package rafalex.pdm.ugr.parquedelasciencias;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int MY_PERMISSIONS_REQUEST_CAMERA = 123;

    //Datos para dialogos
    private AlertDialog menuDialog;
    private AlertDialog.Builder helpDialog;

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

        //Pide permisos para la camara
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT > 22) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA))
                    Toast.makeText(getApplicationContext(), R.string.permisos_camara, Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }

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
    protected void onResume() {
        super.onResume();

        //Comprueba si ya hay una entrada escaneada
        SharedPreferences entrada_escaneada = getSharedPreferences("Entrada", Context.MODE_PRIVATE);
        String codigo_entrada = entrada_escaneada.getString("codigo", "");
        if (!codigo_entrada.equals("")) {
            Intent i = new Intent(MainActivity.this, TicketInfo.class);
            i.putExtra("QRResult", codigo_entrada);
            startActivity(i);
            finish();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.contenedor, new EscanearQRFragment()).commit();

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

        if (id == R.id.expo) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ExposicionesTemporalesFragment()).commit();
        } else if (id == R.id.horarios) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new HorariosTarifasFragment()).commit();
        } else if (id == R.id.mapa) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new MapaFragment()).commit();
        } else if (id == R.id.escanear) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new EscanearQRFragment()).commit();

        } else if (id == R.id.ayuda) {
            menuDialog.show();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
