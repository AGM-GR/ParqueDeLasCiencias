package rafalex.pdm.ugr.parquedelasciencias;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Temporizador extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)  {
        Intent actividadALanzar = new Intent(context, Alarma.class);
        context.startActivity(actividadALanzar);
    }
}

