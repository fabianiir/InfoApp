package com.example.infosyst.infosyst;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.infosyst.infosyst.DataBase.LocalDB;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;


/*Esta clase  hereda los metodos del servicio de firebase para poder
controlar los eventos de las notificaciones.*/

public class MyService extends FirebaseMessagingService {
    public MyService() {
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
        super.onMessageReceived(remoteMessage);

        String titulo;
        String mensaje;
        String fecha;

        //Se crea formato y se obtiene Fecha del dispositivo
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = simpleDateFormat.format(new Date());

        //Se obtienen los valores del Json enviado en el DATA por medio de sus claves
        titulo = remoteMessage.getData().get("title");
        mensaje = remoteMessage.getData().get("body");

        //Se genera una notificacion si es que la aplicacion esta foreground
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setSmallIcon(R.drawable.ic_notifications)
                .build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(/*notification id*/0, notification);



        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            //si el mensaje contiene datos, se guarda en la base de datos local
            SaveNotification(titulo, mensaje, currentDateandTime);

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());


        }


    }

    // Metodo para guardar, el titulo, mensaje y la fecha de recibido de la notificacion en la base de datos
    public void SaveNotification(String titulo, String mensaje, String tiempo) {

        LocalDB localDB = new LocalDB(this, "InfoAppDB", null, 1);
        SQLiteDatabase db = localDB.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("titulo", titulo);
        registro.put("mensaje", mensaje);
        registro.put("FechaHora_recibido", tiempo);

        db.insert("notificacion", null, registro);
        db.close();


    }


}

