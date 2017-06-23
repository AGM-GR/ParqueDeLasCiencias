package rafalex.pdm.ugr.parquedelasciencias;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TicketInfo extends AppCompatActivity {

    TextView text;
    String qrString;
    String fecha;
    String biodomo;
    String planetario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_info);

        text = (TextView) findViewById(R.id.qrText);
    }

    @Override
    protected void onResume () {
        super.onResume();

        qrString = getIntent().getExtras().getString("QRResult");

        String[] horarios = qrString.split("[-]+");
        fecha = horarios[0];
        biodomo = horarios[1];
        planetario = horarios[2];

        text.setText(R.string.fecha_entrada + ": " + fecha + " Biodomo: " + biodomo + R.string.planetario + ": " + planetario);

    }

    //Intent intentoLanzar = new Intent(getBaseContext(), Temporizador.class);
    //PendingIntent pIntent=PendingIntent.getBroadcast(this, 0, intentoLanzar, PendingIntent.FLAG_UPDATE_CURRENT);


    //Calendar cal = Calendar.getInstance();
    //cal.setTimeInMillis(System.currentTimeMillis());
    //cal.set (Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
    //cal.set (Calendar.MINUTE, timePicker.getCurrentMinute());
    //cal.set (Calendar.SECOND, 0);

    //AlarmManager aMan = (AlarmManager)getSystemService(ALARM_SERVICE);
    //aMan.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);


}
