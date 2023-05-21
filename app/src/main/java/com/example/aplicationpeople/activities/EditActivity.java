package com.example.aplicationpeople.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.aplicationpeople.R;
import com.example.aplicationpeople.model.PersonInputDTO;
import com.example.aplicationpeople.model.PersonOutDTO;
import com.example.aplicationpeople.services.PeopleService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName, editTextDateBirth;
    private Button buttonUpdate;

    private static final String BASE_URL = "https://people-spring-api.herokuapp.com";
    private Retrofit retrofit;
    private PeopleService peopleService;

    private String name;
    private String dateBirth;
    private Long personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.app_name_edit);

        editTextName = findViewById(R.id.editTextName);
        editTextDateBirth = findViewById(R.id.editTextDateBirth);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            dateBirth = bundle.getString("dateBirth");
            personId = bundle.getLong("id");

            editTextName.setText(name);
            editTextDateBirth.setText(dateBirth);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        peopleService = retrofit.create(PeopleService.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonUpdate) {
            updatePerson();
        }
    }

    private void updatePerson() {
        String updatedName = editTextName.getText().toString();
        String updatedDateBirth = editTextDateBirth.getText().toString();

        Call<PersonOutDTO> call = peopleService.putPerson(personId, new PersonInputDTO(updatedName, updatedDateBirth));
        call.enqueue(new Callback<PersonOutDTO>() {
            @Override
            public void onResponse(Call<PersonOutDTO> call, Response<PersonOutDTO> response) {
                if (response.isSuccessful()) {
                    PersonOutDTO updatedPerson = response.body();
                    Toast.makeText(EditActivity.this, "Pessoa atualizada com sucesso!", Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putLong("id", updatedPerson.getId());
                    bundle.putString("name", updatedPerson.getName());
                    bundle.putString("dateBirth", updatedPerson.getDateBirth());

                    Intent detailsActivity = new Intent(EditActivity.this, DetailsActivity.class);
                    detailsActivity.putExtras(bundle);
                    startActivity(detailsActivity);

                    finish();
                } else {
                    Toast.makeText(EditActivity.this, "Falha ao atualizar pessoa.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PersonOutDTO> call, Throwable t) {
                Toast.makeText(EditActivity.this, "Erro na chamada de API.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
