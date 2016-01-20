package qrcodescanner.agungmanuaba.com.qrcodescanner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import qrcodescanner.agungmanuaba.com.qrcodescanner.R;

/**
 * Created by Ari_S on 1/19/2016.
 */
public class InfoDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_details);
        overridePendingTransition(R.anim.activity_open_translate,
                R.anim.slide_right_out);

    }
}
