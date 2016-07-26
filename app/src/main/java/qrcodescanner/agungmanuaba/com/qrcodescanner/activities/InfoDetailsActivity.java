package qrcodescanner.agungmanuaba.com.qrcodescanner.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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
public class InfoDetailsActivity extends AppCompatActivity {
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
    private Spinner mSpinnerKategori;
    private String itemNamaKoleksi;
    private String itemNamaKategori;
    private String itemNamaPembuat;
    private String itemTempatPenyimpanan;
    private String itemKondisi;
    private String itemProvinsi;
    private String itemKabupaten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_details);
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
        mSpinnerKategori = (Spinner) findViewById(R.id.spinner_kategori);

        mInfoErrorLayout = (LinearLayout) findViewById(R.id.info_error_layout);
        mTvError = (TextView) findViewById(R.id.info_error_text);
        mQuitButton = (Button) findViewById(R.id.info_quit_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("qrcode_id")) {
            itemId = extras.getString("qrcode_id");

            // request to web service
            dialog = Common.showWaitView(InfoDetailsActivity.this, getString(R.string.mohon_tunggu));

            requestItemDetails(itemId);
            requestManualRelatedItem(itemId);
        }
    }

    private void requestItemDetails(String itemId) {
        String baseUrl = ApplicationSettings.getServiceUrl(getApplicationContext());
        String url = baseUrl + (getString(R.string.json_item_details_url)) + itemId;

        try {
            HttpClient.getInstance().get(InfoDetailsActivity.this, url, new JsonHttpResponseHandler() {
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

    private void requestAutoRelatedItem(String itemId, String option, String val) {
        String baseUrl = ApplicationSettings.getServiceUrl(getApplicationContext());
        String url = baseUrl + (getString(R.string.json_auto_related_item_url)) + itemId + "&option="+option+"&val="+val;

        try {
            HttpClient.getInstance().get(InfoDetailsActivity.this, url, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            // clear info related layout
                            mInfoRelatedLL.removeAllViews();

                            try {
                                JSONArray relatedItemsArray = response.getJSONArray("data");
                                int arrayLength = relatedItemsArray.length();
                                if (arrayLength > 0) {
                                    for (int i = 0; i < arrayLength; i++) {
                                        JSONObject row = relatedItemsArray.getJSONObject(i);
                                        final String idKoleksi = row.getString("id_koleksi");
                                        String namaKoleksi = row.getString("nama_koleksi");
                                        String keterangan = row.getString("deskripsi");

                                        if (namaKoleksi.isEmpty()) {
                                            namaKoleksi = getString(R.string.empty_value);
                                        }

                                        if (keterangan.isEmpty()) {
                                            keterangan = getString(R.string.empty_value);
                                        }

                                        // instance new layout for related info
                                        View mInfoRelatedLayout = getLayoutInflater().inflate(R.layout.info_details_related_item, null);
                                        mInfoRelatedLayout.setId(i);

                                        TextView mNama = (TextView) mInfoRelatedLayout.findViewById(R.id.info_related_nama);

                                        mNama.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent mapIntent = new Intent(InfoDetailsActivity.this, InfoActivity.class);
                                                mapIntent.putExtra("id_koleksi", idKoleksi);
                                                startActivity(mapIntent);
                                            }
                                        });

                                        TextView mKeterangan = (TextView) mInfoRelatedLayout.findViewById(R.id.info_related_keterangan);

                                        SpannableString content = new SpannableString(namaKoleksi);
                                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                        mNama.setText(content);
                                        mKeterangan.setText(keterangan);

                                        mInfoRelatedLL.addView(mInfoRelatedLayout);
                                    }
                                } else {
                                    setErrorInfoRelatedItem();
                                }
                            } catch (JSONException ex) {
                                setErrorInfoRelatedItem();
                            } catch (Exception ex) {
                                setErrorInfoRelatedItem();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              String responseBody, Throwable e) {
                            Common.toastIt(InfoDetailsActivity.this, e.getMessage());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable
                                throwable, JSONObject errorResponse) {
                            Common.toastIt(InfoDetailsActivity.this, errorResponse.toString());
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

    private void requestManualRelatedItem(String itemId) {
        String baseUrl = ApplicationSettings.getServiceUrl(getApplicationContext());
        String url = baseUrl + (getString(R.string.json_related_item_url)) + itemId;

        try {
            HttpClient.getInstance().get(InfoDetailsActivity.this, url, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {
                                JSONArray relatedItemsArray = response.getJSONArray("data");
                                int arrayLength = relatedItemsArray.length();
                                if (arrayLength > 0) {
                                    for (int i = 0; i < arrayLength; i++) {
                                        JSONObject row = relatedItemsArray.getJSONObject(i);
                                        final String idKoleksi = row.getString("id_koleksi1");
                                        String namaKoleksi = row.getString("nama_koleksi");
                                        String keterangan = row.getString("keterangan");

                                        if (namaKoleksi.isEmpty()) {
                                            namaKoleksi = getString(R.string.empty_value);
                                        }

                                        if (keterangan.isEmpty()) {
                                            keterangan = getString(R.string.empty_value);
                                        }

                                        // instance new layout for related info
                                        View mInfoRelatedLayout = getLayoutInflater().inflate(R.layout.info_details_related_item, null);
                                        mInfoRelatedLayout.setId(i);

                                        TextView mNama = (TextView) mInfoRelatedLayout.findViewById(R.id.info_related_nama);

                                        mNama.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent mapIntent = new Intent(InfoDetailsActivity.this, InfoActivity.class);
                                                mapIntent.putExtra("id_koleksi", idKoleksi);
                                                startActivity(mapIntent);
                                            }
                                        });

                                        TextView mKeterangan = (TextView) mInfoRelatedLayout.findViewById(R.id.info_related_keterangan);

                                        SpannableString content = new SpannableString(namaKoleksi);
                                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                        mNama.setText(content);
                                        mKeterangan.setText(keterangan);

                                        mInfoRelatedLL.addView(mInfoRelatedLayout);
                                    }
                                } else {
//                                    setErrorInfoRelatedItem();
                                    requestAutoRelatedItem();
                                }
                            } catch (JSONException ex) {
                                setErrorInfoRelatedItem();
                            } catch (Exception ex) {
                                setErrorInfoRelatedItem();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers,
                                              String responseBody, Throwable e) {
                            Common.toastIt(InfoDetailsActivity.this, e.getMessage());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable
                                throwable, JSONObject errorResponse) {
                            Common.toastIt(InfoDetailsActivity.this, errorResponse.toString());
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

    private void requestAutoRelatedItem() {
        mSpinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedString = adapterView.getItemAtPosition(i).toString();
                switch (selectedString) {
                    case Constants.KAT_NAMA_KOLEKSI:
                        requestAutoRelatedItem(itemId, Constants.KAT_OPT_NAMA_KOLEKSI, itemNamaKoleksi);
                        break;
                    case Constants.KAT_KATEGORI:
                        requestAutoRelatedItem(itemId, Constants.KAT_OPT_KATEGORI, itemNamaKategori);
                        break;
                    case Constants.KAT_NAMA_PEMBUAT:
                        requestAutoRelatedItem(itemId, Constants.KAT_OPT_NAMA_PEMBUAT, itemNamaPembuat);
                        break;
                    case Constants.KAT_TEMPAT_PENYIMPANAN:
                        requestAutoRelatedItem(itemId, Constants.KAT_OPT_TEMPAT_PENYIMPANAN, itemTempatPenyimpanan);
                        break;
                    case Constants.KAT_KONDISI:
                        requestAutoRelatedItem(itemId, Constants.KAT_OPT_KONDISI, itemKondisi);
                        break;
                    case Constants.KAT_PROVINSI:
                        requestAutoRelatedItem(itemId, Constants.KAT_OPT_PROVINSI, itemProvinsi);
                        break;
                    case Constants.KAT_KABUPATEN:
                        requestAutoRelatedItem(itemId, Constants.KAT_OPT_KABUPATEN, itemKabupaten);
                        break;

                    default:
                        requestAutoRelatedItem(itemId, Constants.KAT_OPT_NAMA_KOLEKSI, itemNamaKoleksi);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void setErrorInfoRelatedItem() {
        // instance new layout for related info
        View mInfoRelatedLayout = getLayoutInflater().inflate(R.layout.info_details_related_item, null);

        TextView mNama = (TextView) mInfoRelatedLayout.findViewById(R.id.info_related_nama);
        mNama.setVisibility(View.GONE);

        TextView mKeterangan = (TextView) mInfoRelatedLayout.findViewById(R.id.info_related_keterangan);
        mKeterangan.setText(getString(R.string.empty_data));

        mInfoRelatedLL.addView(mInfoRelatedLayout);
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
            itemNamaKoleksi = itemDetails.getString("nama_koleksi");
            itemNamaKategori = itemDetails.getString("nama_kategori");
            itemTempatPenyimpanan = itemDetails.getString("nama_museum");
            String deskripsi = itemDetails.getString("deskripsi");
            itemKondisi = itemDetails.getString("kondisi");
            itemNamaPembuat = itemDetails.getString("nama_pembuat");
            String longitude = itemDetails.getString("longtitude");
            String latitude = itemDetails.getString("latitude");
            itemProvinsi = itemDetails.getString("id_prov_pembuatan");
            itemKabupaten = itemDetails.getString("id_kab_pembuatan");
            String id_unit_ukuran = itemDetails.getString("nama_unit_ukuran");
            String panjang = itemDetails.getString("panjang");
            String lebar = itemDetails.getString("lebar");
            String tinggi = itemDetails.getString("tinggi");
            String tanggal_update = itemDetails.getString("modified_date");
            String kontributor = itemDetails.getString("username");
            String dimensi = panjang + "/" + lebar + "/" + tinggi + " " + id_unit_ukuran;
            String latLong = latitude + "," + longitude;

            String imageUrl = Constants.IMAGE_URL + gambar;
            mInfoBudaya.setText(itemNamaKoleksi);
            mInfoKategori.setText(itemNamaKategori);
            mInfoTempatPenyimpanan.setText(itemTempatPenyimpanan);
            mInfoPembuat.setText(itemNamaPembuat);
            mInfoDeskripsi.setText(deskripsi);
            mInfoKondisi.setText(itemKondisi);
            mInfoLatLong.setText(latLong);
            mInfoProvinsi.setText(itemProvinsi);
            mInfoKabupaten.setText(itemKabupaten);
            mInfoDimensi.setText(dimensi);
            mInfoTanggalUpdate.setText(tanggal_update);
            mInfoKontributor.setText(kontributor);
            mInfoBendaTerkait.setText(getString(R.string.info_benda_terkait) + " " + itemNamaKoleksi);

            mInfoDetailsLayout.setVisibility(View.VISIBLE);
            Common.hideWaitView(dialog);

            mInfoGambar.setImageUrl(imageUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            Common.toastIt(InfoDetailsActivity.this, e.getMessage());
            Common.hideWaitView(dialog);
        }
    }
}