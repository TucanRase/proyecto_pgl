package com.example.proyecto_tommy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * clase para crear la base de datos y sus respectivos métodos para crear,actualizar,añadir datos,comprobar
 * usuario,comprobar contraseña
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String nombreDB = "pcApp.db";

    public DBHelper(Context context) {
        super(context, nombreDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MiDB) {
        MiDB.execSQL("create Table usuarios(usuario TEXT primary key,contrasena TEXT)");
        MiDB.execSQL("create Table componentes(ID INTEGER primary key,imagen int,nombre text,tipo text,precio real,caracteristicas text)");
        MiDB.execSQL("create Table ordenadores(ID INTEGER primary key,cpuId int,ramId int,gpuId int,psuId int,almacenamientoId int,UID TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MiDB, int i, int i1) {
        MiDB.execSQL("drop Table if exists usuarios");
        MiDB.execSQL("drop Table if exists componentes");
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

    public boolean insertarOrdenador(int cpuId, int ramId, int gpuId, int psuId, int almacenamientoId, String UID) {
        SQLiteDatabase MiDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", (Integer) null);
        contentValues.put("cpuId", cpuId);
        contentValues.put("ramId", ramId);
        contentValues.put("gpuId", gpuId);
        contentValues.put("psuId", psuId);
        contentValues.put("UID", UID);
        contentValues.put("almacenamientoId", almacenamientoId);
        long resultado = MiDB.insert("ordenadores", null, contentValues);
        //se puede usar el "si es menos 1 no devuelve false,si no devuelve true" esta linea es la simplificación
        return resultado != -1;
    }

    public boolean insertarComponentes(int id, int imagen, String nombreComponente, String tipo, double precio, String caracteristicas) {
        SQLiteDatabase MiDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("imagen", imagen);
        contentValues.put("nombre", nombreComponente);
        contentValues.put("tipo", tipo);
        contentValues.put("precio", precio);
        contentValues.put("caracteristicas", caracteristicas);
        long resultado = MiDB.insert("componentes", null, contentValues);
        //se puede usar el "si es menos 1 devuelve false,si  devuelve true" esta linea es la simplificación
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

    public ArrayList<Componente> getComponentes(String tipoComponente) {
        ArrayList<Componente> listaComponentes = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from componentes where tipo='" + tipoComponente + "'", null);

        while (res.moveToNext()) {
            int id = res.getInt(0);
            int imagen = res.getInt(1);
            String nombreComponente = res.getString(2);
            String tipo = res.getString(3);
            double precio = res.getDouble(4);
            String caracteristicas = res.getString(5);


            Componente nuevoComponente = new Componente(id, imagen, nombreComponente, tipo, precio, caracteristicas);
            listaComponentes.add(nuevoComponente);
        }
        return listaComponentes;
    }
    // TODO: 22/01/2022 Investigar solucion para los ordenadores 
    /*
    public ArrayList<Ordenador> getOrdenadores() {
        ArrayList<Ordenador> listaOrdenadores = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from ordenadores", null);

        while (res.moveToNext()) {
            int id = res.getInt(0);
            int cpuId = res.getInt(1);
            String ramId = res.getString(2);
            String gpuId = res.getString(3);
            double psuId = res.getDouble(4);
            String almacenamientoId = res.getString(5);
            String UID = res.getString(6);


            Componente nuevoOrdenador = new Ordenador(id,cpuId, ramId,gpuId, psuId,almacenamientoId,UID);
            listaOrdenadores.add(nuevoOrdenador);
        }
        return listaComponentes;
    }*/
}
