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
        super(context, nombreDB, null, 1);
    }

    @Override // TODO: 17/01/2022 Cambiar el login por email
    public void onCreate(SQLiteDatabase MiDB) {
        MiDB.execSQL("create Table usuarios(usuario TEXT primary key,contrasena TEXT)");
        //MiDB.execSQL("create Table componentes(ID INTEGER primary key, TEXT)");
        MiDB.execSQL("create Table ordenadores(ID INTEGER primary key,cpuId int,ramId int,gpuId int,psuId int,almacenamientoId int)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MiDB, int i, int i1) {
        MiDB.execSQL("drop Table if exists usuarios");
        //MiDB.execSQL("drop Table if exists componentes");
        MiDB.execSQL("drop Table if exists ordenadores");
    }

    public boolean insertarDatosUsuario(String usuario, String contrasena) {
        SQLiteDatabase MiDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("usuario", usuario);
        contentValues.put("contrasena", contrasena);
        long resultado = MiDB.insert("usuarios", null, contentValues);
        //se puede usar el "si es menos 1 no devuelve false,si no devuelve true" esta linea es la simplificación
        return resultado != -1;
    }

    public boolean insertarDatosOrdenador(int cpuId, int ramId, int gpuId, int psuId, int almacenamientoId) {
        SQLiteDatabase MiDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", (Integer) null);
        contentValues.put("cpuId", cpuId);
        contentValues.put("ramId", ramId);
        contentValues.put("gpuId", gpuId);
        contentValues.put("psuId", psuId);
        contentValues.put("almacenamientoId", almacenamientoId);
        long resultado = MiDB.insert("ordenadores", null, contentValues);
        //se puede usar el "si es menos 1 no devuelve false,si no devuelve true" esta linea es la simplificación
        return resultado != -1;
    }

    public Boolean comprobarUsuario(String usuario) {
        SQLiteDatabase MiDB = this.getWritableDatabase();
        Cursor cursor = MiDB.rawQuery("Select * from usuarios where usuario = ?", new String[]{usuario});
        return cursor.getCount() > 0;
    }

    public Boolean comprobarContrasenaUsuario(String usuario, String contrasena) {
        SQLiteDatabase MiDB = this.getWritableDatabase();
        Cursor cursor = MiDB.rawQuery("Select * from usuarios where usuario = ? and contrasena = ?", new String[]{usuario, contrasena});
        return cursor.getCount() > 0;
    }
}
