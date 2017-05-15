package ru.saoworld.sm.swortmasters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import ru.saoworld.sm.swortmasters.bin.GameMainWindow;
import ru.saoworld.sm.swortmasters.dao.AbstractDAOController;
import ru.saoworld.sm.swortmasters.dao.impl.user.impl.UserDAO;
import ru.saoworld.sm.swortmasters.exception.DataException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedpreferences;
    String MyPREFERENCES = "Sword Masters";
    Button loginRequest;
    EditText emailText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        loginRequest = (Button) findViewById(R.id.loginReqeust);
        loginRequest.setOnClickListener(this);
    }

    private String getUserEmail() {
        emailText = (EditText) findViewById(R.id.textEmail);
        String email = emailText.getText().toString();
        return email;
    }

    private String getPasswordText() {
        passwordText = (EditText) findViewById(R.id.textPassword);
        String password = passwordText.getText().toString();
        return password;
    }

    public void registryRequest(View v) {
        Intent intent = new Intent(LoginActivity.this, RegistryActivity.class);
        startActivity(intent);

    }

    private void refresh() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String email = getUserEmail();
        String password = getPasswordText();
        UserDAO daoController = new UserDAO();
        try {
            int id = daoController.checkOnRightLoginData(email, password);
            if (id != 0) {
                editor.putInt("Id", id);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Все отлично,поздравляю!", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(LoginActivity.this, GameMainWindow.class);
                startActivity(intent);
            } else
                Toast.makeText(getApplicationContext(), "Некорректный ввод данных!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Что то пошло не так", Toast.LENGTH_SHORT).show();
        }

    }
}
