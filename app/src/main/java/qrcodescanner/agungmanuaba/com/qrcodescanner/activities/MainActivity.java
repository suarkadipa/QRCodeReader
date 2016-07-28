package qrcodescanner.agungmanuaba.com.qrcodescanner.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import qrcodescanner.agungmanuaba.com.qrcodescanner.R;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button scanBtn, quitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        scanBtn = (Button) findViewById(R.id.scan_button);
        quitBtn = (Button) findViewById(R.id.quit_button);

        scanBtn.setOnClickListener(this);
        quitBtn.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent1 = new Intent(MainActivity.this, InfoDetailsActivity.class);
                intent1.putExtra("qrcode_id", "K0003");
                startActivity(intent1);
            }
        });

        // enable this to check the gps availability
        checkGPSAvailability();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_button:
                PackageManager packageManager = getApplicationContext().getPackageManager();
                if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    IntentIntegrator integrator = new IntentIntegrator(this);
                    integrator.setPrompt(getString(R.string.scan_prompt));
                    integrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
                    integrator.setOrientationLocked(false);
                    integrator.initiateScan();
                } else {
                    // Toast
                    Toast toast = Toast.makeText(getApplicationContext(), "Your device has not camera feature!", Toast.LENGTH_SHORT);
                    toast.show();
                }

                break;
            case R.id.quit_button:
                this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            // we have a result
            String scanContent = scanningResult.getContents();

            Intent intent = new Intent(MainActivity.this, InfoDetailsActivity.class);
            intent.putExtra("qrcode_id", scanContent);
            startActivity(intent);
        } else {
            // Toast
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void checkGPSAvailability() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        }else{
            showGPSDisabledAlertToUser();
        }
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS Anda dalam posisi Off. Hidupkan GPS agar pengecekan lokasi benda koleksi berjalan dengan baik.")
                .setCancelable(false)
                .setPositiveButton("Hidupkan",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Abaikan",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
