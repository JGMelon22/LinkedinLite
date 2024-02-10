package com.example.linkedinlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        Toast.makeText(ErrorActivity.this, "Error de conexão. É necessário estar conectado a internet para usar este aplicativo!", Toast.LENGTH_SHORT).show();
    }

    public void buttonRetornarOnClick(View view) {
        isUserConnected();
    }

    public void isUserConnected() {
        // Instancia o network manager e julga se está offline ou não (evitando a web view offline)
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            Intent mainIntent = new Intent(ErrorActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        } else {
            Toast.makeText(ErrorActivity.this, "Error de conexão. É necessário estar conectado a internet para usar este aplicativo!", Toast.LENGTH_SHORT).show();
        }
    }
}