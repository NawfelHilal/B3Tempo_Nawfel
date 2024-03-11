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
import android.view.View;

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

    TempoDateAdapter tempoDateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init recycler view
        binding.tempoHistoryRv.setHasFixedSize(true);
        binding.tempoHistoryRv.setLayoutManager(new LinearLayoutManager(this));
        tempoDateAdapter = new TempoDateAdapter(tempoDates, this);
        binding.tempoHistoryRv.setAdapter(tempoDateAdapter);

        updateTempoHistory();
    }

    private void updateTempoHistory() {
        Call<TempoHistory> call = edfApi.getTempoHistory("2023","2024");
        binding.tempoHistoryPb.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<TempoHistory>() {

            @Override
            public void onResponse(@NonNull Call<TempoHistory> call, @NonNull Response<TempoHistory> response) {
                tempoDates.clear();
                TempoHistory tempoHistory = response.body();
                if (response.code() == HttpURLConnection.HTTP_OK && tempoHistory != null) {
                    tempoDates.addAll(response.body().getDates());
                    Log.d(LOG_TAG,"nb element = " + tempoDates.size());
                    tempoDateAdapter.notifyDataSetChanged();

                } else {
                    Log.w(LOG_TAG, "call to getTempoHistory() failed with error code " + response.code());
                }
                binding.tempoHistoryPb.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<TempoHistory> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, "call to getTempoHistory() failed ");
                binding.tempoHistoryPb.setVisibility(View.GONE);
            }
        });
    }
}