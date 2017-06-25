package rafalex.pdm.ugr.parquedelasciencias;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        //Utiliza la fecha actual como valor por defecto para el selector
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        //Crea y devuelve una nueva instancia de DatePickerDialog
        return new DatePickerDialog(getActivity(),this, year, month,day);
    }

    //Meotodo llamado al seleccionar la fecha
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){


    }
}