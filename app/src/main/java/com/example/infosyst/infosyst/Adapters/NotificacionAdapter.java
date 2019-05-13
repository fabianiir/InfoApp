package com.example.infosyst.infosyst.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.infosyst.infosyst.Notificacion;
import com.example.infosyst.infosyst.R;

import java.util.List;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.NotificacionViewHolder>{

    List<Notificacion> notificacions;

    public NotificacionAdapter(List<Notificacion> notificacions){
        this.notificacions = notificacions;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public NotificacionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        NotificacionViewHolder pvh = new NotificacionViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacionViewHolder notificacionViewHolder, int i) {
        notificacionViewHolder.tvTitulo.setText(notificacions.get(i).titulo);
        notificacionViewHolder.tvMensaje.setText(notificacions.get(i).mensaje);
        notificacionViewHolder.tvFechaHora.setText(notificacions.get(i).fecha_hora);
    }

    @Override
    public int getItemCount() {
        return notificacions.size();
    }

    public static class NotificacionViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvTitulo;
        TextView tvMensaje;
        TextView tvFechaHora;

        NotificacionViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            tvTitulo = (TextView)itemView.findViewById(R.id.tvTitulo);
            tvMensaje = (TextView)itemView.findViewById(R.id.tvMensaje);
            tvFechaHora = (TextView) itemView.findViewById(R.id.tvFechaHora);
        }
    }


}
