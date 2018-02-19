package fr.mbds.hamdigazzah.barcode_battler;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import fr.mbds.hamdigazzah.barcode_battler.Character.CharacterAddActivity;
import fr.mbds.hamdigazzah.barcode_battler.Character.CharacterListActivity;
import fr.mbds.hamdigazzah.barcode_battler.Services.BackgroundSoundService;

public class MainActivity extends AppCompatActivity {

    Button startLocal;
    RelativeLayout relativeLayout;
    Intent svc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
        startLocal = (Button) findViewById(R.id.button_start_local);
        startLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CharacterListActivity.class));
            }
        });

        svc=new Intent(this, BackgroundSoundService.class);
        startService(svc);

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        }
    }


    private void requestCameraPermission() {

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, 2);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        2);
            }
        };

        findViewById(R.id.relative_layout).setOnClickListener(listener);
        Snackbar.make(relativeLayout, "Access to the camera is needed for detection",
                Snackbar.LENGTH_INDEFINITE)
                .setAction("ok", listener)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode != 2) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // we have permission
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("barcode battler")
                .setMessage("This application cannot run because it does not have the camera permission.  The application will now exit.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(svc);
    }
}
