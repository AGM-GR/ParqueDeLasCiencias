package rafalex.pdm.ugr.parquedelasciencias;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import java.util.Calendar;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        //Utiliza la hora actual como valor por defecto para el selector
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Crea y devuelve una nueva instancia de TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    //Meotodo llamado al seleccionar la hora
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){


    }
}