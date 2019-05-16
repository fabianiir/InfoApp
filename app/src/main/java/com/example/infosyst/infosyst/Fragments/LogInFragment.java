package com.example.infosyst.infosyst.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infosyst.infosyst.MainActivity;
import com.example.infosyst.infosyst.ObjetoRes;
import com.example.infosyst.infosyst.R;
import com.example.infosyst.infosyst.Servicio;
import com.example.infosyst.infosyst.UsuarioFragment;
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

import static com.example.infosyst.infosyst.MainActivity.guardarValor;


public class LogInFragment extends Fragment {
    String BaseUrl = "http://infopower.sytes.net:8060/InfoApp/webresources/glpservices/";
    String token;
    String usuario;
    String pass;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public LogInFragment() {
        // Required empty public constructor
    }


    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        final EditText editUsuario = v.findViewById(R.id.edtUsuario);
        final EditText editPass = v.findViewById(R.id.edtPassword);
        final Button btnLogin = v.findViewById(R.id.btnLogin);
        final ImageButton btnSinUP = v.findViewById(R.id.btnSingUp);

        FirebaseApp.initializeApp(getContext());
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        // Log and toast

                        Log.d("firebasetoken", token);


                    }
                });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = editUsuario.getText().toString().trim();
                pass = editPass.getText().toString();

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BaseUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                Servicio service = retrofit.create(Servicio.class);
                Call call = service.login(usuario, token, pass);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        ObjetoRes resObj = (ObjetoRes) response.body();
                        if (response.isSuccessful()) {
                            if (resObj.geterror().equals("false")) {

                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, new UsuarioFragment());
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                                guardarValor(getContext(),"user",usuario);
                                guardarValor(getContext(),"token",token);

                            } else


                                Toast.makeText(getContext(), "Usuario o Contraseña incorrecta. Intente de nuevo", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "El servidor no responde", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(getContext(), "Verifique su conexión a internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        btnSinUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new SingUPFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        // Inflate the layout for this fragment
        return v;
    }




}
