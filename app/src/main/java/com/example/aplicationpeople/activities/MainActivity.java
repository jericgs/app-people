package com.example.aplicationpeople.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.aplicationpeople.R;
import com.example.aplicationpeople.services.PeopleService;
import com.example.aplicationpeople.model.PersonOutDTO;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.aplicationpeople.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String BASE_URL = "https://people-spring-api.herokuapp.com";

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private PeopleService peopleService;

    private ListView listView;

    private List<String> namesPeople;
    private List<PersonOutDTO> peopleList; // Adicionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        binding.fab.setOnClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peopleService = retrofit.create(PeopleService.class);

        // Inicializar a lista de pessoas
        peopleList = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }

    private void refreshListView() {
        Call<List<PersonOutDTO>> call = peopleService.getPeople();
        call.enqueue(new Callback<List<PersonOutDTO>>() {
            @Override
            public void onResponse(Call<List<PersonOutDTO>> call, Response<List<PersonOutDTO>> response) {
                if (response.isSuccessful()) {
                    peopleList = response.body(); // Atualizado
                    namesPeople = new ArrayList<>();
                    if (peopleList != null) {
                        namesPeople = peopleList.stream().map(PersonOutDTO::getName).collect(Collectors.toList());
                        listView.setAdapter(new ArrayAdapter<>(MainActivity.this, R.layout.list_fragment_view, namesPeople));
                    }
                } else {
                    Log.e("API Error", "Erro na resposta da API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<PersonOutDTO>> call, Throwable t) {
                // Lidar com a falha na requisição
                Log.e("API Error", "Falha na requisição: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String personName = namesPeople.get(position);

        PersonOutDTO person = peopleList.stream()
                .filter(p -> p.getName().equals(personName))
                .findFirst()
                .orElse(null);

        if (person != null) {
            Bundle bundle = new Bundle();
            bundle.putLong("id", person.getId());
            bundle.putString("name", person.getName());
            bundle.putString("dateBirth", person.getDateBirth());

            Intent detailsActivity = new Intent(this, DetailsActivity.class);
            detailsActivity.putExtras(bundle);
            startActivity(detailsActivity);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            Intent addActivity = new Intent(this, AddActivity.class);
            startActivity(addActivity);
        }
    }
}
