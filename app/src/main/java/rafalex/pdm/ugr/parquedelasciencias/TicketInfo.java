package rafalex.pdm.ugr.parquedelasciencias;

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

        text.setText("Fecha entrada: " + fecha + " Biodomo: " + biodomo + " Planetario: " + planetario);
    }

}
