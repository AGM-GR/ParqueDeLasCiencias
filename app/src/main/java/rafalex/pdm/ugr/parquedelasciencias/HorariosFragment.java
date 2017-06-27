package rafalex.pdm.ugr.parquedelasciencias;


import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class HorariosFragment extends Fragment {

    SharedPreferences.Editor editor_entrada;

    LinearLayout planetario;
    LinearLayout biodomo;
    LinearLayout torre;
    LinearLayout aves;
    LinearLayout mariposario;

    TextView horaBiodomo;
    TextView horaPlanetario;

    ImageView imagenAlarmaPlanetario;
    ImageView imagenAlarmaBiodomo;
    ImageView imagenAlarmaTorre;
    ImageView imagenAlarmaAves;
    ImageView imagenAlarmaMariposario;

    boolean alarmaBiodomo;
    boolean alarmaPlanetario;
    boolean alarmaTorre;
    boolean alarmaAves;
    boolean alarmaMariposario;

    String horaEntradaBiodomo;
    String horaEntradaPlanetario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Obtiene los datos guardados
        editor_entrada = getContext().getSharedPreferences("Entrada", Context.MODE_PRIVATE).edit();
        SharedPreferences entrada_escaneada = getContext().getSharedPreferences("Entrada", Context.MODE_PRIVATE);
        String qrString = entrada_escaneada.getString("codigo", "");
        alarmaBiodomo = entrada_escaneada.getBoolean("alarma_biodomo", false);
        alarmaPlanetario = entrada_escaneada.getBoolean("alarma_planetario", false);
        alarmaTorre = entrada_escaneada.getBoolean("alarma_torre", false);
        alarmaAves = entrada_escaneada.getBoolean("alarma_aves", false);
        alarmaMariposario = entrada_escaneada.getBoolean("alarma_mariposario", false);

        String[] horariosEntrada = qrString.split("[-]+");
        horaEntradaBiodomo = horariosEntrada[1];
        horaEntradaPlanetario = horariosEntrada[2];

        // Inflate the layout for this fragment
        View horarios = inflater.inflate(R.layout.fragment_horarios, container, false);

        planetario = (LinearLayout) horarios.findViewById(R.id.planetario_layout);
        biodomo = (LinearLayout) horarios.findViewById(R.id.biodomo_layout);
        torre = (LinearLayout) horarios.findViewById(R.id.torre_layout);
        aves = (LinearLayout) horarios.findViewById(R.id.aves_layout);
        mariposario = (LinearLayout) horarios.findViewById(R.id.mariposario_layout);

        horaBiodomo = (TextView) horarios.findViewById(R.id.hora_biodomo);
        horaPlanetario = (TextView) horarios.findViewById(R.id.hora_planetario);

        imagenAlarmaPlanetario = (ImageView) horarios.findViewById(R.id.alarma_planetario);
        imagenAlarmaBiodomo = (ImageView) horarios.findViewById(R.id.alarma_biodomo);
        imagenAlarmaTorre = (ImageView) horarios.findViewById(R.id.alarma_torre);
        imagenAlarmaAves = (ImageView) horarios.findViewById(R.id.alarma_aves);
        imagenAlarmaMariposario = (ImageView) horarios.findViewById(R.id.alarma_mariposas);

        //Establece las horas reservadas
        horaBiodomo.setText(horaEntradaBiodomo);
        horaPlanetario.setText(horaEntradaPlanetario);

        //Establece el icono de alarma
        actualizarImagenesAlarmas();

        //Establece los onClick de las vistas
        if(!horaEntradaPlanetario.contains("_")) {
            planetario.setOnClickListener(new View.OnClickListener() {
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
            biodomo.setOnClickListener(new View.OnClickListener() {
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
                                PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
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
                        PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
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

        torre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String horaEntrada = "18";
                String minutoEntrada = "30";

                if (!alarmaTorre) {

                    TimePickerFragment timePicker = new TimePickerFragment(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {

                            //Estblece la alarma para la hora elegida
                            Intent intentoLanzar = new Intent(getActivity(), Temporizador.class);
                            intentoLanzar.putExtra("Titulo", getResources().getString(R.string.torre));
                            intentoLanzar.putExtra("Hora", "");
                            intentoLanzar.putExtra("Alarma", "alarma_torre");
                            PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(System.currentTimeMillis());
                            cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                            cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                            cal.set(Calendar.SECOND, 0);
                            AlarmManager aMan = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                            aMan.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);

                            //Guarda la información de la alarma
                            alarmaTorre = true;
                            editor_entrada.putBoolean("alarma_torre", alarmaTorre);
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
                    intentoLanzar.putExtra("Titulo", getResources().getString(R.string.torre));
                    intentoLanzar.putExtra("Hora", "");
                    intentoLanzar.putExtra("Alarma", "alarma_torre");
                    PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager aMan = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                    aMan.cancel(pIntent);

                    //Guarda la información de la alarma
                    alarmaTorre = false;
                    editor_entrada.putBoolean("alarma_torre", alarmaTorre);
                    editor_entrada.commit();

                    actualizarImagenesAlarmas();

                    Toast.makeText(getContext(), "Alarma eliminada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        aves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String horaEntrada = "17";
                String minutoEntrada = "45";

                if (!alarmaAves) {

                    TimePickerFragment timePicker = new TimePickerFragment(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {

                            //Estblece la alarma para la hora elegida
                            Intent intentoLanzar = new Intent(getActivity(), Temporizador.class);
                            intentoLanzar.putExtra("Titulo", getResources().getString(R.string.ave));
                            intentoLanzar.putExtra("Hora", "");
                            intentoLanzar.putExtra("Alarma", "alarma_aves");
                            PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(System.currentTimeMillis());
                            cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                            cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                            cal.set(Calendar.SECOND, 0);
                            AlarmManager aMan = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                            aMan.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);

                            //Guarda la información de la alarma
                            alarmaAves = true;
                            editor_entrada.putBoolean("alarma_aves", alarmaAves);
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
                    intentoLanzar.putExtra("Titulo", getResources().getString(R.string.ave));
                    intentoLanzar.putExtra("Hora", "");
                    intentoLanzar.putExtra("Alarma", "alarma_aves");
                    PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager aMan = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                    aMan.cancel(pIntent);

                    //Guarda la información de la alarma
                    alarmaAves = false;
                    editor_entrada.putBoolean("alarma_aves", alarmaAves);
                    editor_entrada.commit();

                    actualizarImagenesAlarmas();

                    Toast.makeText(getContext(), "Alarma eliminada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mariposario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String horaEntrada = "18";
                String minutoEntrada = "30";

                if (!alarmaMariposario) {

                    TimePickerFragment timePicker = new TimePickerFragment(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {

                            //Estblece la alarma para la hora elegida
                            Intent intentoLanzar = new Intent(getActivity(), Temporizador.class);
                            intentoLanzar.putExtra("Titulo", getResources().getString(R.string.mariposa));
                            intentoLanzar.putExtra("Hora", "");
                            intentoLanzar.putExtra("Alarma", "alarma_mariposario");
                            PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(System.currentTimeMillis());
                            cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                            cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                            cal.set(Calendar.SECOND, 0);
                            AlarmManager aMan = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                            aMan.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);

                            //Guarda la información de la alarma
                            alarmaMariposario = true;
                            editor_entrada.putBoolean("alarma_mariposario", alarmaMariposario);
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
                    intentoLanzar.putExtra("Titulo", getResources().getString(R.string.mariposa));
                    intentoLanzar.putExtra("Hora", "");
                    intentoLanzar.putExtra("Alarma", "alarma_mariposario");
                    PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager aMan = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                    aMan.cancel(pIntent);

                    //Guarda la información de la alarma
                    alarmaMariposario = false;
                    editor_entrada.putBoolean("alarma_mariposario", alarmaMariposario);
                    editor_entrada.commit();

                    actualizarImagenesAlarmas();

                    Toast.makeText(getContext(), "Alarma eliminada.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return horarios;
    }

    public void actualizarImagenesAlarmas () {

        if (alarmaBiodomo)
            imagenAlarmaBiodomo.setImageResource(R.drawable.ic_alarm);
        else if(!horaEntradaBiodomo.contains("_"))
            imagenAlarmaBiodomo.setImageResource(R.drawable.ic_alarm_add);
        else
            imagenAlarmaBiodomo.setImageResource(R.drawable.ic_alarm_off);

        if (alarmaPlanetario)
            imagenAlarmaPlanetario.setImageResource(R.drawable.ic_alarm);
        else if(!horaEntradaPlanetario.contains("_"))
            imagenAlarmaPlanetario.setImageResource(R.drawable.ic_alarm_add);
        else
            imagenAlarmaPlanetario.setImageResource(R.drawable.ic_alarm_off);

        if (alarmaTorre)
            imagenAlarmaTorre.setImageResource(R.drawable.ic_alarm);
        else
            imagenAlarmaTorre.setImageResource(R.drawable.ic_alarm_add);

        if (alarmaAves)
            imagenAlarmaAves.setImageResource(R.drawable.ic_alarm);
        else
            imagenAlarmaAves.setImageResource(R.drawable.ic_alarm_add);

        if (alarmaMariposario)
            imagenAlarmaMariposario.setImageResource(R.drawable.ic_alarm);
        else
            imagenAlarmaMariposario.setImageResource(R.drawable.ic_alarm_add);
    }

}
