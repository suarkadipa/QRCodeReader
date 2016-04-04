package qrcodescanner.agungmanuaba.com.qrcodescanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import qrcodescanner.agungmanuaba.com.qrcodescanner.R;

/**
 * Created by Ari_S on 1/19/2016.
 */
public class InfoActivity extends AppCompatActivity implements OnClickListener {
    private TextView infoLink;
    private String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        overridePendingTransition(R.anim.activity_open_translate,
                R.anim.slide_right_out);

        infoLink = (TextView) findViewById(R.id.info_link);

        infoLink.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("qrcode_id")) {
            itemId = extras.getString("qrcode_id");
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.info_link) {
            Intent intent = new Intent(getApplicationContext(), InfoDetailsActivity.class);
            intent.putExtra("qrcode_id", "test");
            startActivity(intent);
        }
    }

//    try {
//        String url = "/ICD10Service.svc/geticd10list/";
//        HttpClient.getInstance().get(LoginActivity.this, url, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                 do what you to do with the UI here
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers,
//                                  String responseBody, Throwable e) {
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//            }
//        });
//    } catch (Exception ex) {
//    }
}
