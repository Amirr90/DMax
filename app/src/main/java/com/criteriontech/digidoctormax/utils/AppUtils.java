package com.criteriontech.digidoctormax.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.criteriontech.digidoctormax.BuildConfig;
import com.criteriontech.digidoctormax.R;
import com.criteriontech.digidoctormax.databinding.ImagePreviewBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {


    public static final String CALL_DISCONNECTED = "Disconnected";
    public static final String CALL_ACCEPT = "Connected";
    public static final String CALL_STARTED = "Started";
    public static final String VIDEO_CALLS = "videoCalls";
    public static final String IS_FIRST_TIME = "isFirstTime";
    public static final String fcmToken = "fcmToken";
    public static final String IS_LOGIN = "isLogin";
    public static final String USER = "user";
    public static final String USER_MAIN = "user_main";
    public static final String TOKEN = "token";
    public static final String MOBILE_NUMBER = "number";
    public static final int REQ_PERMISSION_CODE = 1;
    public static final String MEMBER_ID = "memberId";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_PATIENT_NAME = "patientName";
    public static final String KEY_DOC_ID = "docId";
    public static final String APPOINTMENT_DATE = "appointmentDate";
    public static final String APPOINTMENT_TIME = "appointmentTime";
    public static final String IS_REVISIT = "is_revist";
    public static final String KEY_IS_ERA_USER = "isEraUser";
    public static final String KEY_APPOINTMENT_ID = "appointmentId";
    public static final String KEY_PRESCRIPTION_ID = "prescriptionId";
    public static final String CALL_STATUS = "callStatus";
    public static final String CALL_REJECTED_BY = "rejectedBy";
    public static final String PATIENT = "patient";
    public static final String CALL_MISSED = "Missed";
    public static final String RINGING = "Ringing";
    public static final String VIDEO_CALLS_DEMO = "videoCallDemo";
    public static final String CALL_INITIATED_TIMESTAMP = "callInitiatedTimestamp";
    public static final String ROOM_CODE = "roomCode";
    public static final String CALL_REJECTED = "Rejected";
    public static final String UID = "uid";
    public static final String DOCTOR_ID = "docId";
    public static final String DOCTOR_NAME = "doctorName";
    public static final String PATIENT_NAME = "patientName";

    private static final String TAG = "AppUtils";
    public static Toast mToast;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static final String VITAL_ID = "vitalId";
    public static final String VITAL_NAME = "vitalName";
    public static final String VITAL_IMAGE = "vitalImage";
    public static final String PULSE_RATE_ID = "3";
    public static final String BLOOD_SUGAR_ID = "10";
    public static final String SPO2_ID = "56";
    public static final String RESPIRATORY_ID = "7";
    public static final String TEMPERATURE_ID = "5";
    public static final String BP_ID = "-1";


    public static String parseDateIn(String inDate, String outPattern) {

        String inputPattern = "MMM dd, yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(inDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "parseDateIn: " + inDate);
        Log.d(TAG, "parseDateOut: " + str);
        return str;

    }


    public static void updateTodatabase(String callDisconnected, String roomCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("callStatus", callDisconnected);
        map.put("disconnectedTimestamp", System.currentTimeMillis());
        map.put("disconnectedBy", "doctor");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(AppUtils.VIDEO_CALLS).document(roomCode)
                .update(map).addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.getLocalizedMessage()));
    }

    public static List<String> getBankNameList() {
        List<String> strings = new ArrayList<>();
        strings.add("Aditya Birla Idea Payments Bank Ltd");
        strings.add("Airtel Payments Bank Ltd");
        strings.add("Andhra Pradesh GVB");
        strings.add("Andhra Pragathi Grameena Bank");
        strings.add("Arunachal Pradesh Rural Bank");
        strings.add("Aryavart Bank");
        strings.add("Assam Gramin Vikash Bank");
        strings.add("Au Small Finance Bank Ltd");
        strings.add("Axis Bank Ltd");
        strings.add("Bandhan Bank Ltd");
        strings.add("Bangiya Gramin Vikash Bank");
        strings.add("Bank of Baroda");
        strings.add("Bank of India");
        strings.add("Bank of Maharashtra");
        strings.add("Baroda Gujarat Gramin Bank");
        strings.add("Baroda Rajasthan Kshetriya Gramin Bank");
        strings.add("Baroda Uttar Pradesh Gramin Bank");
        strings.add("Canara Bank");
        strings.add("Capital Small Finance Bank Ltd");
        strings.add("Central Bank of India");
        strings.add("Chaitanya Godavari GB");
        strings.add("Chhattisgarh Rajya Gramin Bank");
        strings.add("City Union Bank Ltd");
        strings.add("Coastal Local Area Bank Ltd");
        strings.add("CSB Bank Limited");
        strings.add("Dakshin Bihar Gramin Bank");
        strings.add("DCB Bank Ltd");
        strings.add("Dhanlaxmi Bank Ltd");
        strings.add("Ellaquai Dehati Bank");
        strings.add("Equitas Small Finance Bank Ltd");
        strings.add("ESAF Small Finance Bank Ltd");
        strings.add("Export - Import Bank of India");
        strings.add("Federal Bank Ltd");
        strings.add("Fincare Small Finance Bank Ltd");
        strings.add("FINO Payments Bank Ltd");
        strings.add("HDFC Bank Ltd");
        strings.add("Himachal Pradesh Gramin Bank");
        strings.add("ICICI Bank Ltd");
        strings.add("IDBI Bank Limited");
        strings.add("IDFC FIRST Bank Limited");
        strings.add("India Post Payments Bank Ltd");
        strings.add("Indian Bank");
        strings.add("Indian Overseas Bank");
        strings.add("IndusInd Bank Ltd");
        strings.add("J & K Grameen Bank");
        strings.add("Jammu & Kashmir Bank Ltd");
        strings.add("Jana Small Finance Bank Ltd");
        strings.add("Jharkhand Rajya Gramin Bank");
        strings.add("Jio Payments Bank Ltd");
        strings.add("Karnataka Bank Ltd");
        strings.add("Karnataka Gramin Bank");
        strings.add("Karnataka Vikas Gramin Bank");
        strings.add("Karur Vysya Bank Ltd");
        strings.add("Kashi Gomti Samyut Gramin Bank");
        strings.add("Kerala Gramin Bank");
        strings.add("Kotak Mahindra Bank Ltd");
        strings.add("Krishna Bhima Samruddhi LAB Ltd");
        strings.add("Lakshmi Vilas Bank Ltd");
        strings.add("Madhya Pradesh Gramin Bank");
        strings.add("Madhyanchal Gramin Bank");
        strings.add("Maharashtra GB");
        strings.add("Manipur Rural Bank");
        strings.add("Meghalaya Rural Bank");
        strings.add("Mizoram Rural Bank");
        strings.add("Nagaland Rural Bank");
        strings.add("Nainital bank Ltd");
        strings.add("National Bank");
        strings.add("National Housing Bank");
        strings.add("Nawanagar Cooperative Bank");
        strings.add("North East Small finance Bank Ltd");
        strings.add("NSDL Payments Bank Limited");
        strings.add("Odisha Gramya Bank");
        strings.add("Paschim Banga Gramin Bank");
        strings.add("Paytm Payments Bank Ltd");
        strings.add("Prathama U.P.Gramin Bank");
        strings.add("Puduvai Bharathiar Grama Bank");
        strings.add("Punjab & Sind Bank");
        strings.add("Punjab Gramin Bank");
        strings.add("Punjab National Bank");
        strings.add("Purvanchal Bank");
        strings.add("Rajasthan Marudhara Gramin Bank");
        strings.add("RBL Bank Ltd");
        strings.add("Saptagiri Grameena Bank");
        strings.add("Sarva Haryana Gramin Bank");
        strings.add("Saurashtra Gramin Bank");
        strings.add("Small Industries Development Bank of India");
        strings.add("South Indian Bank Ltd");
        strings.add("State Bank of India");
        strings.add("Subhadra Local Bank Ltd");
        strings.add("Suryoday Small Finance Bank Ltd");
        strings.add("Tamil Nadu Grama Bank");
        strings.add("Tamilnad Mercantile Bank Ltd");
        strings.add("Telengana Grameena Bank");
        strings.add("Tripura Gramin Bank");
        strings.add("UCO Bank");
        strings.add("Ujjivan Small Finance Bank Ltd");
        strings.add("Union Bank of India");
        strings.add("Utkal Grameen Bank");
        strings.add("Utkarsh Small Finance Bank Ltd");
        strings.add("Uttar Bihar Gramin Bank");
        strings.add("Uttarakhand Gramin Bank");
        strings.add("UttarBanga Kshetriya Gramin Bank");
        strings.add("Vidharbha Konkan Gramin Bank");
        strings.add("YES Bank Ltd");
        strings.add("AB Bank Ltd");
        strings.add("Abu Dhabi Commercial Bank Ltd");
        strings.add("American Express Banking Corp");
        strings.add("Australia and New Zealand Banking Group Ltd");
        strings.add("Bank of America");
        strings.add("Bank of Bahrain & Kuwait BSC");
        strings.add("Bank of Ceylon");
        strings.add("Bank of Nova Scotia");
        strings.add("Barclays Bank Plc");
        strings.add("BNP Paribas");
        strings.add("Citibank");
        strings.add("Cooperatieve Rabobank U.A.");
        strings.add("Credit Agricole Corporate & Investment Bank");
        strings.add("Credit Suisse A.G");
        strings.add("CTBC Bank Co Ltd");
        strings.add("DBS Bank Ltd");
        strings.add("Deutsche Bank");
        strings.add("Doha Bank");
        strings.add("Emirates NBD Bank PJSC");
        strings.add("First Abu Dhabi Bank PJSC");
        strings.add("FirstRand Bank Ltd");
        strings.add("HSBC Ltd");
        strings.add("Industrial & Commercial Bank of China Ltd");
        strings.add("Industrial Bank of Korea");
        strings.add("J.P.Morgan Chase Bank");
        strings.add("JSC VTB Bank");
        strings.add("KEB Hana Bank");
        strings.add("Krung Thai Bank Public Co.Ltd");
        strings.add("Mashreq Bank PSC");
        strings.add("Mizuho Bank Ltd");
        strings.add("National Australia Bank");
        strings.add("PT Bank Maybank Indonesia TBK");
        strings.add("Qatar National Bank SAQ");
        strings.add("Sberbank");
        strings.add("Shinhan Bank");
        strings.add("Societe Generale");
        strings.add("Sonali Bank Ltd");
        strings.add("Standard Chartered Bank");
        strings.add("Sumitomo Mitsui Banking Corporation");
        strings.add("The Bank of Tokyo - Mitsubishi UFJ Ltd");
        strings.add("The Royal Bank of Scotland plc");
        strings.add("United Overseas Bank Ltd");
        strings.add("Westpac Banking Corporation");
        strings.add("Woori Bank");
        return strings;
    }


    public static String getVitalMaxValue(String vitalId) {
        if (vitalId.equalsIgnoreCase(BP_ID))
            return "140/90mmg Higher";
        else return "";
    }

    public static String getVitalMinValue(String vitalId) {
        if (vitalId.equalsIgnoreCase(BP_ID))
            return "90/60mmg Lower";
        else return "";
    }


    static ProgressDialog progressDialog;
    private static boolean doubleBackToExitPressedOnce;

    public static float convertDpToPixel(float dp) {
        return dp * (((float) Resources.getSystem().getDisplayMetrics().densityDpi) / 160.0f);
    }

    public static float convertPixelsToDp(float px) {
        return px / (((float) Resources.getSystem().getDisplayMetrics().densityDpi) / 160.0f);
    }

    public static String parseDate(String inDate, String outPattern) {

        String inputPattern = "dd/MM/yy";
        // String outputPattern = "dd MMMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(inDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;

    }

    public static String getJSONFromModel(Object o) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(o);
        try {
            JSONObject request = new JSONObject(jsonString);
            return request.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Animation fadeIn(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.fade_in);
    }

    public static String parseDateToFormatDMY(String oldDate) {
        String inputPattern = "yyyy/MM/dd";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(oldDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String formatDate(String inpDate) {
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(inpDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
// format the java.util.Date object to the desired format
        String formattedDate = new SimpleDateFormat("MMMM dd, yyyy").format(date);

        return formattedDate;
    }

    public static String formatOutputDate(String inpDate) {
        Date date = null;

        try {
            date = new SimpleDateFormat("MMM dd, yyyy").parse(inpDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        return formattedDate;
    }

    public static StringBuilder getTimeSlots(String jsonString) throws JSONException {

        JSONArray jsonArray = new JSONArray(jsonString);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            Log.d("TAG", "getDocTiming: " + jsonObject.getString("dayName"));
            String day = jsonObject.getString("dayName");
            JSONArray timeDetails = new JSONArray(jsonObject.getString("timeDetails"));

            builder.append(day.substring(0, 3));

            for (int a = 0; a < timeDetails.length(); a++) {
                JSONObject timeDetailObject = (JSONObject) timeDetails.get(a);
                String timeFrom = timeDetailObject.getString("timeFrom");
                String timeTo = timeDetailObject.getString("timeTo");
                builder.append(" : " + timeFrom + " - " + timeTo);
            }


            builder.append("\n");
        }


        return builder;
    }

    public static String formatInputDate(String inpDate) {
        Date date = null;

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(inpDate);
            String formattedDate = new SimpleDateFormat("MMM dd, yyyy").format(date) + "";

            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String print(String mString) {
        return mString;
    }

    public static String printD(String Tag, String mString) {
        return mString;
    }

    public static String printE(String Tag, String mString) {
        return mString;
    }

    public static int startPosition(String word, String sourceString) {
        int startingPosition = sourceString.indexOf(word);
        print("startingPosition" + word + " " + startingPosition);
        return startingPosition;
    }

    public static int endPosition(String word, String sourceString) {
        int endingPosition = sourceString.indexOf(word) + word.length();
        print("startingPosition" + word + " " + endingPosition);
        return endingPosition;
    }

    public static void showToastSort(Context context, String text) {
        if (mToast != null && mToast.getView().isShown()) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        mToast.show();
    }

    public static void rotateView(View view) {
        RotateAnimation rotate =
                new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        view.startAnimation(rotate);
    }

    public static void showSoftKeyboard(Activity activity) {

        if (activity != null) {
            try {
                @SuppressLint("WrongConstant") InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                View view = activity.getCurrentFocus();
                if (view != null) {
                    IBinder binder = view.getWindowToken();
                    if (binder != null) {
                        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            try {
                @SuppressLint("WrongConstant") InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
                View view = activity.getCurrentFocus();
                if (view != null) {
                    IBinder binder = view.getWindowToken();
                    if (binder != null) {
                        inputMethodManager.hideSoftInputFromWindow(binder, 0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static float convertDpToPixel(float dp, Context context) {
        return (((float) getDisplayMetrics(context).densityDpi) / 160.0f) * dp;
    }

    public static int convertDpToPixelSize(float dp, Context context) {
        float pixels = convertDpToPixel(dp, context);
        int res = (int) (0.5f + pixels);
        if (res != 0) {
            return res;
        }
        if (pixels == 0.0f) {
            return 0;
        }
        if (pixels > 0.0f) {
            return 1;
        }
        return -1;
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhone(String pass) {
        return pass != null && pass.length() == 10;
    }


    public static void setCustomFont(Activity mActivity, TextView mTextView, String asset) {
        mTextView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), asset));
    }

    public static void showRequestDialog(Activity activity) {

        //Log.d("Token-Number", AppSettings.getString(AppSettings.token));

        try {
            if (!((Activity) activity).isFinishing()) {
                if (progressDialog == null) {
                    //progressDialog = new ProgressDialog(activity);
                    //progressDialog.setCancelable(false);
                    //progressDialog.setMessage(activity.getString(R.string.pleaseWait));
                    //progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

                    progressDialog = ProgressDialog.show(activity, null, null, false, true);
//                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.fullTransparent)));
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));
                    progressDialog.setCancelable(false);
                    progressDialog.setContentView(R.layout.progress_bar);
                    progressDialog.show();
                } else {
                    //progressDialog = new ProgressDialog(activity);
                    //progressDialog.setCancelable(false);
                    //progressDialog.setMessage(activity.getString(R.string.pleaseWait));
                    //progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                    //progressDialog.show();

                    progressDialog = ProgressDialog.show(activity, null, null, false, true);
//                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));
                    progressDialog.setCancelable(false);
                    progressDialog.setContentView(R.layout.progress_bar);
                    progressDialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showRequestDialog(Activity activity, boolean isCancelable) {

        //Log.d("Token-Number", AppSettings.getString(AppSettings.token));

        try {
            if (!((Activity) activity).isFinishing()) {
                if (progressDialog == null) {

                    progressDialog = ProgressDialog.show(activity, null, null, false, true);
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    progressDialog.setContentView(R.layout.progress_bar);
                    progressDialog.setCancelable(isCancelable);
                    progressDialog.setCanceledOnTouchOutside(isCancelable);
                    progressDialog.show();
                } else {

                    progressDialog = ProgressDialog.show(activity, null, null, false, true);
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    progressDialog.setContentView(R.layout.progress_bar);
                    progressDialog.show();
                    progressDialog.setCancelable(isCancelable);
                    progressDialog.setCanceledOnTouchOutside(isCancelable);
                    progressDialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showRequestDialog(Activity activity, String message) {
        if (progressDialog == null) {
            //progressDialog = new ProgressDialog(activity, R.style.MyAlertDialogStyle);
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(message);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();
        }
    }

    public static void getKeyHash(Activity activity) {
        PackageInfo info;
        try {
            info = activity.getPackageManager().getPackageInfo("com.techtik.android", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    public static void hideDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTncDate() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        return df.format(new Date());
    }

    /*public static void showErrorMessage(View mView, String errorMessage, Context mActivity) {
        Snackbar snackbar = Snackbar.make(mView, errorMessage, Snackbar.LENGTH_SHORT);
        TextView tv = (TextView) (snackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
        *//*Typeface font = Typeface.createFromAsset(mActivity.getAssets(), "centurygothic.otf");
        tv.setTypeface(font);*//*

        snackbar.show();
    }*/

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }

    @SuppressLint("NewApi")
    public static void onBackPressed(Activity mActivity) {
        if (doubleBackToExitPressedOnce) {
            mActivity.finishAffinity();
            return;
        }

        doubleBackToExitPressedOnce = true;

        AppUtils.showToastSort(mActivity, mActivity.getString(R.string.exit_text));
        //Toast.makeText(mActivity, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, 1000);

    }


    public static String toCamelCaseSentence(String s) {
        if (s == null) {
            return "";
        }
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String toCamelCaseWord : words) {
            sb.append(toCamelCaseWord(toCamelCaseWord));
        }
        return sb.toString().trim();
    }

    public static String toCamelCaseWord(String word) {
        if (word == null) {
            return "";
        }
        switch (word.length()) {
            case 0:
                return "";
            case 1:
                return word.toUpperCase(Locale.getDefault()) + " ";
            default:
                return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase(Locale.getDefault()) + " ";
        }
    }

    public static String split(String str) {
        String result = "";
        if (str.contains(" ")) {
            return toCamelCaseWord(str.split("\\s+")[0]);
        }
        return toCamelCaseWord(str);
    }

    public static void expand(final View v) {
        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WindowManager.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {

        v.setVisibility(View.GONE);

        /*final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);*/
    }


    // GetDeviceId
    public static String getDeviceID(Context ctx) {
        return Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getDateCurrentTimeZone(long timestamp) {

        timestamp = timestamp * 1000;

        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy, hh:mm aa");

        //System.out.println(timestamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        //System.out.println(formatter.format(calendar.getTime()));

        String ret = formatter.format(calendar.getTime());

        return ret;
    }

    public static String getDateFromTimestamp(long timestamp) {

        DateFormat formatter = new SimpleDateFormat("dd MMM hh:mm");

        //System.out.println(timestamp);

        Calendar calendar = Calendar.getInstance();
        if (timestamp < 1000000000000L) {
            calendar.setTimeInMillis(timestamp * 1000);
        }
        //System.out.println(formatter.format(calendar.getTime()));

        String ret = formatter.format(calendar.getTime());

        return ret;
    }

    public static String getTimeLineDate(long timestamp) {

        DateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");

        //System.out.println(timestamp);

        Calendar calendar = Calendar.getInstance();
        if (timestamp < 1000000000000L) {
            calendar.setTimeInMillis(timestamp * 1000);
        }
        //System.out.println(formatter.format(calendar.getTime()));

        String ret = formatter.format(calendar.getTime());

        return ret;
    }

    public static String getTimeLineTime(long timestamp) {

        DateFormat formatter = new SimpleDateFormat("hh:mm aa");

        //System.out.println(timestamp);

        Calendar calendar = Calendar.getInstance();
        if (timestamp < 1000000000000L) {
            calendar.setTimeInMillis(timestamp * 1000);
        }
        //System.out.println(formatter.format(calendar.getTime()));

        String ret = formatter.format(calendar.getTime());

        return ret;
    }

    public static String getTimeFromDate(String dateTime) {

        String result = "";

        String[] time = dateTime.split(" ");

        return time[1];
    }

    public static String getTimeFromTimestamp(long timestamp) {

        DateFormat formatter = new SimpleDateFormat("hh:mm aa");

        //System.out.println(timestamp);

        Calendar calendar = Calendar.getInstance();
        if (timestamp < 1000000000000L) {
            calendar.setTimeInMillis(timestamp * 1000);
        }
        //System.out.println(formatter.format(calendar.getTime()));

        String ret = formatter.format(calendar.getTime());

        return ret;
    }

    public static String getCurrentDate() {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current Date => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getCurrentDateSendFormat() {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current Date => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getCurrentTime() {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current Time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getCurrentTimeIn12Hour() {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current Time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getCurrentDateNew() {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        return formattedDate;
    }


    public static String getCurrentDateTime() {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy hh:mm aa");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getCurrentDateYMD(int addDays) {

        Calendar mcurrentDate = Calendar.getInstance();
        mcurrentDate.add(Calendar.DAY_OF_MONTH, addDays);

        Date c = mcurrentDate.getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getCurrentDateDMY(int addDays) {

        Calendar mcurrentDate = Calendar.getInstance();
        mcurrentDate.add(Calendar.DAY_OF_MONTH, addDays);

        Date c = mcurrentDate.getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public static String getNewDateTimeFromTimestamp(long timestamp) {

        //DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(timestamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000);
        //System.out.println(formatter.format(calendar.getTime()));

        String ret = formatter.format(calendar.getTime());

        return ret;
    }

    public static String parseDateToFormat(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateToDMYFormat(String oldDate) {
        String inputPattern = "yyyy/MM/dd";
        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(oldDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateToFormatNew(String oldDate) {
        String inputPattern = "yyyy/MM/dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(oldDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateToFormatNewFromDDMMYYYY(String oldDate) {
        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(oldDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getDayOfWeekFromDate(String date) {
        String dayName = "";
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date myDate = inFormat.parse(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
            dayName = simpleDateFormat.format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayName;
    }

    public static String getCurrentDay(String date) {
        String dayName = "";
        SimpleDateFormat inFormat = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date myDate = inFormat.parse(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
            dayName = simpleDateFormat.format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayName;
    }

    public static long getTimestampFromDate(String strDate) {

        Date date = null;

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            date = (Date) formatter.parse(strDate);
//            System.out.println("Today is " +date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date.getTime();
    }

    public static String getDateTimeFromTimestampNew(long timestamp) {

        DateFormat formatter = new SimpleDateFormat("dd-MM-yy hh:mm aa");

        System.out.println(timestamp);

        Calendar calendar = Calendar.getInstance();

        if (timestamp < 1000000000000L) {
            calendar.setTimeInMillis(timestamp * 1000);
        } else {
            calendar.setTimeInMillis(timestamp);
        }

        System.out.println(formatter.format(calendar.getTime()));

        String ret = formatter.format(calendar.getTime());

        return ret;
    }

    public static String getDateTimeFromTimestamp(long timestamp) {

        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");

        System.out.println(timestamp);

        Calendar calendar = Calendar.getInstance();

        if (timestamp < 1000000000000L) {
            calendar.setTimeInMillis(timestamp * 1000);
        } else {
            calendar.setTimeInMillis(timestamp);
        }

        System.out.println(formatter.format(calendar.getTime()));

        String ret = formatter.format(calendar.getTime());

        return ret;
    }

    public static String covertTimeToText(long createdAt) {
        DateFormat userDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        DateFormat dateFormatNeeded = new SimpleDateFormat("MM/dd/yyyy HH:MM:SS");
        Date date = null;
        date = new Date(createdAt);
        String crdate1 = dateFormatNeeded.format(date);

        // Date Calculation
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        crdate1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);

        // get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        String currenttime = dateFormat.format(cal.getTime());

        Date CreatedAt = null;
        Date current = null;
        try {
            CreatedAt = dateFormat.parse(crdate1);
            current = dateFormat.parse(currenttime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = current.getTime() - CreatedAt.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String time = null;
        if (diffDays > 0) {
            if (diffDays == 1) {
                time = diffDays + " day ago ";
            } else {
                time = diffDays + " hours ago ";
            }
        } else {
            if (diffHours > 0) {
                if (diffHours == 1) {
                    time = diffHours + " hr ago";
                } else {
                    time = diffHours + " hrs ago";
                }
            } else {
                if (diffMinutes > 0) {
                    if (diffMinutes == 1) {
                        time = diffMinutes + " min ago";
                    } else {
                        time = diffMinutes + " mins ago";
                    }
                } else {
                    if (diffSeconds > 0) {
                        time = diffSeconds + " secs ago";
                    }
                }

            }

        }
        return time;
    }

    public static ArrayList<HashMap<String, String>> getNextWeekDays() {
//        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy");

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        HashMap<String, String> hashMap = new HashMap<>();

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat sdfDay = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat sdfDateSend = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        for (int i = 0; i < 7; i++) {
            hashMap = new HashMap<>();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            String date = sdfDate.format(calendar.getTime());
            String day = sdfDay.format(calendar.getTime());
            String dateSend = sdfDateSend.format(calendar.getTime());

            hashMap.put("date", date);
            hashMap.put("day", day);
            hashMap.put("dateSend", dateSend);

            list.add(hashMap);

        }

        return list;
    }

    public static String covertTimeToHours(String createdAt) {

        // Date Calculation
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        String currenttime = dateFormat.format(cal.getTime());

        Date CreatedAt = null;
        Date current = null;
        try {
            CreatedAt = dateFormat.parse(createdAt);
            current = dateFormat.parse(currenttime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = current.getTime() - CreatedAt.getTime();
        //long diffSeconds = diff / 1000;
        //long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String time = "0";
        if (diffDays > 0) {
            diffDays = diffDays * 24;
        }

        if (diffHours > 0) {
            if (diffHours == 1) {
                time = String.valueOf(diffHours + diffDays);
            } else {
                time = String.valueOf(diffHours + diffDays);
            }

        }
        return time;
    }

    public static String parseDate(String givenDateString) {
        if (givenDateString.equalsIgnoreCase("")) {
            return "";
        }

        long timeInMilliseconds = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        try {

            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String result = "0";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");

        String todayDate = formatter.format(new Date());
        Calendar calendar = Calendar.getInstance();

        long dayagolong = timeInMilliseconds;
        calendar.setTimeInMillis(dayagolong);
        String agoformater = formatter.format(calendar.getTime());

        Date CurrentDate = null;
        Date CreateDate = null;

        try {
            CurrentDate = formatter.parse(todayDate);
            CreateDate = formatter.parse(agoformater);

            long different = Math.abs(CurrentDate.getTime() - CreateDate.getTime());

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            if (elapsedDays > 0) {
                elapsedDays = elapsedDays * 24;
            }

            if (elapsedHours > 0) {
                result = String.valueOf(elapsedHours + elapsedDays);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.v("result-Data", result);

        return result;
    }

    public static boolean isEmailValid(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static String getmiliTimeStamp() {

        long LIMIT = 10000000000L;

        long t = Calendar.getInstance().getTimeInMillis();

        return String.valueOf(t).substring(0, 10);
    }

    public static String changeHrFormat(String time) {

        String input = time;
        //Format of the date defined in the input String
        DateFormat df = new SimpleDateFormat("hh:mm aa");
        //Desired format: 24 hour format: Change the pattern as per the need
        DateFormat outputformat = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        String output = null;
        try {
            //Converting the input String to Date
            date = df.parse(input);
            //Changing the format of date and storing it in String
            output = outputformat.format(date);
            //Displaying the date
            System.out.println(output);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return output;
    }

    public static String formatTime(int hour, int minutes) {

        String timeSet = "";
        if (hour > 12) {
            hour -= 12;
            timeSet = "PM";
        } else if (hour == 0) {
            hour += 12;
            timeSet = "AM";
        } else if (hour == 12) {
            timeSet = "PM";
        } else {
            timeSet = "AM";
        }

        String min = "";
        if (minutes < 10)
            min = "0" + minutes;
        else
            min = String.valueOf(minutes);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hour).append(':')
                .append(min).append(" ").append(timeSet).toString();

        return aTime;
    }

    public static Boolean getDifference2(String del, String lmp) {
        boolean diff = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse(del);
            Date now = sdf.parse(lmp);
            long days = getDateDiff(date, now, TimeUnit.DAYS);
            diff = days >= 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }

    public static String getDifference(String del, String lmp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = sdf.parse(del);
            Date now = sdf.parse(lmp);
            long days = getDateDiff(date, now, TimeUnit.DAYS);
            if (days < 7)
                return days + " Days";
            else
                return days / 7 + " Weeks";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    public static int getWeekDifference(String lmpDate, String delDate) {
        int week = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = sdf.parse(lmpDate);
            Date now = sdf.parse(delDate);
            long days = getDateDiff(date, now, TimeUnit.DAYS);
            if (days < 7)
                week = 0;
                //return hours + " Days";
            else
                week = (int) (days / 7);
            //return hours / 7 + " Weeks";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return week;
    }


    public static String getTimeDifference(String time1, String time2) {

        String timeDiff = "-1";
        if (!time1.isEmpty() && !time2.isEmpty()) {

            String[] morNight1 = time1.split(" ");
            String[] morNight2 = time2.split(" ");

            if (morNight1[1].equalsIgnoreCase("PM") && morNight2[1].equalsIgnoreCase("PM")) {
                if (morNight1[0].length() == 4) {
                    morNight1[0] = "0" + morNight1[0];
                }

                int b = Integer.parseInt(("" + morNight1[0]).substring(0, 2));

                if (b == 12) {
                    timeDiff = "1";
                } else {
                    int newTime1 = Integer.parseInt(morNight1[0].replaceAll(":", ""));
                    int newTime2 = Integer.parseInt(morNight2[0].replaceAll(":", ""));

                    if (newTime2 > newTime1) {
                        timeDiff = "1";
                    } else {
                        timeDiff = "-1";
                    }
                }

            } else if (morNight1[1].equalsIgnoreCase("AM") && morNight2[1].equalsIgnoreCase("AM")) {
                if (morNight1[0].length() == 4) {
                    morNight1[0] = "0" + morNight1[0];
                }

                int b = Integer.parseInt(("" + morNight1[0]).substring(0, 2));

                if (b == 12) {
                    timeDiff = "1";
                } else {
                    int newTime1 = Integer.parseInt(morNight1[0].replaceAll(":", ""));
                    int newTime2 = Integer.parseInt(morNight2[0].replaceAll(":", ""));

                    if (newTime2 > newTime1) {
                        timeDiff = "1";
                    } else {
                        timeDiff = "-1";
                    }
                }
            } else if (morNight1[1].equalsIgnoreCase("PM") && morNight2[1].equalsIgnoreCase("AM")) {
                timeDiff = "-1";
            } else {
                timeDiff = "1";
            }

        }


        return timeDiff;
    }

    public static String getDateAgo(String del) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(del);
            Date now = new Date(System.currentTimeMillis());
            long days = getDateDiff(date, now, TimeUnit.DAYS);
            return days + " Days";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    public static String getDateDifference(String dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dt);
            Date now = new Date(System.currentTimeMillis());
            long days = getDateDiff(date, now, TimeUnit.DAYS);
            long daysDiff = TimeUnit.MILLISECONDS.toDays(days);
            return String.valueOf(daysDiff);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static String getDateDiff(String dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dt);
            Date now = new Date(System.currentTimeMillis());
            long days = getDateDiff(date, now, TimeUnit.DAYS);
            return String.valueOf(days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static String getDateTimeDiff(String dt1, String dt2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        try {
            Date delDate = sdf.parse(dt1);
            Date feedDate = sdf.parse(dt2);
            //Date now = new Date(System.currentTimeMillis());
            long days = getDateDiff(delDate, feedDate, TimeUnit.MINUTES);

            Log.d("days", String.valueOf(days));

            //long daysDiff = TimeUnit.MILLISECONDS.toDays(days);
            return String.valueOf(days);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0";
    }


    public static String getWeightDaysDiff(String dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dt);
            Date now = new Date(System.currentTimeMillis());
            long days = getDateDiff(date, now, TimeUnit.DAYS);
            return String.valueOf(days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0";
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static int getAgeFromDOB(String dobDate) {

        int age = 0;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dobDate);

            try {

                if (dobDate != null) {

                    Date currDate = Calendar.getInstance().getTime();
                    // Log.d("Curr year === "+currDate.getYear()+" DOB Date == "+dobDate.getYear());
                    age = currDate.getYear() - date.getYear();
                    // Log.d("Calculated Age == "+age);
                }

            } catch (Exception e) {
                //Log.d(SyncStateContract.Constants.kApiExpTag, e.getMessage()+ "at Get Age From DOB mehtod.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010

        return age;

    }

    public static String saveScreenShotsAppCache(Activity context, View view) throws IOException {
        try {
            AppUtils.print("===saveScreenShotsAppCache");
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());

            //  Bitmap cropImg = Bitmap.createBitmap(bitmap, 0, 240, bitmap.getWidth(), bitmap.getHeight() - 250);

            view.setDrawingCacheEnabled(false);
            File cachePath = new File(context.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

            File imagePath = new File(context.getCacheDir(), "images");
            File newFile = new File(imagePath, "image.png");
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", newFile);
            AppUtils.print("===saveScreenShotsAppCache" + contentUri);

            /*if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                shareIntent.setDataAndType(contentUri, context.getContentResolver().getType(contentUri));
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                context.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
            }*/

            return getEncoded64ImageStringFromBitmap(bitmap);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ERROR";

    }

    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;

    }

    public static void showAlert(Activity activity, String message, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(R.string.yes, okListener)
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       /* Intent intent = new Intent(activity,ErrorActivity.class);
                        intent.putExtra("permissions_denied",true);
                        activity.startActivity(intent);
                        activity.finish();*/
                    }
                })
                .create()
                .show();
    }

    /*private void setAppLocale(Activity activity, String localeCode){
        Resources resources = activity.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }
*/

    @SuppressLint("NewApi")
    public static final void recreateActivityCompat(final Activity a) {
        //GetBackFragment.ClearStack();

        a.finish();
        a.startActivity(a.getIntent());
        a.overridePendingTransition(0, 0);

        /*if (VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            a.recreate();
        } else {
            final Intent intent = a.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            a.finishAffinity();
            a.overridePendingTransition(0, 0);
            a.startActivity(intent);
            a.overridePendingTransition(0, 0);
        }*/
    }

    public static String capitalizeFirstLetter(@NonNull String input) {

        try {
            if (!input.contains("  ")) {
                String[] words = input.toLowerCase().split(" ");
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];

                    if (i > 0 && word.length() > 0) {
                        builder.append(" ");
                    }

                    String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
                    builder.append(cap);
                }
                return builder.toString();
            } else {
                return input;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return input;
        }
    }

    public static String getMimeType(Context context, Uri uri) {
        /*String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;*/
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public static void animateGrowShrink(final View view, Animation.AnimationListener animationListener) {

        final Animation growAnimation = new ScaleAnimation(
                1f, 1.1f,
                1.0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        growAnimation.setDuration(300);
        view.startAnimation(growAnimation);

        growAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Animation shrinkAnimation = new ScaleAnimation(
                        1.1f, 1.0f,
                        1.1f, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                shrinkAnimation.setDuration(300);
                view.startAnimation(shrinkAnimation);

                shrinkAnimation.setAnimationListener(animationListener);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public static String getCurrencyFormat(int num) {
        try {
            String COUNTRY = "IN";
            String LANGUAGE = "en";
            return NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(num);
        } catch (Exception ex) {
            return "0";
        }
    }

    public static String getCurrencyFormat1(float num) {
        try {
            String COUNTRY = "IN";
            String LANGUAGE = "en";
            return NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(num);
        } catch (Exception ex) {
            return "0";
        }
    }

    public static String getCurrencyFormat(float num) {
        String COUNTRY = "IN";
        String LANGUAGE = "en";
        return NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(num);
    }

    public static String getCurrentDateInWeekMonthDayFormat() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current Date => " + c);
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d");
        return df.format(c);
    }

    public static void showImagePreview(Activity activity, String url) {

        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImagePreviewBinding imagePreviewBinding = ImagePreviewBinding.inflate(layoutInflater, null, false);
        imagePreviewBinding.setUri(url);
        new AlertDialog.Builder(activity).setView(imagePreviewBinding.getRoot()).setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public static void createCallData(String callDisconnected, String roomCode, String uid, String docId) {

        Map<String, Object> map = new HashMap<>();
        map.put("callStatus", callDisconnected);
        map.put("uid", uid);
        map.put("docId", docId);
        map.put("callStartedTimestamp", System.currentTimeMillis());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(AppUtils.VIDEO_CALLS)
                .document(roomCode)
                .set(map).addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.getLocalizedMessage()));
    }
}