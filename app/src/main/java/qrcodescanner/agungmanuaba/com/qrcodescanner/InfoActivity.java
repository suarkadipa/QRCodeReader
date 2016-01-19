package qrcodescanner.agungmanuaba.com.qrcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * Created by Ari_S on 1/19/2016.
 */
public class InfoActivity extends Activity implements OnClickListener {
    private TextView infoLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        overridePendingTransition(R.anim.activity_open_translate,
                R.anim.slide_right_out);

        infoLink = (TextView) findViewById(R.id.info_link);

        infoLink.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.info_link) {
            Intent intent = new Intent(getApplicationContext(), InfoDetailsActivity.class);
            intent.putExtra("qrcode_id", "test");
            startActivity(intent);
        }
    }
}
