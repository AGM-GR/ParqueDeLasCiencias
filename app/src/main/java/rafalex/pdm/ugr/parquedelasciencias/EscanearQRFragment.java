package rafalex.pdm.ugr.parquedelasciencias;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EscanearQRFragment extends Fragment {

    Button scannButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View escanear_qr = inflater.inflate(R.layout.fragment_escanear_qr, container, false);

        scannButton = (Button) escanear_qr.findViewById(R.id.scannButton);

        scannButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), QRScanner.class);
                startActivity(i);
            }
        });

        return escanear_qr;
    }

}
