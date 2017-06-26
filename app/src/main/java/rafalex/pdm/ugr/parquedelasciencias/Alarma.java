package rafalex.pdm.ugr.parquedelasciencias;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Calendar;

public class Alarma extends AppCompatActivity {

    Vibrator vib;
    long [] patron = {0, 500, 500, 500, 500};
    Ringtone ringtone;

    TextView titulo;
    TextView hora;
    Button aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma);

        vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        titulo = (TextView) findViewById(R.id.titulo_alarma);
        hora = (TextView) findViewById(R.id.hora_alarma);
        aceptar = (Button) findViewById(R.id.aceptar_alarma);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.cancel();
                ringtone.stop();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Establece los elementos de la vista
        titulo.setText(getIntent().getExtras().getString("Titulo"));
        String horaText = getIntent().getExtras().getString("Hora");
        if (horaText.equals("")) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            horaText = cal.getTime().getHours() + ":" + cal.getTime().getMinutes();
        }
        hora.setText(horaText);

        //Limpia el valor de la alarma
        String keyAlarma = getIntent().getExtras().getString("Alarma");
        SharedPreferences.Editor editor_entrada = getSharedPreferences("Entrada", Context.MODE_PRIVATE).edit();
        editor_entrada.putBoolean(keyAlarma, false);
        editor_entrada.commit();

        //Activa la vibración y el sonido
        vib.vibrate(patron,3);
        Uri alarma = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarma);
        ringtone.play();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vib.cancel();
        ringtone.stop();
    }
}
