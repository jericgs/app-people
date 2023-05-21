package com.example.aplicationpeople.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.aplicationpeople.R;
import com.example.aplicationpeople.model.AddressOutDTO;
import com.example.aplicationpeople.services.AddressesService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewDetails;

    private static final String BASE_URL = "https://people-spring-api.herokuapp.com";

    private AddressesService addressesService;

    private Button buttonEdit, buttonDelete;
    private Long addressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.app_name_details);

        textViewDetails = findViewById(R.id.textViewDetails);

        buttonEdit = findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(this);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        addressesService = retrofit.create(AddressesService.class);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("name");
            String dateBirth = bundle.getString("dateBirth");
            Long personId = bundle.getLong("id");

            String[] detailsText = {"Name: " + name + "\nDate of Birth: " + dateBirth};
            textViewDetails.setText(detailsText[0]);

            Call<List<AddressOutDTO>> call = addressesService.getAddressMain(personId);
            call.enqueue(new Callback<List<AddressOutDTO>>() {
                @Override
                public void onResponse(Call<List<AddressOutDTO>> call, Response<List<AddressOutDTO>> response) {
                    if (response.isSuccessful()) {
                        List<AddressOutDTO> addresses = response.body();
                        if (addresses != null && !addresses.isEmpty()) {

                            StringBuilder addressesText = new StringBuilder("\n\nEndereço:\n");
                            for (AddressOutDTO address : addresses) {
                                addressesText.append("Logradouro: ").append(address.getStreet())
                                        .append("\nNúmero: ").append(address.getNumber())
                                        .append("\nCidade: ").append(address.getCity())
                                        .append("\nCep: ").append(address.getZipCode())
                                        .append("\n\n");

                                addressId = address.getId();
                            }
                            detailsText[0] += addressesText.toString();
                            textViewDetails.setText(detailsText[0]);
                        }
                    } else {
                        // Tratar erro na resposta da API
                    }
                }

                @Override
                public void onFailure(Call<List<AddressOutDTO>> call, Throwable t) {
                    // Tratar falha na chamada
                    Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonEdit) {

            Bundle bundle = getIntent().getExtras();
            Intent editActivity = new Intent(this, EditActivity.class);
            editActivity.putExtras(bundle);
            startActivity(editActivity);
            finish();

        }

        if (v.getId() == R.id.buttonDelete) {
            deleteAddress(addressId);
        }

    }

    private void deleteAddress(Long addressId) {
        Call<Void> call = addressesService.deleteAddress(addressId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Endereço excluído com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Erro: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
