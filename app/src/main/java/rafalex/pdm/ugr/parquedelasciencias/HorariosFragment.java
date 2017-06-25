package rafalex.pdm.ugr.parquedelasciencias;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HorariosFragment extends Fragment {

    LinearLayout planetario;
    LinearLayout biodomo;
    LinearLayout torre;
    LinearLayout aves;
    LinearLayout mariposario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View horarios = inflater.inflate(R.layout.fragment_horarios, container, false);

        planetario = (LinearLayout) horarios.findViewById(R.id.planetario_layout);
        biodomo = (LinearLayout) horarios.findViewById(R.id.biodomo_layout);
        torre = (LinearLayout) horarios.findViewById(R.id.torre_layout);
        aves = (LinearLayout) horarios.findViewById(R.id.aves_layout);
        mariposario = (LinearLayout) horarios.findViewById(R.id.mariposario_layout);

        planetario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getActivity().getFragmentManager(), "TimePicker");
            }
        });

        biodomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getActivity().getFragmentManager(), "TimePicker");
            }
        });

        torre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getActivity().getFragmentManager(), "TimePicker");
            }
        });

        aves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getActivity().getFragmentManager(), "TimePicker");
            }
        });

        mariposario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getActivity().getFragmentManager(), "TimePicker");
            }
        });


        return horarios;
    }

}
