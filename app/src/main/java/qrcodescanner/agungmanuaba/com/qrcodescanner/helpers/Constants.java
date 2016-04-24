package qrcodescanner.agungmanuaba.com.qrcodescanner.helpers;

/**
 * Created by Ari_S on 7/27/2015.
 */
public class Constants {
    //Time format
    public static final String TIME_FORMAT = "yyyy-MM-dd kk:mm";

    public static final String BIRTH_DATE_FORMAT = "dd-MM-yyyy";

    //Time Zone
    public static final String UTC = "UTC";

    //Encryption
    public static final String UTF8 = "UTF8";
    public static final String DES = "DES";

    //URL
    public static final String URL_GOOGLE_MAP = "http://maps.google.co.in/maps?q=";

    //Conditions
    public static final String STRING_TRUE = "true";
    public static final String STRING_FALSE = "false";
    public static final String STRING_NULL = "null";

    //Intent Types
    public static final String TYPE_TEXT_HTML = "text/html";

    //Directory Path
    public static final String PATH_DATA = "/Android/data/";
    public static final String PATH_ANDROID = "/Android/";
    public static final String PATH_CAMERA = "/DCIM/Camera";

    public static final String K_MIME_TYPE_JSON = "application/json";

    // Preference Serialization
    public static final String K_FIELD_SERIALIZED_ID = "id";
    public static final String K_FIELD_TYPE = "type";
    public static final String K_FIELD_NAME = "name";
    public static final String K_FIELD_IS_MANDATORY = "isMandatory";
    public static final String K_FIELD_KEY = "key";
    public static final String K_FIELD_DEFAULT_VALUE = "defaultValue";
    public static final String K_FIELD_FIELD_LENGTH = "fieldLength";
    public static final String K_FIELD_IS_NUMERIC_ONLY = "isNumericOnly";
    public static final String K_FIELD_OPTIONS = "options";

    public static final String BASE_URL = "http://emuseum.16mb.com/emuseum/admin/";
    public static final String SERVICE_URL = BASE_URL + "Mobileserver/";
    public static final String IMAGE_URL = BASE_URL + "gambar/koleksi/";

    public static final String KAT_NAMA_KOLEKSI = "Nama Koleksi";
    public static final String KAT_KATEGORI = "Kategori";
    public static final String KAT_NAMA_PEMBUAT = "Nama Pembuat";
    public static final String KAT_TEMPAT_PENYIMPANAN = "Tempat Penyimpanan";
    public static final String KAT_KONDISI = "Kondisi";
    public static final String KAT_PROVINSI = "Provinsi";
    public static final String KAT_KABUPATEN = "Kabupaten";

    public static final String KAT_OPT_NAMA_KOLEKSI = "tb_koleksi.nama_koleksi";
    public static final String KAT_OPT_KATEGORI = "tb_koleksi.id_kategori";
    public static final String KAT_OPT_NAMA_PEMBUAT = "tb_koleksi.nama_pembuat";
    public static final String KAT_OPT_TEMPAT_PENYIMPANAN = "tb_koleksi.tempat_penyimpanan";
    public static final String KAT_OPT_KONDISI = "tb_koleksi.kondisi";
    public static final String KAT_OPT_PROVINSI = "tb_koleksi.id_prov_pembuatan";
    public static final String KAT_OPT_KABUPATEN = "tb_koleksi.id_kab_pembuatan";
}
