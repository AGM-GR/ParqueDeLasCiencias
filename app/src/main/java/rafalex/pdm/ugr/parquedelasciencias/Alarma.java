package rafalex.pdm.ugr.parquedelasciencias;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Alarma extends AppCompatActivity {

    Vibrator vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    long [] patron = {0, 500, 500, 500, 500};

    TextView titulo;
    TextView hora;
    Button aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);

        titulo = (TextView) findViewById(R.id.titulo_alarma);
        hora = (TextView) findViewById(R.id.hora_alarma);
        aceptar = (Button) findViewById(R.id.aceptar_alarma);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.cancel();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        vib.vibrate(patron,-1);
    }
}
