package com.example.proyecto_tommy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * clase para crear la base de datos y sus respectivos métodos para crear,actualizar,añadir datos,comprobar
 * usuario,comprobar contraseña
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String nombreDB = "pcApp.db";
    SQLiteDatabase MiDB = this.getWritableDatabase();

    public DBHelper(Context context) {
        super(context, nombreDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MiDB) {
        MiDB.execSQL("create Table usuarios(usuario TEXT primary key,contrasena TEXT,tipoUsuario TEXT,curso INT)");
        MiDB.execSQL("create Table componentes(ID INTEGER primary key,imagen INT,nombre TEXT,tipo TEXT,precio REAL,caracteristicas TEXT)");
        MiDB.execSQL("create Table ordenadores(ID INTEGER primary key,fecha TEXT,precio REAL,cpuId INT,ramId INT,gpuId INT,psuId INT,almacenamientoId INT,UID TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MiDB, int i, int i1) {
        MiDB.execSQL("drop Table if exists usuarios");
        MiDB.execSQL("drop Table if exists componentes");
        MiDB.execSQL("drop Table if exists ordenadores");
    }

    public boolean insertarDatosUsuario(String usuario, String contrasena, String tipoUsuario, int curso) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("usuario", usuario);
        contentValues.put("contrasena", contrasena);
        contentValues.put("tipoUsuario", tipoUsuario);
        contentValues.put("curso", curso);
        long resultado = MiDB.insert("usuarios", null, contentValues);
        //se puede usar el "si es menos 1 no devuelve false,si no devuelve true" esta linea es la simplificación
        return resultado != -1;
    }

    public boolean insertarOrdenador(String fecha, double precio, int cpuId, int ramId, int gpuId, int psuId, int almacenamientoId, String UID) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", (Integer) null);
        contentValues.put("fecha", fecha);
        contentValues.put("precio", precio);
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

    public void insertarComponentes(int id, int imagen, String nombreComponente, String tipo, double precio, String caracteristicas) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("imagen", imagen);
        contentValues.put("nombre", nombreComponente);
        contentValues.put("tipo", tipo);
        contentValues.put("precio", precio);
        contentValues.put("caracteristicas", caracteristicas);
        try {
            MiDB.insertOrThrow("componentes", null, contentValues);
        } catch (SQLException e) {
            System.out.println("Este componente ya está en la lista");
        }
    }

    public Boolean comprobarUsuario(String usuario) {
        Cursor cursor = MiDB.rawQuery("SELECT * FROM usuarios WHERE usuario = ?", new String[]{usuario});
        return cursor.getCount() > 0;
    }

    public Boolean comprobarContrasenaUsuario(String usuario, String contrasena) {
        Cursor cursor = MiDB.rawQuery("SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?", new String[]{usuario, contrasena});
        return cursor.getCount() > 0;
    }

    public ArrayList<Componente> getComponentes(String tipoComponente) {
        ArrayList<Componente> listaComponentes = new ArrayList<>();
        Cursor res = MiDB.rawQuery("SELECT * FROM componentes WHERE tipo = ?", new String[]{tipoComponente});

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

    public Boolean updateUsuario(String usuario, String contrasena, String tipoUsuario, int curso) {
        SQLiteDatabase MiDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("usuario", usuario);
        cv.put("contrasena", contrasena);
        cv.put("tipoUsuario", tipoUsuario);
        cv.put("curso", curso);

        return MiDB.update("usuarios", cv, "usuario = ?", new String[]{usuario}) > 0;

    }

    public boolean borrarUsuario(String email) {
        return MiDB.delete("usuarios", "usuario=?", new String[]{email}) > 0;

    }

    public Boolean comprobarAdmin(String email) {
        Cursor cursor = MiDB.rawQuery("SELECT * FROM usuarios WHERE usuario = ? AND tipoUsuario='Profesor'", new String[]{email});
        return cursor.getCount() > 0;
    }

    public int[] getIdsPC(String idOrdenador) {
        int[] listaIds = new int[5];
        Cursor res = MiDB.rawQuery("SELECT cpuID,ramID,gpuId,psuID,almacenamientoId FROM ordenadores WHERE ID = ?", new String[]{idOrdenador});

        while (res.moveToNext()) {
            int cpuID = res.getInt(0);
            int ramID = res.getInt(1);
            int gpuId = res.getInt(2);
            int psuID = res.getInt(3);
            int almacenamientoId = res.getInt(4);

            listaIds[0] = cpuID;
            listaIds[1] = ramID;
            listaIds[2] = gpuId;
            listaIds[3] = psuID;
            listaIds[4] = almacenamientoId;
        }
        return listaIds;
    }

    public ArrayList<Componente> getComponentesPc(String idOrdenador) {
        ArrayList<Componente> listaComponentes = new ArrayList<>();
        int[] listaIds = getIdsPC(idOrdenador);
        Cursor res = MiDB.rawQuery("SELECT * FROM componentes WHERE id in(?,?,?,?,?)", new String[]{String.valueOf(listaIds[0]), String.valueOf(listaIds[1]), String.valueOf(listaIds[2]), String.valueOf(listaIds[3]), String.valueOf(listaIds[4])});
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

    public boolean borrarPc(int id) {
        return MiDB.delete("ordenadores", "ID=?", new String[]{String.valueOf(id)}) > 0;

    }
}
