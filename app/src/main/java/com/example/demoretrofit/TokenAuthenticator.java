package com.example.demoretrofit;

import android.os.AsyncTask;
import android.util.Log;

import com.example.demoretrofit.model.Login;
import com.example.demoretrofit.model.User;
import com.example.demoretrofit.service.UserClient;
//import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.ExecutionException;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenAuthenticator implements Authenticator {

    private String newAccessToken = "";
    static String ACCESS_TOKEN = "";

    //@Override
    /*public Request authenticate(Proxy proxy, Response response) throws IOException {
        // Refresh your access_token using a synchronous api request
        newAccessToken = login();

        // Add new header to rejected request and retry it
        return response.request().newBuilder()
                .header("Authorization", newAccessToken)
                .build();
    }*/

    //@Override
    /*public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
        // Null indicates no attempt to authenticate.
        return null;
    }*/

    private String login() {
        Log.d("TAG", "== login()");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.118.103.54:8001/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);
        Login login = new Login("nilson@email.com","nilson");


        Call<User> call = userClient.login(login);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "token -> " + response.body().getToken());
                    ACCESS_TOKEN = response.body().getToken();
                } else {
                    Log.d("TAG", "login not correct -> :(" + response.raw());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("TAG", "error -> " + t.toString());
            }
        });

        Log.d("TAG", "Token -> " + ACCESS_TOKEN);
        return ACCESS_TOKEN;
    }


    @Override
    public okhttp3.Request authenticate( Route route, okhttp3.Response responseAuth) throws IOException {
        // Refresh your access_token using a synchronous api request
        Log.d("TAG", "== authenticate() ==");
        try {
            new Peticion().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("TAG", "== ACCESS_TOKEN ==");
        return responseAuth.request().newBuilder()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .build();

    }

    public static class Peticion extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://10.118.103.54:8001/")
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();
            UserClient userClient = retrofit.create(UserClient.class);
            Login login = new Login("nilson@email.com", "nilson");


            Call<User> call = userClient.login(login);

            try {
                User user = call.execute().body();
                Log.e("Respuesta:   ", user.getToken());
                ACCESS_TOKEN = user.getToken();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}