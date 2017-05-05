package rafalex.pdm.ugr.parquedelasciencias;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TicketInfo extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_info);

        text = (TextView) findViewById(R.id.qrText);
    }

    @Override
    protected void onResume () {
        super.onResume();

        text.setText(getIntent().getExtras().getString("QRResult"));
    }

}
