package com.example.infosyst.infosyst.DataBase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDB extends SQLiteOpenHelper {

    final String NOTIFICATIONS_TABLE = "CREATE TABLE notificacion(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "titulo TEXT, mensaje TEXT, FechaHora_recibido TEXT)";


    public LocalDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NOTIFICATIONS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notification");
        onCreate(db);
    }
}
