package com.example.infosyst.infosyst;

import android.content.ClipData;
import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infosyst.infosyst.DataBase.LocalDB;
import com.example.infosyst.infosyst.Fragments.InfoEmpleadosFragment;
import com.example.infosyst.infosyst.Fragments.NotificacionesFragment;
import com.example.infosyst.infosyst.Fragments.RegistrarFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Administrador de Fragments, se despliega fragment de Notificaciones
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NotificacionesFragment fragment = new NotificacionesFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();



        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);



        //Opciones del menu de navegación
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (id) {
                    case R.id.navigation_profile:
                        //se reemplaza fragment en cada uno de los casos
                        RegistrarFragment fragmentRegistrar = new RegistrarFragment();
                        fragmentTransaction.replace(R.id.fragment_container, fragmentRegistrar);
                        fragmentTransaction.commit();
                        break;
                    case R.id.navigation_notifications:


                        NotificacionesFragment fragment = new NotificacionesFragment();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.navigation_infoempleados:

                        InfoEmpleadosFragment fragmentIWeb = new InfoEmpleadosFragment();
                        fragmentTransaction.replace(R.id.fragment_container, fragmentIWeb);
                        fragmentTransaction.commit();
                        break;
                    default:
                        return true;

                }


                return false;
            }
        });








    }


}
