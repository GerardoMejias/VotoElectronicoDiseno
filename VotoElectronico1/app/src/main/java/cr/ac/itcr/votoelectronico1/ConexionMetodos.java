package cr.ac.itcr.votoelectronico1;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Gerardo on 6/6/2018.
 */
public class ConexionMetodos {

    public static ArrayList<Propuesta> lista = new ArrayList<>();
    public static ArrayList<Usuario> listaU = new ArrayList<>();
    //public static Propuesta propuestaLocal = new Propuesta("", "", "", "", "", "");

    public static String tituloPropuestaLocal = "";


    public static String titulo = "";
    public static String tituloTemp = "";
    public static String autor = "";
    public static String fLimite = "";
    public static String hLimite = "";
    public static String descripcion = "";


    public ConexionMetodos() {

    }

    public static void insertarPropuestas(String tit, String aut, String fl, String hl, String desc, String cor) {
        //Propuesta p = new Propuesta(tit, aut, fl, hl, desc, cor);
        //lista.add(p);
    }

    /*public static void insertarUsuarios(String nombre, String apellidos, String correo, String clave, boolean rol) {
        Usuario u = new Usuario(nombre, apellidos, correo, clave, rol);
        listaU.add(u);
    }*/

    public static ArrayList getPropuestas() {
        return lista;
    }

    public static ArrayList getListaU() {
        return listaU;
    }

    ////////////////////////DEVUELVE UNA PROPUESTA POR SU NOMBRE//////////////////////////////////
    public static Propuesta getPropuesta(String nombre) {
        Propuesta propuesta = null;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getTitulo().equals(nombre)) {
                propuesta = lista.get(i);
                break;
            }
        }
        return propuesta;
    }
    ///////////////////////////////////////////////////////////////////////

    //////////////////DEVUELVE LA PROPUESTA LOCAL CON QUE TRABAJAR////////////////////
    public static Propuesta getPropuestaLocal() {
        return null;
    }

    /////////////////////////////////////////////////////////////////////////////////
    //////////////////SETEA LA PROPUESTA LOCAL CON QUE TRABAJAR////////////////////
    public static void setPropuestaLocal(Propuesta propuestaLocal) {
        //ConexionMetodos.propuestaLocal = propuestaLocal;
    }
    ////////////////////////////////////////////////////////////////////////////


    ////////////////CAMBIAR LOS VALORES DE LA PRUPOUESTA CON EL NOMBRE tituloABuscar////////////////////////
    public static void editarPropuesta(String tituloABuscar, String tit, String aut, String fl, String hl, String desc) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getTitulo().equals(tituloABuscar)) {
                lista.get(i).setTitulo(tit);
                lista.get(i).setAutor(aut);
                lista.get(i).setFechaLimite(fl);
                lista.get(i).setHoraLimite(hl);
                lista.get(i).setDescripcion(desc);
                break;
            }
        }
    }
    /////////////////////////////////////////////////////////////


    /////////////////VOTAR PROPUESTA////////////////

    /*public static void votarPropuesta(String titulo, int n) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getTitulo().equals(titulo)) {
                if (n == 1) {
                    lista.get(i).votarAprobar();
                } else if (n == 2) {
                    lista.get(i).votarRechazar();
                } else if (n == 3) {
                    lista.get(i).votarNulo();
                } else if (n == 4) {
                    lista.get(i).votarEnBLanco();
                }
            }
        }
    }*/

    //////////////////////////////////////////////////


    public static String getDescripcion() {
        return descripcion;
    }

    public static void setDescripcion(String descripcion) {
        ConexionMetodos.descripcion = descripcion;
    }

    public static String gethLimite() {
        return hLimite;
    }

    public static void sethLimite(String hLimite) {
        ConexionMetodos.hLimite = hLimite;
    }

    public static String getfLimite() {
        return fLimite;
    }

    public static void setfLimite(String fLimite) {
        ConexionMetodos.fLimite = fLimite;
    }

    public static String getAutor() {
        return autor;
    }

    public static void setAutor(String autor) {
        ConexionMetodos.autor = autor;
    }

    public static String getTituloTemp() {
        return tituloTemp;
    }

    public static void setTituloTemp(String tituloTemp) {
        ConexionMetodos.tituloTemp = tituloTemp;
    }

    public static String getTitulo() {
        return titulo;
    }

    public static void setTitulo(String titulo) {
        ConexionMetodos.titulo = titulo;
    }

   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static Connection conexionBDHost()
    {
        Connection conexion = null;

        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://mssql1.gear.host;databaseName=votoelectronicdb;user=votoelectronicdb;password=Cb43TkB~6!DE");

            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            //conexion = DriverManager.getConnection("jdbc:mysql://db4free.net:3306","ninalineadmin","al13ks64");


            PreparedStatement pst = conexion.prepareStatement("INSERT INTO `ninalinedb`.`Usuario`(`nombre`, `apellido`, `correo`, `clave`, `rol`) VALUES (`Juan`,`Perez`,`aaa@sss.dd`,`claveclave`,2)");
            pst.executeUpdate();
            conexion.close();

        } catch (Exception e)
        {
        }
        return conexion;
    }

    public static void agregarUsuario(String nombre, String apellido, String correo, String clave, int rol) {
        try {
            PreparedStatement pst = conexionBDHost().prepareStatement("INSERT INTO Usuario VALUES (?,?,?,?,?)");
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setString(3, correo);
            pst.setString(4, clave);
            pst.setInt(5, rol);

            pst.executeUpdate();
            conexionBDHost().close();

        } catch (Exception e) {
        }
    }

    public static void agregarPropuesta(String titulo, String autor, String descripcion, String fLimite, String hLimite, String correos)
    {
        try
        {
            PreparedStatement pst = conexionBDHost().prepareStatement("INSERT INTO Propuesta VALUES (?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, titulo);
            pst.setString(2, autor);
            pst.setString(3, descripcion);
            pst.setString(4, fLimite);
            pst.setString(5, hLimite);
            pst.setString(6, correos);
            pst.setInt(7, 0);
            pst.setInt(8, 0);
            pst.setInt(9, 0);
            pst.setInt(10, 0);

            pst.executeUpdate();
            //Toast.makeText(getApplicationContext(), "¡EXITO!", Toast.LENGTH_SHORT).show();

            conexionBDHost().close();

        } catch (Exception e)
        {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public static ArrayList<String> getPropuestasDeBD() throws SQLException {
        ArrayList<String> listaPropuestas = new ArrayList<>();
        try
        {
            PreparedStatement pst = conexionBDHost().prepareStatement("SELECT titulo FROM Propuesta");
            PreparedStatement pstCantidad = conexionBDHost().prepareStatement("SELECT COUNT(titulo) FROM Propuesta");

            ResultSet result = pst.executeQuery();
            int cantidadRegistros = 0;

            while (result.next())
            {
                listaPropuestas.add(result.getString(1));
            }

            conexionBDHost().close();

        } catch (Exception e) {

        }
        return listaPropuestas;
    }

    public static void votarAprueboPropuesta(String titulo)
    {
        try {
            PreparedStatement pstGetCantidad = conexionBDHost().prepareStatement("SELECT aprueban FROM Propuesta WHERE titulo = (?)");
            PreparedStatement pstSumar = conexionBDHost().prepareStatement("UPDATE Propuesta SET aprueban = (?) WHERE titulo = (?)");

            pstGetCantidad.setString(1, titulo);
            ResultSet resultCantidad = pstGetCantidad.executeQuery();
            int aprueban = 0; //resultCantidad.getInt(1);
            while (resultCantidad.next())
            {
                aprueban = resultCantidad.getInt(1);
            }
            aprueban++;

            pstSumar.setInt(1, aprueban);
            pstSumar.setString(2, titulo);
            pstSumar.executeUpdate();
            //Toast.makeText(getApplicationContext(), "¡EXITO!", Toast.LENGTH_SHORT).show();
            conexionBDHost().close();
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void votarRechazoPropuesta(String titulo)
    {
        try {
            PreparedStatement pstGetCantidad = conexionBDHost().prepareStatement("SELECT rechazan FROM Propuesta WHERE titulo = (?)");
            PreparedStatement pstSumar = conexionBDHost().prepareStatement("UPDATE Propuesta SET rechazan = (?) WHERE titulo = (?)");

            pstGetCantidad.setString(1, titulo);
            ResultSet resultCantidad = pstGetCantidad.executeQuery();
            int rechazan = 0; //resultCantidad.getInt(1);
            while (resultCantidad.next())
            {
                rechazan = resultCantidad.getInt(1);
            }
            rechazan++;

            pstSumar.setInt(1, rechazan);
            pstSumar.setString(2, titulo);
            pstSumar.executeUpdate();
            //Toast.makeText(getApplicationContext(), "¡EXITO!", Toast.LENGTH_SHORT).show();
            conexionBDHost().close();

        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void votarNuloPropuesta(String titulo)
    {
        try {
            PreparedStatement pstGetCantidad = conexionBDHost().prepareStatement("SELECT nulos FROM Propuesta WHERE titulo = (?)");
            PreparedStatement pstSumar = conexionBDHost().prepareStatement("UPDATE Propuesta SET nulos = (?) WHERE titulo = (?)");

            pstGetCantidad.setString(1, titulo);
            ResultSet resultCantidad = pstGetCantidad.executeQuery();
            int nulos = 0;// resultCantidad.getInt(1);
            while (resultCantidad.next())
            {
                nulos = resultCantidad.getInt(1);
            }
            nulos++;

            pstSumar.setInt(1, nulos);
            pstSumar.setString(2, titulo);
            pstSumar.executeUpdate();
            //Toast.makeText(getApplicationContext(), "¡EXITO!", Toast.LENGTH_SHORT).show();
            conexionBDHost().close();

        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void votarBlancoPropuesta(String titulo)
    {
        try {
            PreparedStatement pstGetCantidad = conexionBDHost().prepareStatement("SELECT blancos FROM Propuesta WHERE titulo = (?)");
            PreparedStatement pstSumar = conexionBDHost().prepareStatement("UPDATE Propuesta SET blancos = (?) WHERE titulo = (?)");

            pstGetCantidad.setString(1, titulo);
            ResultSet resultCantidad = pstGetCantidad.executeQuery();
            int blancos = 0;//resultCantidad.getInt(1);
            while (resultCantidad.next())
            {
                blancos = resultCantidad.getInt(1);
            }
            blancos++;

            pstSumar.setInt(1, blancos);
            pstSumar.setString(2, titulo);
            pstSumar.executeUpdate();
            //Toast.makeText(getApplicationContext(), "¡EXITO!", Toast.LENGTH_SHORT).show();
            conexionBDHost().close();

        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static int getCantidadAprueban(String titulo)
    {
        int cantidad = 0;
        try {
            PreparedStatement pstGetCantidad = conexionBDHost().prepareStatement("SELECT aprueban FROM Propuesta WHERE titulo = (?)");
            pstGetCantidad.setString(1, titulo);

            ResultSet result = pstGetCantidad.executeQuery();
            while (result.next())
            {
                cantidad = result.getInt(1);
            }
            //Toast.makeText(getApplicationContext(), "¡EXITO!", Toast.LENGTH_SHORT).show();
            conexionBDHost().close();

        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return cantidad;
    }

    public static int getCantidadRechazan(String titulo)
    {
        int cantidad = 0;
        try {
            PreparedStatement pstGetCantidad = conexionBDHost().prepareStatement("SELECT rechazan FROM Propuesta WHERE titulo = (?)");
            pstGetCantidad.setString(1, titulo);

            ResultSet result = pstGetCantidad.executeQuery();
            cantidad = 0; //result.getInt(1);
            while (result.next())
            {
                cantidad = result.getInt(1);
            }
            //Toast.makeText(getApplicationContext(), "¡EXITO!", Toast.LENGTH_SHORT).show();
            conexionBDHost().close();

        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return cantidad;
    }

    public static int getCantidadNulos(String titulo)
    {
        int cantidad = 0;
        try {
            PreparedStatement pstGetCantidad = conexionBDHost().prepareStatement("SELECT nulos FROM Propuesta WHERE titulo = (?)");
            pstGetCantidad.setString(1, titulo);

            ResultSet result = pstGetCantidad.executeQuery();
            while (result.next())
            {
                cantidad = result.getInt(1);
            }
            //Toast.makeText(getApplicationContext(), "¡EXITO!", Toast.LENGTH_SHORT).show();
            conexionBDHost().close();

        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return cantidad;
    }

    public static int getCantidadBlancos(String titulo)
    {
        int cantidad = 0;
        try {
            PreparedStatement pstGetCantidad = conexionBDHost().prepareStatement("SELECT blancos FROM Propuesta WHERE titulo = (?)");
            pstGetCantidad.setString(1, titulo);

            ResultSet result = pstGetCantidad.executeQuery();
            while (result.next())
            {
                cantidad = result.getInt(1);
            }
            //Toast.makeText(getApplicationContext(), "¡EXITO!", Toast.LENGTH_SHORT).show();
            conexionBDHost().close();

        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return cantidad;
    }
}
