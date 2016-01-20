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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        overridePendingTransition(R.anim.activity_open_translate,
                R.anim.slide_right_out);

        infoLink = (TextView) findViewById(R.id.info_link);

        infoLink.setOnClickListener(this);

//        Bundle extras = getIntent().getExtras();
//        if(extras != null && extras.containsKey("deepLink")) {
//            int detailsViewMatterId = extras.getInt("entity_id");
//
//            getIntent().removeExtra("deepLink");
//            Intent intent = new Intent(getApplicationContext(), MatterDetailsActivity.class);
//
//            // add parameter to be processed in matte	r details
//            // activity
//            // request convey
//            intent.putExtra("showMatterDetails", extras.getBoolean("showMatterDetails"));
//            intent.putExtra("TabPosition", extras.getInt("TabPosition"));
//            intent.putExtra("entity_id", detailsViewMatterId);
//            intent.putExtra("deepLink", true);
//
//            //need to set for "return to my matters" button, so it loads the matter summary list
//            ApplicationSettings.setShouldReload(getApplicationContext(), true);
//
//            // set true if received notification
//            ApplicationSettings.setReceiveNotification(getApplicationContext(), true);
//
//            startActivity(intent);
//            return;
//        }

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
