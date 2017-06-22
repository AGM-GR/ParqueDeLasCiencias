package rafalex.pdm.ugr.parquedelasciencias;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Alarma extends AppCompatActivity {

    TextView titulo;
    TextView hora;
    Button aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);

        titulo = (TextView) findViewById(R.id.titulo_alarma);
        hora = (TextView) findViewById(R.id.hora_alarma);
        aceptar = (Button) findViewById(R.id.aceptar_alarma)
    }
}
