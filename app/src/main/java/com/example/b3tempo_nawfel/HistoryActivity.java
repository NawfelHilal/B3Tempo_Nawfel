package com.example.b3tempo_nawfel;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import static com.example.b3tempo_nawfel.MainActivity.edfApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.example.b3tempo_nawfel.databinding.ActivityHistoryBinding;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    List<TempoDate> tempoDates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init recycler view
        binding.tempoHistoryRv.setHasFixedSize(true);
        binding.tempoHistoryRv.setLayoutManager(new LinearLayoutManager(this));
        binding.tempoHistoryRv.setAdapter(new TempoDateAdapter(tempoDates));

        updateTempoHistory();
    }

    private void updateTempoHistory() {
        Call<TempoHistory> call = edfApi.getTempoHistory("2023","2024");

        call.enqueue(new Callback<TempoHistory>() {

            @Override
            public void onResponse(@NonNull Call<TempoHistory> call, @NonNull Response<TempoHistory> response) {
                tempoDates.clear();
                TempoHistory tempoHistory = response.body();
                if (response.code() == HttpURLConnection.HTTP_OK && tempoHistory != null) {
                    tempoDates.addAll(response.body().getDates());
                    Log.d(LOG_TAG,"nb element = " + tempoDates.size());
                } else {
                    Log.w(LOG_TAG, "call to getTempoHistory() failed with error code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TempoHistory> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, "call to getTempoHistory() failed ");
            }
        });
    }
}