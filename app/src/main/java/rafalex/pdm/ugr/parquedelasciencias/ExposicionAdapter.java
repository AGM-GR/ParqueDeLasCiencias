package rafalex.pdm.ugr.parquedelasciencias;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ExposicionAdapter extends BaseAdapter {

    private ArrayList<Exposicion> exposiciones;

    public ExposicionAdapter(ArrayList<Exposicion> categoria) {
        this.exposiciones = categoria;

        //Cada vez que cambiamos los elementos debemos noficarlo
        notifyDataSetChanged();
    }

    //Devuelve el numero de elementos
    public int getCount() {
        return exposiciones.size();
    }

    //Devuelve el elemento de una posición
    public Object getItem(int position) {
        return exposiciones.get(position);
    }

    //Devulve el ID del elemento (Generalmente no se usa)
    public long getItemId(int position) {
        return position;
    }

    //Devuelve la vista de un elemento
    public View getView(int position, View convertView, ViewGroup parent) {

        //Si el contentView ya tiene un device, lo reutilizaremos con los nuevos datos
        // Si no crearemos uno nuevo
        ExposicionView view;
        if (convertView == null)
            view = new ExposicionView(parent.getContext());
        else
            view = (ExposicionView) convertView;

        //Asignamos los valores del Device a mostrar
        view.setExposicion(exposiciones.get(position));

        return view;
    }
}
