package rafalex.pdm.ugr.parquedelasciencias;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ExposicionesTemporalesFragment extends Fragment {


    private ArrayList<Exposicion> exposiciones = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View expo = inflater.inflate(R.layout.fragment_exposiciones_temporales, container, false);

        //Inicializamos el array de Categorias
        exposiciones.add(new Exposicion(R.drawable.mar, getContext().getString(R.string.mar), getContext().getString(R.string.mar_des), "02-06-2017", "20-08-2017"));
        exposiciones.add(new Exposicion(R.drawable.marte, getContext().getString(R.string.marte), getContext().getString(R.string.marte_des), "18-05-2017", "16-07-2017"));
        exposiciones.add(new Exposicion(R.drawable.vida, getContext().getString(R.string.vida), getContext().getString(R.string.vida_des), "04-04-2017", "09-07-2017"));
        exposiciones.add(new Exposicion(R.drawable.robots, getContext().getString(R.string.robots), getContext().getString(R.string.robots_des), "07-03-2017", "08-01-2018"));
        exposiciones.add(new Exposicion(R.drawable.wow, getContext().getString(R.string.wow), getContext().getString(R.string.wow_des), "15-11-2016", "01-08-2017"));
        exposiciones.add(new Exposicion(R.drawable.celula_madre, getContext().getString(R.string.celula_madre), getContext().getString(R.string.celula_madre_des), "04-04-2017", "09-07-2017"));

        // Inflate the layout for this fragment
        return expo;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onResume() {

        super.onResume();

        // Establece la vista y el adaptador
        ListView pairedListView = (ListView) getView().findViewById(R.id.exposicion_selector);
        pairedListView.setAdapter(new ExposicionAdapter(exposiciones));
        setListViewHeightBasedOnChildren(pairedListView);

    }

}
