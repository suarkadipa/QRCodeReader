package qrcodescanner.agungmanuaba.com.qrcodescanner;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Ari_S on 1/19/2016.
 */
public class InfoDetailsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_details);
        overridePendingTransition(R.anim.activity_open_translate,
                R.anim.slide_right_out);

    }
}
