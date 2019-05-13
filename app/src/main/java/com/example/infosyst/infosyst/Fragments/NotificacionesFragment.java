package com.example.infosyst.infosyst.Fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infosyst.infosyst.Adapters.NotificacionAdapter;
import com.example.infosyst.infosyst.Notificacion;
import com.example.infosyst.infosyst.DataBase.LocalDB;
import com.example.infosyst.infosyst.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificacionesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacionesFragment extends Fragment {


    SwipeRefreshLayout swipeRefreshLayout;
    NotificacionAdapter adapter;
    List<Notificacion> notificacions;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NotificacionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificacionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificacionesFragment newInstance(String param1, String param2) {
        NotificacionesFragment fragment = new NotificacionesFragment();
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


        // Se genera la vista del fragment con un layout y se asignan los controles del layout a variables
        View view = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ObtenerNotificaciones();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });





        /*Se guarda en una lista los valores obtenidos de la base de datos y
        se asignan al adapter para su despliegue*/

        ObtenerNotificaciones();

    /*    Este objeto permite que los cardview(los cuadros donde se muestran las notificaciones)
        sean deslizables y a su vez de cachar los eventos */
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                // Cuando se desliza por completo el cardview se obtiene la posicion
                // del cardview deslizado y se elimina de la base de datos.

                int pos = viewHolder.getAdapterPosition();
                LocalDB localDB = new LocalDB(getContext(), "InfoAppDB", null, 1);
                final SQLiteDatabase sqLiteDatabase = localDB.getWritableDatabase();
                int id = notificacions.get(pos).indice;
                notificacions.remove(pos);

                //Se actualiza el adapater, notificando la posicion del cardview eliminado
                adapter.notifyItemRemoved(pos);
                sqLiteDatabase.delete("notificacion", "id=" + id, null);


            }
        };

        //Se asigna el recyclerview al Item
        // Touchhelper para realizar al animacion de deslizar
        ItemTouchHelper myHelper = new ItemTouchHelper(simpleCallback);
        myHelper.attachToRecyclerView(recyclerView);


        return view;
    }

    // Metodo para obtener las notificaciones almacenadas en la base de datos local

    private void ObtenerNotificaciones() {
        LocalDB localDB = new LocalDB(getContext(), "InfoAppDB", null, 1);
        final SQLiteDatabase sqLiteDatabase = localDB.getWritableDatabase();

        //consulta todos los registros de la tabla de notificaciones
        // y las guarda en una lista que retornada
        Cursor consulta = sqLiteDatabase.rawQuery("SELECT * FROM notificacion", null);
        notificacions = new ArrayList<>();
        for (consulta.moveToFirst(); !consulta.isAfterLast(); consulta.moveToNext()) {


            notificacions.add(new Notificacion(consulta.getInt(0), consulta.getString(1), consulta.getString(2), consulta.getString(3)));

        }
        adapter = new NotificacionAdapter(notificacions);
        recyclerView.setAdapter(adapter);


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
