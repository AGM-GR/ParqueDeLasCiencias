package rafalex.pdm.ugr.parquedelasciencias;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class InformacionGeneralFragment extends Fragment {

    TextView fechaEntrada;
    TextView estadoEntrada;
    LinearLayout layoutPlanetario;
    TextView horaPlanetario;
    ImageView alarmaPlanetario;
    LinearLayout layoutBiodomo;
    TextView horaBiodomo;
    ImageView alarmaBiodomo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View informacionGeneral = inflater.inflate(R.layout.fragment_informacion_general, container, false);

        SharedPreferences entrada_escaneada = getContext().getSharedPreferences("Entrada", Context.MODE_PRIVATE);
        String qrString = entrada_escaneada.getString("codigo", "");

        String[] horarios = qrString.split("[-]+");
        String fecha = horarios[0];
        String biodomo = horarios[1];
        String planetario = horarios[2];

        fechaEntrada = (TextView)informacionGeneral.findViewById(R.id.fecha_entrada);
        estadoEntrada = (TextView)informacionGeneral.findViewById(R.id.estado_entrada);
        layoutPlanetario = (LinearLayout) informacionGeneral.findViewById(R.id.planetario_layout);
        horaPlanetario = (TextView)informacionGeneral.findViewById(R.id.hora_planetario);
        alarmaPlanetario = (ImageView) informacionGeneral.findViewById(R.id.alarma_planetario);
        layoutBiodomo = (LinearLayout) informacionGeneral.findViewById(R.id.biodome_layout);
        horaBiodomo = (TextView)informacionGeneral.findViewById(R.id.hora_biodomo);
        alarmaBiodomo = (ImageView) informacionGeneral.findViewById(R.id.alarma_biodomo);

        fechaEntrada.setText(fecha);
        if (!planetario.contains("_")) {
            layoutPlanetario.setVisibility(View.VISIBLE);
            horaPlanetario.setText(planetario);
        } else
            layoutPlanetario.setVisibility(View.INVISIBLE);
        if (!biodomo.contains("_")) {
            layoutBiodomo.setVisibility(View.VISIBLE);
            horaBiodomo.setText(biodomo);
        } else
            layoutBiodomo.setVisibility(View.INVISIBLE);




        Button boton =  (Button)informacionGeneral.findViewById(R.id.botonAlarma);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentoLanzar = new Intent(getActivity(), Temporizador.class);
                intentoLanzar.putExtra("Titulo", "Biodomo");
                intentoLanzar.putExtra("Hora", "10:20");
                PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis()+4000);
                ((Button)v).setText("Hora: " + cal.getTime());
                /*TimePicker timePicker = new TimePicker();
                cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                cal.set(Calendar.SECOND, 0);*/
                AlarmManager aMan = (AlarmManager)getActivity().getSystemService(getActivity().ALARM_SERVICE);
                aMan.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);

                //aMan.cancel(pIntent);
            }
        });

        return informacionGeneral;
    }

}
