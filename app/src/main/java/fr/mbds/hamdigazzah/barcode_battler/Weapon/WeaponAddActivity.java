package fr.mbds.hamdigazzah.barcode_battler.Weapon;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;

import java.io.IOException;

import fr.mbds.hamdigazzah.barcode_battler.DataBaseHandler.DBHandler;
import fr.mbds.hamdigazzah.barcode_battler.Model.Weapon;
import fr.mbds.hamdigazzah.barcode_battler.R;
import fr.mbds.hamdigazzah.barcode_battler.Services.BackgroundSoundService;
import fr.mbds.hamdigazzah.barcode_battler.Utils.WeaponGenerator;

public class WeaponAddActivity extends AppCompatActivity {
    SurfaceView cameraView;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    TextView weaponNameTV;
    TextView barCodeTV;
    ProgressBar damage;
    Button button;
    RelativeLayout relativelayout;
    Weapon newWeapon;
    ImageView weaponImage;

    DBHandler db;
    Intent svc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_add);
        relativelayout = (RelativeLayout) findViewById(R.id.weapon_relativelayout);
        cameraView = (SurfaceView) findViewById(R.id.weapon_camera_view);
        weaponNameTV = (TextView) findViewById(R.id.weapon_name);
        barCodeTV = (TextView) findViewById(R.id.weapon_barcodeTV);
        weaponImage = (ImageView) findViewById(R.id.weapon_image_view);
        damage = (ProgressBar) findViewById(R.id.weapon_damage_progress_bar);
        damage.setMax(20);
        button = (Button) findViewById(R.id.weapon_buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWeaponToDB(newWeapon);
                startActivity(new Intent(WeaponAddActivity.this, WeaponListActivity.class));

            }
        });

        db = new DBHandler(this);
        svc=new Intent(this, BackgroundSoundService.class);

        // Check for the camera permission before accessing the camera.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        }
    }

    private void createCameraSource() {
        Context context = getApplicationContext();

        //fetch a stream of images from the device’s camera and display them in the SurfaceView
        barcodeDetector = new BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.ALL_FORMATS).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();

        startCameraSource();

    }

    private void startCameraSource() throws SecurityException {

        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, 9001);
            dlg.show();
        }

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    //start drawing the preview frames
                    cameraSource.start(cameraView.getHolder());


                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //stop drawing the preview frames
                cameraSource.stop();
            }
        });

        //Call when BarcodeDetector detects a QR code
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    barCodeTV.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            // Update the TextView
                            Log.i("result", barcodes.valueAt(0).displayValue);
                            try {
                                barCodeTV.setText(barcodes.valueAt(0).displayValue);
                                newWeapon = new WeaponGenerator().generate(barcodes.valueAt(0).displayValue);

                                int id = getResources().getIdentifier("fr.mbds.hamdigazzah.barcode_battler:drawable/" + newWeapon.getImagePath(), null, null);
                                weaponImage.setImageDrawable(getResources().getDrawable(id));

                                weaponNameTV.setText(newWeapon.getName());
                                damage.setProgress(newWeapon.getDemage());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });
    }

    public void addWeaponToDB(Weapon ch) {
        db.addWeapon(ch);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(svc);
    }
}