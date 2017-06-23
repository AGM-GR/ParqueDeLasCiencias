package rafalex.pdm.ugr.parquedelasciencias;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRScanner extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_CAMERA = 123;

    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    SurfaceView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qrscanner);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //DETECCION QR
        cameraView = (SurfaceView) findViewById(R.id.camera_view);

        //Creamos el lector de qr
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        //Creamos la camara
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .build();

        // listener de ciclo de vida de la camara
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                //Verificamos si el usuario ha dado permiso para la camara, en caso contrario volvemos a pedirlos
                if (ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                } else {
                    if (Build.VERSION.SDK_INT > 22) {
                        if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA))
                            Toast.makeText(getApplicationContext(), R.string.permisos_camara, Toast.LENGTH_SHORT).show();
                        requestPermissions(new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        // preparo el detector de QR
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }


            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {

                    String value = barcodes.valueAt(0).displayValue.toString();

                    String[] horarios = value.split("[-]+");
                    if (horarios.length == 3) {
                        if (horarios[0].split("[/]+").length == 3
                                && horarios[1].split("[:]+").length == 2
                                && horarios[2].split("[:]+").length == 2) {

                            Intent i = new Intent(QRScanner.this, TicketInfo.class);
                            i.putExtra("QRResult", value);
                            startActivity(i);

                            barcodeDetector.release();

                            finish();
                        }
                    }
                }
            }
        });
    }
}
