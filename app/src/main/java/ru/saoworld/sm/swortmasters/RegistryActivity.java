package ru.saoworld.sm.swortmasters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import ru.saoworld.sm.swortmasters.dao.impl.user.impl.UserDAO;

public class RegistryActivity extends AppCompatActivity {

    EditText loginText;
    EditText emailText;
    EditText passwordText;
    EditText provePasswordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registry_activity);
        loginText = (EditText) findViewById(R.id.loginText);
        emailText = (EditText)findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        provePasswordText = (EditText) findViewById(R.id.passwordProveText);
    }
    public void registryRequest(View v) throws IOException {
        if((passwordText.getText().toString().equals(provePasswordText.getText().toString()))&&(passwordText.getText().length()>5))
        {
            UserDAO userDAO = new UserDAO();
            if(userDAO.userRegistry(loginText.getText().toString(),passwordText.getText().toString(),emailText.getText().toString()))
            {
                Toast.makeText(getApplicationContext(),"Поздравляю,Вы зарегестрированы",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistryActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            {
                Toast.makeText(getApplicationContext(),"Почему-то в регистрации отказано!",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Пароли не совпадают или длина пароля менее 6 символов!",Toast.LENGTH_SHORT).show();
        }
    }

}
