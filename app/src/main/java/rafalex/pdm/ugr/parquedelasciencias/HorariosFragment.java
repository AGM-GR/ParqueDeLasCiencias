package rafalex.pdm.ugr.parquedelasciencias;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
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

public class HorariosFragment extends Fragment {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Obtiene los datos guardados
        SharedPreferences entrada_escaneada = getContext().getSharedPreferences("Entrada", Context.MODE_PRIVATE);
        String qrString = entrada_escaneada.getString("codigo", "");
        boolean alarmaBiodomo = entrada_escaneada.getBoolean("alarma_biodomo", false);
        boolean alarmaPlanetario = entrada_escaneada.getBoolean("alarma_planetario", false);
        boolean alarmaTorre = entrada_escaneada.getBoolean("alarma_torre", false);
        boolean alarmaAves = entrada_escaneada.getBoolean("alarma_aves", false);
        boolean alarmaMariposario = entrada_escaneada.getBoolean("alarma_mariposario", false);

        String[] horariosEntrada = qrString.split("[-]+");
        String horaEntradaBiodomo = horariosEntrada[1];
        String horaEntradaPlanetario = horariosEntrada[2];

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

        //Establece los onClick de las vistas
        if(!horaEntradaPlanetario.contains("_")) {
            planetario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerFragment timePicker = new TimePickerFragment(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            System.out.println("Hora seleccionada");
                        }
                    }, 1 ,2 );

                    timePicker.show();
                }
            });
        }

        if(!horaEntradaBiodomo.contains("_")) {
            biodomo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerFragment timePicker = new TimePickerFragment(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            System.out.println("Hora seleccionada");
                        }
                    }, 1 ,2 );

                    timePicker.show();
                }
            });
        }

        torre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePicker = new TimePickerFragment(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        System.out.println("Hora seleccionada");
                    }
                }, 1 ,2 );

                timePicker.show();
            }
        });

        aves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePicker = new TimePickerFragment(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        System.out.println("Hora seleccionada");
                    }
                }, 1 ,2 );

                timePicker.show();
            }
        });

        mariposario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePicker = new TimePickerFragment(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        System.out.println("Hora seleccionada");
                    }
                }, 1 ,2 );

                timePicker.show();
            }
        });


        return horarios;
    }

}
