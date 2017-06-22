package rafalex.pdm.ugr.parquedelasciencias;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
