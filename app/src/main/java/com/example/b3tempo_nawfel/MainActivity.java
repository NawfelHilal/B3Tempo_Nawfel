package com.example.b3tempo_nawfel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.b3tempo_nawfel.databinding.ActivityMainBinding;

import java.net.HttpURLConnection;
import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;
    public static IEdfApi edfApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Init view
        binding.button.setOnClickListener(this);


        Retrofit retrofitClient = ApiClient.get();
        if (retrofitClient != null) {
            edfApi = retrofitClient.create(IEdfApi.class);
        } else {
            Log.e(LOG_TAG, "unable to initialize Retrofit client");
            finish();
        }

        updateNbTempoDaysLeft();
        updateTempoDaysColor();

        Call<TempoDaysColor> call2 = edfApi.getDaysColor("2024-03-05", IEdfApi.EDF_TEMPO_API_ALERT_TYPE);

        call2.enqueue(new Callback<TempoDaysColor>() {

            @Override
            public void onResponse(@NonNull Call<TempoDaysColor> call, @NonNull Response<TempoDaysColor> response) {
                TempoDaysColor tempoDaysColor = response.body();
                if (response.code() == HttpURLConnection.HTTP_OK && tempoDaysColor != null) {
                    Log.d(LOG_TAG,"Today color = " + tempoDaysColor.getCouleurJourJ());
                    Log.d(LOG_TAG,"Tomorrow color = " + tempoDaysColor.getCouleurJourJ1());
                } else {
                    Log.w(LOG_TAG, "call to getTempoDaysColor() failed with error code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TempoDaysColor> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, "call to getTempoDaysColor() failed ");
            }
        });
    }


    private void updateNbTempoDaysLeft() {
        Call<TempoDaysLeft> call = edfApi.getTempoDaysLeft(IEdfApi.EDF_TEMPO_API_ALERT_TYPE);

        call.enqueue(new Callback<TempoDaysLeft>() {
            @Override
            public void onResponse(@NonNull Call<TempoDaysLeft> call, @NonNull Response<TempoDaysLeft> response) {
                TempoDaysLeft tempoDaysLeft = response.body();
                if (response.code() == HttpURLConnection.HTTP_OK && tempoDaysLeft != null) {
                    Log.d(LOG_TAG, "nb red days = " + tempoDaysLeft.getParamNbJRouge());
                    Log.d(LOG_TAG, "nb white days = " + tempoDaysLeft.getParamNbJBlanc());
                    Log.d(LOG_TAG, "nb blue days = " + tempoDaysLeft.getParamNbJBleu());
                    binding.blueDaysTv.setText(String.valueOf(tempoDaysLeft.getParamNbJBleu()));
                    binding.whiteDaysTv.setText(String.valueOf(tempoDaysLeft.getParamNbJBlanc()));
                    binding.redDaysTv.setText(String.valueOf(tempoDaysLeft.getParamNbJRouge()));
                } else {
                    Log.w(LOG_TAG, "call to getTempoDaysLeft() failed with error code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TempoDaysLeft> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, "call to getTempoDaysLeft () failed ");
            }
        });

    }

    private void updateTempoDaysColor() {
        Call<TempoDaysColor> call = edfApi.getDaysColor(Tools.getNowDate("yyyy-MM-dd"), IEdfApi.EDF_TEMPO_API_ALERT_TYPE);


        call.enqueue(new Callback<TempoDaysColor>() {

            @Override
            public void onResponse(@NonNull Call<TempoDaysColor> call, @NonNull Response<TempoDaysColor> response) {
                TempoDaysColor tempoDaysColor = response.body();
                if (response.code() == HttpURLConnection.HTTP_OK && tempoDaysColor != null) {
                    Log.d(LOG_TAG,"Today color = " + tempoDaysColor.getCouleurJourJ());
                    Log.d(LOG_TAG,"Tomorrow color = " + tempoDaysColor.getCouleurJourJ1());
                    binding.dayColorView.setDayCircleColor(tempoDaysColor.getCouleurJourJ());
                    binding.dayColorView2.setDayCircleColor(tempoDaysColor.getCouleurJourJ1());
                } else {
                    Log.w(LOG_TAG, "call to getTempoDaysColor() failed with error code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TempoDaysColor> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, "call to getTempoDaysColor() failed ");
            }
        });
    }

    /*
        public void showHistory(View view) {
        Intent intent = new Intent();
        intent.setClass(this, HistoryActivity.class);
        startActivity(intent);
    }*/

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "onClick");
        if (v.getId() == R.id.button) {
            Intent intent = new Intent();
            intent.setClass(this, HistoryActivity.class);
            startActivity(intent);
        } else {
            Log.w(LOG_TAG, "unhandled onClick event");
        }
    }
}