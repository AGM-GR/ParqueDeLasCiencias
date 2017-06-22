package rafalex.pdm.ugr.parquedelasciencias;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Temporizador extends BroadcastReceiver {

    String titulo;
    String hora;

    @Override
    public void onReceive(Context context, Intent intent)  {
        Intent actividadALanzar = new Intent(context, Alarma.class);

        actividadALanzar.putExtra("titulo",titulo);
        actividadALanzar.putExtra("hora",hora);

        context.startActivity(actividadALanzar);
    }
}

