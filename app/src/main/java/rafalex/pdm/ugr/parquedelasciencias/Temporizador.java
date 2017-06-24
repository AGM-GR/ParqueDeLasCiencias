package rafalex.pdm.ugr.parquedelasciencias;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Temporizador extends BroadcastReceiver {

    String titulo;
    String hora;

    @Override
    public void onReceive(Context context, Intent intent)  {

        titulo = intent.getStringExtra("Titulo");
        hora = intent.getStringExtra("Hora");

        Intent actividadALanzar = new Intent(context, Alarma.class);
        actividadALanzar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        actividadALanzar.putExtra("Titulo",titulo);
        actividadALanzar.putExtra("Hora",hora);

        context.startActivity(actividadALanzar);
    }
}

