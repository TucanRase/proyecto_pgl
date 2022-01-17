package com.example.proyecto_tommy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * clase para crear la base de datos y sus respectivos métodos para crear,actualizar,añadir datos,comprobar
 * usuario,comprobar contraseña
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String nombreDB = "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override // TODO: 17/01/2022 Cambiar el login por email
    public void onCreate(SQLiteDatabase MiDB) {
        MiDB.execSQL("create Table usuarios(usuario TEXT primary key,contrasena TEXT)");
       // MiDB.execSQL("create Table componentes(ID int primary key, TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MiDB, int i, int i1) {
        MiDB.execSQL("drop Table if exists usuarios");
    }

    public boolean insertarDatos(String usuario, String contrasena) {
        SQLiteDatabase MiDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("usuario", usuario);
        contentValues.put("contrasena", contrasena);
        long resultado = MiDB.insert("usuarios", null, contentValues);
        if (resultado == -1)
            return false;
        else
            return true;
    }

    public Boolean comprobarUsuario(String usuario) {
        SQLiteDatabase MiDB = this.getWritableDatabase();
        Cursor cursor = MiDB.rawQuery("Select * from usuarios where usuario = ?", new String[]{usuario});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean comprobarContrasenaUsuario(String usuario, String contrasena) {
        SQLiteDatabase MiDB = this.getWritableDatabase();
        Cursor cursor = MiDB.rawQuery("Select * from usuarios where usuario = ? and contrasena = ?", new String[]{usuario, contrasena});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
