package rafalex.pdm.ugr.parquedelasciencias;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExposicionView extends LinearLayout {

    private ImageView image;
    private TextView titulo;
    private TextView descripcion;
    private TextView fecha_ini;
    private TextView fecha_fin;

    public ExposicionView(Context context) {
        super(context);
        inflate(context, R.layout.exposicion_view, this);

        image = (ImageView) findViewById(R.id.exposicion_image);
        titulo = (TextView) findViewById(R.id.exposicion_titulo);
        descripcion = (TextView) findViewById(R.id.exposicion_descripcion);
        fecha_ini = (TextView) findViewById(R.id.exposicion_fecha_ini);
        fecha_fin = (TextView) findViewById(R.id.exposicion_fecha_fin);
    }

    //Permite establecer la exposición a mostrar
    public void setExposicion(Exposicion exposicion) {

        image.setImageResource(exposicion.getImagen());
        titulo.setText(exposicion.getTitulo());
        descripcion.setText(exposicion.getTDescripcion());
        fecha_ini.setText(exposicion.getFechaIni());
        fecha_fin.setText(exposicion.getFechaFin());
    }

}
