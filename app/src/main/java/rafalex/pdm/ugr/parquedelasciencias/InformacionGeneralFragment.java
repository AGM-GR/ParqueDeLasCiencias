package rafalex.pdm.ugr.parquedelasciencias;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;

public class InformacionGeneralFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View informacionGeneral = inflater.inflate(R.layout.fragment_informacion_general, container, false);

        Button boton =  ((Button)informacionGeneral.findViewById(R.id.botonAlarma));
        boton.setText("ALA");

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((Button)v).setText("Mierda");

                Intent intentoLanzar = new Intent(getContext(), Temporizador.class);
                intentoLanzar.putExtra("Titulo", "Biodomo");
                intentoLanzar.putExtra("Hora", "10:20");
                PendingIntent pIntent = PendingIntent.getBroadcast(getContext(), 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis()+1000);
                ((Button)v).setText("Hora: " + cal.getTime());
                /*TimePicker timePicker = new TimePicker();
                cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                cal.set(Calendar.SECOND, 0);*/
                AlarmManager aMan = (AlarmManager)getContext().getSystemService(getContext().ALARM_SERVICE);
                aMan.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
            }
        });

        return informacionGeneral;
    }

}
