package com.eza.androidabsent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    //0. inisialisasi variabel
    private static final String TAG = "Absent_Login";
    private String strEmail, strPassword;

    //1. inisialisasi komponen
    EditText edtEmail, edtPassword;
    TextView txtGreeting, txtTime;
    ImageView imgHeader;
    MaterialCardView cvTime;
    CardView cvLogin;
    AlertDialog dialog;
    LinearLayout layoutLogin;

    //2. inisialisasi firebase
    FirebaseAuth firebaseAuth;

    //#. inisialisasi lain-lain
    final Handler handler = new Handler();
    Timer timer;
    Intent intent;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        //#. lain-lain
        //extras = new Bundle();
        //intent = new Intent(this, ErrorActivity.class);
        timer = new Timer();

        //1. komponen
        txtGreeting = findViewById(R.id.txtGreeting);
        txtTime = findViewById(R.id.txtTime);
        imgHeader = findViewById(R.id.imgHeader);
        cvTime = findViewById(R.id.cvTime);
        layoutLogin = findViewById(R.id.layoutLogin);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        cvLogin = findViewById(R.id.cvLogin);

        txtGreeting.setVisibility(View.INVISIBLE);
        layoutLogin.setVisibility(View.INVISIBLE);
        cvTime.setVisibility(View.INVISIBLE);

        dialog = new SpotsDialog.Builder().setCancelable(false).setContext(this).build();
        dialog.show();

        initTimer();
        initView();
    }

    private void initTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LoginActivity.this.runOnUiThread(() -> {
                    setHeader();
                    //checkDevice();
                });
            }
        },0, 1000);
    }

    private void setHeader() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        Date dateNow = Calendar.getInstance().getTime();
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        String timeNow = time.format(new Date());
        String day = (String) DateFormat.format("EEEE", dateNow);
        String dateNumber = (String) DateFormat.format("d", dateNow);
        String monthNumber = (String) DateFormat.format("M", dateNow);
        String yearNumber = (String) DateFormat.format("yyyy", dateNow);

        if (day.equalsIgnoreCase("sunday")) {
            day = "Minggu";
        } else if (day.equalsIgnoreCase("monday")) {
            day = "Senin";
        } else if (day.equalsIgnoreCase("tuesday")) {
            day = "Selasa";
        } else if (day.equalsIgnoreCase("wednesday")) {
            day = "Rabu";
        } else if (day.equalsIgnoreCase("thursday")) {
            day = "Kamis";
        } else if (day.equalsIgnoreCase("friday")) {
            day = "Jumat";
        } else if (day.equalsIgnoreCase("saturday")) {
            day = "Sabtu";
        }

        int month = Integer.parseInt(monthNumber);
        String mounthNow = null;
        if (month == 1) {
            mounthNow = "Januari";
        } else if (month == 2) {
            mounthNow = "Februari";
        } else if (month == 3) {
            mounthNow = "Maret";
        } else if (month == 4) {
            mounthNow = "April";
        } else if (month == 5) {
            mounthNow = "Mei";
        } else if (month == 6) {
            mounthNow = "Juni";
        } else if (month == 7) {
            mounthNow = "Juli";
        } else if (month == 8) {
            mounthNow = "Agustus";
        } else if (month == 9) {
            mounthNow = "September";
        } else if (month == 10) {
            mounthNow = "Oktober";
        } else if (month == 11) {
            mounthNow = "November";
        } else if (month == 12) {
            mounthNow = "Desember";
        }

        if (timeOfDay >= 5 && timeOfDay < 10){
            txtGreeting.setText("Selamat Pagi ");
            imgHeader.setImageResource(R.drawable.img_default_half_morning);
        } else if (timeOfDay >= 10 && timeOfDay < 13) {
            txtGreeting.setText("Selamat Siang ");
            imgHeader.setImageResource(R.drawable.img_default_half_afternoon);
        } else if (timeOfDay >= 13 && timeOfDay < 18) {
            txtGreeting.setText("Selamat Sore ");
            imgHeader.setImageResource(R.drawable.img_default_half_without_sun);
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            if (timeOfDay >= 0 && timeOfDay < 5) {
                txtGreeting.setText("Selamat Malam ");
                txtGreeting.setTextColor(Color.WHITE);
                imgHeader.setImageResource(R.drawable.img_default_half_night);
            } else {
                txtGreeting.setText("Selamat Malam ");
                txtGreeting.setTextColor(Color.WHITE);
                imgHeader.setImageResource(R.drawable.img_default_half_night);
            }
        }

        String formatTime = day + ", "
                + dateNumber + " " + mounthNow + " " + yearNumber
                + "\n" + timeNow;
        txtTime.setText(formatTime);
    }

    private void initView() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtGreeting.setVisibility(View.VISIBLE);
                layoutLogin.setVisibility(View.VISIBLE);
                cvTime.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        }, 1000);
    }
}
