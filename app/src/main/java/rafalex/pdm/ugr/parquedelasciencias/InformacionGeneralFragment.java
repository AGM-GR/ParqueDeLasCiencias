package rafalex.pdm.ugr.parquedelasciencias;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class InformacionGeneralFragment extends Fragment {

    SharedPreferences.Editor editor_entrada;

    TextView fechaEntrada;
    TextView estadoEntrada;
    LinearLayout layoutPlanetario;
    TextView horaPlanetario;
    ImageView imagenAlarmaPlanetario;
    LinearLayout layoutBiodomo;
    TextView horaBiodomo;
    ImageView imagenAlarmaBiodomo;

    boolean alarmaBiodomo;
    boolean alarmaPlanetario;

    String horaEntradaBiodomo;
    String horaEntradaPlanetario;

    Bundle saved;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.saved = savedInstanceState;

        //Obtiene los datos guardados
        editor_entrada = getContext().getSharedPreferences("Entrada", Context.MODE_PRIVATE).edit();
        SharedPreferences entrada_escaneada = getContext().getSharedPreferences("Entrada", Context.MODE_PRIVATE);
        String qrString = entrada_escaneada.getString("codigo", "");
        alarmaBiodomo = entrada_escaneada.getBoolean("alarma_biodomo", false);
        alarmaPlanetario = entrada_escaneada.getBoolean("alarma_planetario", false);

        String[] horarios = qrString.split("[-]+");
        String fecha = horarios[0];
        horaEntradaBiodomo = horarios[1];
        horaEntradaPlanetario = horarios[2];

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

        String[] fechas_split = fecha.split("[/]+");
        int dia = Integer.parseInt(fechas_split[0]);
        int mes = Integer.parseInt(fechas_split[1]);
        int ano = Integer.parseInt(fechas_split[2]);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int dia_hoy = cal.get(Calendar.DAY_OF_MONTH);
        int mes_hoy = cal.get(Calendar.MONTH) + 1;
        int ano_hoy = cal.get(Calendar.YEAR);

        if(ano < ano_hoy) {
            //La fecha ya ha pasado
            estadoEntrada.setText(getResources().getString(R.string.ha_pasado));
        } else {
            if (ano > ano_hoy){
                //La fecha aun no ha llegado
                estadoEntrada.setText(getResources().getString(R.string.aun_no));
            } else {
                if(mes < mes_hoy){
                    //La fecha ha pasado
                    estadoEntrada.setText(getResources().getString(R.string.ha_pasado));
                } else {
                    if (mes > mes_hoy){
                        //La fecha aun no ha llegado
                        estadoEntrada.setText(getResources().getString(R.string.aun_no));
                    }
                    else {
                        if (dia < dia_hoy) {
                            //La fecha ha pasado
                            estadoEntrada.setText(getResources().getString(R.string.ha_pasado));
                        } else {
                            if(dia > dia_hoy) {
                                //La fecha no ha llegado
                                estadoEntrada.setText(getResources().getString(R.string.aun_no));
                            } else {
                                //Es hoy
                                estadoEntrada.setText(getResources().getString(R.string.es_hoy));
                            }
                        }
                    }
                }
            }
        }

        if (!horaEntradaPlanetario.contains("_")) {
            layoutPlanetario.setVisibility(View.VISIBLE);
            horaPlanetario.setText(horaEntradaPlanetario);
        } else
            layoutPlanetario.setVisibility(View.INVISIBLE);
        if (!horaEntradaBiodomo.contains("_")) {
            layoutBiodomo.setVisibility(View.VISIBLE);
            horaBiodomo.setText(horaEntradaBiodomo);
        } else
            layoutBiodomo.setVisibility(View.INVISIBLE);

        actualizarImagenesAlarmas();

        //Botón para eliminar la alarma
        Button boton =  (Button)informacionGeneral.findViewById(R.id.eliminarEntrada);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCreateDialog(saved).show();
            }
        });

        //Establece los onClick de las vistas
        if(!horaEntradaPlanetario.contains("_")) {
            layoutPlanetario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String[] horariosEntrada = horaEntradaPlanetario.split("[:]+");
                    String horaEntrada = horariosEntrada[0];
                    String minutoEntrada = horariosEntrada[1];

                    if (!alarmaPlanetario) {

                        TimePickerFragment timePicker = new TimePickerFragment(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                                //Estblece la alarma para la hora elegida
                                Intent intentoLanzar = new Intent(getActivity(), Temporizador.class);
                                intentoLanzar.putExtra("Titulo", getResources().getString(R.string.planetario));
                                intentoLanzar.putExtra("Hora", horaEntradaPlanetario);
                                intentoLanzar.putExtra("Alarma", "alarma_planetario");
                                PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
                                Calendar cal = Calendar.getInstance();
                                cal.setTimeInMillis(System.currentTimeMillis());
                                cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                                cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                                cal.set(Calendar.SECOND, 0);
                                AlarmManager aMan = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                                aMan.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);

                                //Guarda la información de la alarma
                                alarmaPlanetario = true;
                                editor_entrada.putBoolean("alarma_planetario", alarmaPlanetario);
                                editor_entrada.commit();

                                actualizarImagenesAlarmas();

                                Toast.makeText(getContext(), "Alarma establecida. Sonará a las: " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute(), Toast.LENGTH_SHORT).show();

                            }
                        } ,Integer.parseInt(horaEntrada) ,Integer.parseInt(minutoEntrada) - 10);

                        timePicker.setMax(Integer.parseInt(horaEntrada), Integer.parseInt(minutoEntrada));

                        timePicker.show();

                    } else {

                        //Cancela la alarma establecida
                        Intent intentoLanzar = new Intent(getActivity(), Temporizador.class);
                        intentoLanzar.putExtra("Titulo", getResources().getString(R.string.planetario));
                        intentoLanzar.putExtra("Hora", horaEntradaPlanetario);
                        intentoLanzar.putExtra("Alarma", "alarma_planetario");
                        PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager aMan = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                        aMan.cancel(pIntent);

                        //Guarda la información de la alarma
                        alarmaPlanetario = false;
                        editor_entrada.putBoolean("alarma_planetario", alarmaPlanetario);
                        editor_entrada.commit();

                        actualizarImagenesAlarmas();

                        Toast.makeText(getContext(), "Alarma eliminada.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if(!horaEntradaBiodomo.contains("_")) {
            layoutBiodomo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String[] horariosEntrada = horaEntradaBiodomo.split("[:]+");
                    String horaEntrada = horariosEntrada[0];
                    String minutoEntrada = horariosEntrada[1];

                    if (!alarmaBiodomo) {

                        TimePickerFragment timePicker = new TimePickerFragment(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                                //Estblece la alarma para la hora elegida
                                Intent intentoLanzar = new Intent(getActivity(), Temporizador.class);
                                intentoLanzar.putExtra("Titulo", getResources().getString(R.string.biodomo));
                                intentoLanzar.putExtra("Hora", horaEntradaBiodomo);
                                intentoLanzar.putExtra("Alarma", "alarma_biodomo");
                                PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 1, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
                                Calendar cal = Calendar.getInstance();
                                cal.setTimeInMillis(System.currentTimeMillis());
                                cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                                cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                                cal.set(Calendar.SECOND, 0);
                                AlarmManager aMan = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                                aMan.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);

                                //Guarda la información de la alarma
                                alarmaBiodomo = true;
                                editor_entrada.putBoolean("alarma_biodomo", alarmaBiodomo);
                                editor_entrada.commit();

                                actualizarImagenesAlarmas();

                                Toast.makeText(getContext(), "Alarma establecida. Sonará a las: " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute(), Toast.LENGTH_SHORT).show();

                            }
                        } ,Integer.parseInt(horaEntrada) ,Integer.parseInt(minutoEntrada) - 10);

                        timePicker.setMax(Integer.parseInt(horaEntrada), Integer.parseInt(minutoEntrada));

                        timePicker.show();

                    } else {

                        //Cancela la alarma establecida
                        Intent intentoLanzar = new Intent(getActivity(), Temporizador.class);
                        intentoLanzar.putExtra("Titulo", getResources().getString(R.string.biodomo));
                        intentoLanzar.putExtra("Hora", horaEntradaBiodomo);
                        intentoLanzar.putExtra("Alarma", "alarma_biodomo");
                        PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 1, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager aMan = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                        aMan.cancel(pIntent);

                        //Guarda la información de la alarma
                        alarmaBiodomo = false;
                        editor_entrada.putBoolean("alarma_biodomo", alarmaBiodomo);
                        editor_entrada.commit();

                        actualizarImagenesAlarmas();

                        Toast.makeText(getContext(), "Alarma eliminada.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return informacionGeneral;
    }

    public void actualizarImagenesAlarmas () {

        //Establece el icono de alarma
        if (alarmaBiodomo)
            imagenAlarmaBiodomo.setImageResource(R.drawable.ic_alarm);
        else
            imagenAlarmaBiodomo.setImageResource(R.drawable.ic_alarm_off);

        if (alarmaPlanetario)
            imagenAlarmaPlanetario.setImageResource(R.drawable.ic_alarm);
        else
            imagenAlarmaPlanetario.setImageResource(R.drawable.ic_alarm_off);
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
