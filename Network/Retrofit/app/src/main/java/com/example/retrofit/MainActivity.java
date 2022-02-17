package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit.api.RemoteService;
import com.example.retrofit.api.RetrofitClient;
import com.example.retrofit.model.Currency;
import com.example.retrofit.model.User;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
* Link api : http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1
*
* */
public class MainActivity extends AppCompatActivity {

    TextView tvTerm;
    TextView tvPrivacy;
    TextView tvSource;
    TextView tvQuote;
    Button btCallApi;
    Button btPostApi;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTerm = findViewById(R.id.tvTerm);
        tvPrivacy = findViewById(R.id.tvPrivacy);
        tvSource = findViewById(R.id.tvSource);
        tvQuote = findViewById(R.id.tvQuote);
        btCallApi = findViewById(R.id.btCallApi);
        btPostApi = findViewById(R.id.btPostApi);
        tvResult = findViewById(R.id.tvResult);

        btCallApi.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                clickCallApi();
            }
            
        });

        btPostApi.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                clickPostApi();
            }
        });
    }

    private void clickCallApi() {

        Map<String, String> options = new HashMap<>();
        options.put("access_key","843d4d34ae72b3882e3db642c51e28e6");
        options.put("currencies","VND");
        options.put("source","USD");
        options.put("format","1");

        //ApiService.apiService.convertUsdtoVnd("843d4d34ae72b3882e3db642c51e28e6","VND","USD",1)
        RetrofitClient.getInstance().getRetrofit().create(RemoteService.class).convertUsdtoVnd(options)
                .enqueue(new Callback<Currency>() {
                    @Override
                    public void onResponse(Call<Currency> call, Response<Currency> response) {
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Currency currency = response.body();
                        if(currency != null && currency.isSuccess()){
                            tvTerm.setText(currency.getTerms());
                            tvPrivacy.setText(currency.getPrivacy());
                            tvSource.setText(currency.getSource());
                            tvQuote.setText(String.valueOf(currency.getQuotes().getUsdVnd()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Currency> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clickPostApi(){
        User user = new User(1,100,"Title","Body");
        RetrofitClient.getInstance().getRetrofit().create(RemoteService.class).createUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if(user != null){
                    tvResult.setText(user.toString());
                }
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}