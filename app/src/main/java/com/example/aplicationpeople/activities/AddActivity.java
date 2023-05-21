package com.example.aplicationpeople.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicationpeople.R;
import com.example.aplicationpeople.model.AddressInputDTO;
import com.example.aplicationpeople.model.AddressOutDTO;
import com.example.aplicationpeople.model.PersonBaseDTO;
import com.example.aplicationpeople.model.PersonInputDTO;
import com.example.aplicationpeople.model.PersonOutDTO;
import com.example.aplicationpeople.services.AddressesService;
import com.example.aplicationpeople.services.PeopleService;

import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName, editTextDateBirth;
    private EditText editTextStreet, editTextNumber, editTextCity, editTextZipCode;

    private Button buttonRegister;

    private static final String BASE_URL = "https://people-spring-api.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.app_name_create);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDateBirth = (EditText) findViewById(R.id.editTextDateBirth);
        editTextStreet = (EditText) findViewById(R.id.editTextStreet);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        editTextCity = (EditText) findViewById(R.id.editTextCity);
        editTextZipCode = (EditText) findViewById(R.id.editTextZipCode);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonRegister) {
            String name = editTextName.getText().toString();
            String dateOfBirth = editTextDateBirth.getText().toString();
            String street = editTextStreet.getText().toString();
            String number = editTextNumber.getText().toString();
            String city = editTextCity.getText().toString();
            String zipCode = editTextZipCode.getText().toString();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PeopleService peopleService = retrofit.create(PeopleService.class);
            AddressesService addressesService = retrofit.create(AddressesService.class);

            PersonInputDTO personInputDTO = new PersonInputDTO(name, dateOfBirth);
            PersonBaseDTO personBaseDTO = new PersonBaseDTO();

            final Call<AddressOutDTO>[] addressCall = new Call[1];

            Call<PersonOutDTO> personCall = peopleService.postPerson(personInputDTO);
            personCall.enqueue(new Callback<PersonOutDTO>() {
                @Override
                public void onResponse(Call<PersonOutDTO> call, Response<PersonOutDTO> response) {
                    handlePersonResponse(response, personBaseDTO, street, zipCode, number, city, addressesService, addressCall);
                }

                @Override
                public void onFailure(Call<PersonOutDTO> call, Throwable t) {
                    handlePersonFailure(t);
                }
            });
        }
    }

    private void handlePersonResponse(Response<PersonOutDTO> response, PersonBaseDTO personBaseDTO, String street, String zipCode, String number, String city, AddressesService addressesService, Call<AddressOutDTO>[] addressCall) {
        if (response.isSuccessful()) {
            PersonOutDTO personOutDTO = response.body();
            personBaseDTO.setId(personOutDTO.getId());
            AddressInputDTO addressInputDTO = new AddressInputDTO(personBaseDTO, street, zipCode, number, city, true);

            addressCall[0] = addressesService.postAddress(addressInputDTO);
            addressCall[0].enqueue(new Callback<AddressOutDTO>() {
                @Override
                public void onResponse(Call<AddressOutDTO> call, Response<AddressOutDTO> response) {
                    handleAddressResponse(response);
                }

                @Override
                public void onFailure(Call<AddressOutDTO> call, Throwable t) {
                    handleAddressFailure(t);
                }
            });
        } else {
            // A requisição retornou um código de erro, trate o erro aqui
            // Você pode obter mais informações sobre o erro usando response.errorBody()
        }
    }

    private void handlePersonFailure(Throwable t) {
        // Lógica para lidar com falhas na requisição de pessoa (person)
        // Trate a falha de conexão ou outros erros aqui
        Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e("API Error", "Falha na requisição person: " + t.getMessage());
    }

    private void handleAddressResponse(Response<AddressOutDTO> response) {
        if (response.isSuccessful()) {
            AddressOutDTO addressOutDTO = response.body();
            Toast.makeText(getApplicationContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // A requisição retornou um código de erro, trate o erro aqui
            // Pode obter mais informações sobre o erro usando response.errorBody()
        }
    }

    private void handleAddressFailure(Throwable t) {
        // Lógica para lidar com falhas na requisição de endereço (address)
        // Trate a falha de conexão ou outros erros aqui
        Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e("API Error", "Falha na requisição endereço: " + t.getMessage());
    }

}
