package com.example.demoretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.demoretrofit.model.Login;
import com.example.demoretrofit.model.Products;
import com.example.demoretrofit.model.User;
import com.example.demoretrofit.service.UserClient;
//import com.squareup.okhttp.Authenticator;
//import com.squareup.okhttp.OkHttpClient;


import java.util.List;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String TOKEN = "yJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im5pbHNvbkBlbWFpbC5jb20iLCJwYXNzd29yZCI6Im5pbHNvbiIsImlhdCI6MTYwMzU1ODczMywiZXhwIjoxNjAzNTYyMzMzfQ.ZI_PF486BFmqwY9_nCJgWVtPE1kyGPllOnMQbZ9vulQ";

    /*ConnectionSpec spec = new
            ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_3)
            .cipherSuites(
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
            .build();

    OkHttpClient client = new OkHttpClient.Builder()
            .connectionSpecs(Collections.singletonList(spec))
            .build();*/

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://10.118.103.54:8001/")
            .client(createTrustingOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        actions();
    }

    private void setupView(){
        button = (Button) findViewById(R.id.btnRetrofit);

    }

    private static OkHttpClient createTrustingOkHttpClient() {
        Authenticator authenticator = new TokenAuthenticator();

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .authenticator(authenticator);


        return client.build();
    }

    private void login() {
        Login login = new Login("nilson@email.com","nilson");



        Call<User> call = userClient.login(login);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "token -> " + response.body().getToken());
                } else {
                    Log.d("TAG", "login not correct -> :(" + response.raw());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("TAG", "error -> " + t.toString());
            }
        });
    }

    private void getProducts() {
        Log.d("TAG", "== getProducts() ==");

        Call<List<Products>> call = userClient.getSecret("Bearer " + TOKEN );
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "Product -> " + response.body().get(0).getName());
                } else {
                    Log.d("TAG", "login not correct -> :(" + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Log.d("TAG", "error -> " + t.getMessage());
            }
        });
    }

    private void actions() {

        button.setOnClickListener(view -> {
            Log.d("TAG","buttonPressed");
            getProducts();
        });

    }
}