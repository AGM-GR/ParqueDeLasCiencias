package rafalex.pdm.ugr.parquedelasciencias;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
    ImageView imagenAlarmaPlanetario;
    LinearLayout layoutBiodomo;
    TextView horaBiodomo;
    ImageView imagenAlarmaBiodomo;

    Bundle saved;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.saved = savedInstanceState;

        //Obtiene los datos guardados
        SharedPreferences entrada_escaneada = getContext().getSharedPreferences("Entrada", Context.MODE_PRIVATE);
        String qrString = entrada_escaneada.getString("codigo", "");
        boolean alarmaBiodomo = entrada_escaneada.getBoolean("alarma_biodomo", false);
        boolean alarmaPlanetario = entrada_escaneada.getBoolean("alarma_planetario", false);

        String[] horarios = qrString.split("[-]+");
        String fecha = horarios[0];
        String biodomo = horarios[1];
        String planetario = horarios[2];

        // Inflate the layout for this fragment
        View informacionGeneral = inflater.inflate(R.layout.fragment_informacion_general, container, false);

        fechaEntrada = (TextView)informacionGeneral.findViewById(R.id.fecha_entrada);
        estadoEntrada = (TextView)informacionGeneral.findViewById(R.id.estado_entrada);
        layoutPlanetario = (LinearLayout) informacionGeneral.findViewById(R.id.planetario_layout);
        horaPlanetario = (TextView)informacionGeneral.findViewById(R.id.hora_planetario);
        imagenAlarmaPlanetario = (ImageView) informacionGeneral.findViewById(R.id.alarma_planetario);
        layoutBiodomo = (LinearLayout) informacionGeneral.findViewById(R.id.biodome_layout);
        horaBiodomo = (TextView)informacionGeneral.findViewById(R.id.hora_biodomo);
        imagenAlarmaBiodomo = (ImageView) informacionGeneral.findViewById(R.id.alarma_biodomo);

        //Muestra las vistas dependiendo de si están disponibles
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

        //Establece el icono de alarma
        if (alarmaBiodomo)
            imagenAlarmaBiodomo.setImageResource(R.drawable.ic_alarm);
        else
            imagenAlarmaBiodomo.setImageResource(R.drawable.ic_alarm_off);

        if (alarmaPlanetario)
            imagenAlarmaPlanetario.setImageResource(R.drawable.ic_alarm);
        else
            imagenAlarmaPlanetario.setImageResource(R.drawable.ic_alarm_off);

        Button boton =  (Button)informacionGeneral.findViewById(R.id.eliminarEntrada);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCreateDialog(saved).show();
            }
        });

        return informacionGeneral;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.comprobacion)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Borra la entrada escaneada
                        SharedPreferences.Editor entrada_escaneada = getActivity().getSharedPreferences("Entrada", Context.MODE_PRIVATE).edit();
                        entrada_escaneada.putString("codigo", "");
                        entrada_escaneada.putBoolean("alarma_biodomo", false);
                        entrada_escaneada.putBoolean("alarma_planetario", false);
                        entrada_escaneada.putBoolean("alarma_torre", false);
                        entrada_escaneada.putBoolean("alarma_aves", false);
                        entrada_escaneada.putBoolean("alarma_mariposario", false);
                        entrada_escaneada.commit();

                        Intent i = new Intent(getActivity(), MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getActivity().startActivity(i);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
