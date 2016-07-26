package qrcodescanner.agungmanuaba.com.qrcodescanner.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import qrcodescanner.agungmanuaba.com.qrcodescanner.R;
import qrcodescanner.agungmanuaba.com.qrcodescanner.helpers.ApplicationSettings;
import qrcodescanner.agungmanuaba.com.qrcodescanner.helpers.Common;
import qrcodescanner.agungmanuaba.com.qrcodescanner.helpers.Constants;
import qrcodescanner.agungmanuaba.com.qrcodescanner.helpers.HttpClient;

/**
 * Created by Ari_S on 1/19/2016.
 */
public class InfoActivity extends AppCompatActivity {
    private String itemId;
    private SmartImageView mInfoGambar;
    private TextView mInfoBudaya;
    private TextView mInfoKategori;
    private TextView mInfoDeskripsi;
    private TextView mInfoPembuat;
    private TextView mInfoTempatPenyimpanan;
    private TextView mInfoTeknikPembuatan;
    private TextView mInfoDimensi;
    private TextView mInfoProvinsi;
    private TextView mInfoKabupaten;
    private TextView mInfoKondisi;
    private TextView mInfoLatLong;
    private TextView mInfoKontributor;
    private TextView mInfoTanggalUpdate;
    private ProgressDialog dialog;
    private ScrollView mInfoDetailsLayout;
    private LinearLayout mInfoRelatedLL;
    private TextView mTvError;
    private LinearLayout mInfoErrorLayout;
    private Button mQuitButton;
    private TextView mInfoBendaTerkait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        overridePendingTransition(R.anim.activity_open_translate,
                R.anim.slide_right_out);

        mInfoDetailsLayout = (ScrollView) findViewById(R.id.info_details_layout);
        mInfoGambar = (SmartImageView) findViewById(R.id.info_gambar);
        mInfoBudaya = (TextView) findViewById(R.id.info_budaya);
        mInfoKategori = (TextView) findViewById(R.id.info_kategori);
        mInfoDeskripsi = (TextView) findViewById(R.id.info_deskripsi);
        mInfoPembuat = (TextView) findViewById(R.id.info_pembuat);
        mInfoTempatPenyimpanan = (TextView) findViewById(R.id.info_tempat_penyimpanan);
        mInfoTeknikPembuatan = (TextView) findViewById(R.id.info_teknik_pembuatan);
        mInfoDimensi = (TextView) findViewById(R.id.info_dimensi);
        mInfoProvinsi = (TextView) findViewById(R.id.info_provinsi);
        mInfoKabupaten = (TextView) findViewById(R.id.info_kabupaten);
        mInfoKondisi = (TextView) findViewById(R.id.info_kondisi);
        mInfoLatLong = (TextView) findViewById(R.id.info_lat_long);
        mInfoKontributor = (TextView) findViewById(R.id.info_kontributor);
        mInfoTanggalUpdate = (TextView) findViewById(R.id.info_tanggal_update);
        mInfoBendaTerkait = (TextView) findViewById(R.id.info_related_title);

        mInfoRelatedLL = (LinearLayout) findViewById(R.id.info_related);

        mInfoErrorLayout = (LinearLayout) findViewById(R.id.info_error_layout);
        mTvError = (TextView) findViewById(R.id.info_error_text);
        mQuitButton = (Button) findViewById(R.id.info_quit_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("id_koleksi")) {
            itemId = extras.getString("id_koleksi");

            // request to web service
            dialog = Common.showWaitView(InfoActivity.this, getString(R.string.mohon_tunggu));

            requestItemDetails(itemId);
        }

    }

    private void requestItemDetails(String itemId) {
        String baseUrl = ApplicationSettings.getServiceUrl(getApplicationContext());
        String url = baseUrl + (getString(R.string.json_item_details_url)) + itemId;

        try {
            HttpClient.getInstance().get(InfoActivity.this, url, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {
                                JSONArray itemDetailsArray = response.getJSONArray("data");
                                JSONObject itemDetails = itemDetailsArray.getJSONObject(0);
                                setUI(itemDetails);
                            } catch (JSONException ex) {
                                setErrorInfoItemDetails();
                            } catch (Exception ex) {
                                setErrorInfoItemDetails();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              String responseBody, Throwable e) {
                            setErrorInfoItemDetails();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable
                                throwable, JSONObject errorResponse) {
                            setErrorInfoItemDetails();
                        }
                    }

            );
        } catch (JSONException e) {
            e.printStackTrace();
            setErrorInfoItemDetails();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            setErrorInfoItemDetails();
        }
    }

    private void setErrorInfoItemDetails() {
        mInfoErrorLayout.setVisibility(View.VISIBLE);
        mTvError.setText(getString(R.string.empty_data));
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Common.hideWaitView(dialog);
    }

    private void setUI(JSONObject itemDetails) {
        try {
            String gambar = itemDetails.getString("gambar");
            final String budaya = itemDetails.getString("nama_koleksi");
            String kategori = itemDetails.getString("nama_kategori");
            String tempat_penyimpanan = itemDetails.getString("nama_museum");
            String deskripsi = itemDetails.getString("deskripsi");
            final double longitude = itemDetails.getDouble("longtitude");
            final double latitude = itemDetails.getDouble("latitude");
            String id_prov_pembuatan = itemDetails.getString("id_prov_pembuatan");
            String id_kab_pembuatan = itemDetails.getString("id_kab_pembuatan");
            String id_unit_ukuran = itemDetails.getString("nama_unit_ukuran");
            String panjang = itemDetails.getString("panjang");
            String lebar = itemDetails.getString("lebar");
            String tinggi = itemDetails.getString("tinggi");
            String tanggal_update = itemDetails.getString("modified_date");
            String kontributor = itemDetails.getString("username");
            String dimensi = panjang + "/" + lebar + "/" + tinggi + " " + id_unit_ukuran;
            String latLong = String.valueOf(latitude) + "," + String.valueOf(longitude);

            String imageUrl = Constants.IMAGE_URL + gambar;
            mInfoBudaya.setText(budaya);
            mInfoKategori.setText(kategori);
            mInfoTempatPenyimpanan.setText(tempat_penyimpanan);
            mInfoDeskripsi.setText(deskripsi);
            mInfoLatLong.setText(latLong);
            mInfoProvinsi.setText(id_prov_pembuatan);
            mInfoKabupaten.setText(id_kab_pembuatan);
            mInfoDimensi.setText(dimensi);
            mInfoTanggalUpdate.setText(tanggal_update);
            mInfoKontributor.setText(kontributor);

            mInfoDetailsLayout.setVisibility(View.VISIBLE);

            SpannableString content = new SpannableString(getString(R.string.cek_lokasi));
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            mInfoBendaTerkait.setText(content);
            mInfoBendaTerkait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapIntent = new Intent(InfoActivity.this, InfoMapsActivity.class);
                    mapIntent.putExtra("item_name", budaya);
                    mapIntent.putExtra("latitude", latitude);
                    mapIntent.putExtra("longitude", longitude);
                    startActivity(mapIntent);
                }
            });

            Common.hideWaitView(dialog);

            mInfoGambar.setImageUrl(imageUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            Common.toastIt(InfoActivity.this, e.getMessage());
            Common.hideWaitView(dialog);
        }
    }
}
