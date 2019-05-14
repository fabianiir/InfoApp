package com.example.infosyst.infosyst.Fragments;

import android.content.Context;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.infosyst.infosyst.ObjetoRes;
import com.example.infosyst.infosyst.R;
import com.example.infosyst.infosyst.Servicio;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SingUPFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SingUPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingUPFragment extends Fragment {
    String BaseUrl = "http://infopower.sytes.net:8060/InfoApp/webresources/glpservices/";
    String token, usuario, password, passwordConfirm;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SingUPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingUPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingUPFragment newInstance(String param1, String param2) {
        SingUPFragment fragment = new SingUPFragment();
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
        View v = inflater.inflate(R.layout.fragment_singup, container, false);
        final Button btnRegistrar = v.findViewById(R.id.btnRegistrar);
        final EditText edtUsuarioSUP = v.findViewById(R.id.edtUsuarioSUP);
        final EditText edtPasswordSUP = v.findViewById(R.id.edtPasswordSUP);
        final EditText edtConfirmPass = v.findViewById(R.id.edtPasswordConfirm);
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


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = edtUsuarioSUP.getText().toString();
                password = edtPasswordSUP.getText().toString();
                passwordConfirm = edtConfirmPass.getText().toString();


                if (password.equals(passwordConfirm)) {


                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BaseUrl)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    Servicio service = retrofit.create(Servicio.class);

                    Call call = service.SingUp(usuario, token, password);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            ObjetoRes resObj = (ObjetoRes) response.body();
                            if (resObj.geterror().equals("false")) {


                                Toast.makeText(getContext(), resObj.getMessage(), Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(getContext(), resObj.getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });
                } else
                    Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
