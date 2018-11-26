package cr.ac.itcr.votoelectronico1;

import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Almacenamiento {
    public static ArrayList<Propuesta> listaPropuestas = new ArrayList<>();
    public static ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    public static ArrayList<Voto> listaVotos = new ArrayList<>();

    public static String usuarioActual = "";
    public static String usuarioActualGestionando = "";

    public static String propuestaActualGestionada = "";

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;

    //NO BORRAR - SON DEFINITIVOS///////////////////////////////////////
    public static Persona personaActual = null;
    public static Persona logeadoLocal = null;
    public static Propuesta propuestaActual = null;

    ////////////////////////////////////////////////////////////////////

    public static void insertarPropuestaFirebase(String titulo, String autor, String fLimite, String hLimite,
                                                 String descripcion, String correos)
    {
        if (validacion(titulo, autor, fLimite, hLimite, descripcion, correos)) {
            Propuesta p = new Propuesta();
            p.setUid(UUID.randomUUID().toString());
            p.setTitulo(titulo);
            p.setAutor(autor);
            p.setFechaLimite(fLimite);
            p.setHoraLimite(hLimite);
            p.setDescripcion(descripcion);
            p.setCorreos(correos);
            p.setFinalizado(false);
            p.setAprueban(0);
            p.setRechazan(0);
            p.setNulos(0);
            p.setBlancos(0);

            databaseReference.child("Propuesta").child(p.getUid()).setValue(p);
        }
    }

    public static ArrayList<Propuesta> capturarPropuestas() {
        final ArrayList<Propuesta> listPropuesta = new ArrayList<>();
        databaseReference.child("Propuesta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                listPropuesta.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {
                    Propuesta p = objSnapshot.getValue(Propuesta.class);
                    if (!(p.getFinalizado())) {
                        listPropuesta.add(p);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
        return listPropuesta;
    }

    public static Propuesta buscarPropuesta(ArrayList<Propuesta> listPropuesta, String titulo,
                                            String autor, String fLimite, String hLimite,
                                            String descripcion, String correos)
    {
        for (int i = 0; i < listPropuesta.size(); i++)
        {
            Propuesta p = listPropuesta.get(i);
            if (p.getTitulo().equals(titulo) && p.getAutor().equals(autor) && p.getFechaLimite().equals(fLimite)
                    && p.getHoraLimite().equals(hLimite) && p.getDescripcion().equals(descripcion)
                    && p.getCorreos().equals(correos))
            {
                return p;
            }
        }
        return null;
    }

    public static void inicializarFirebase() {
        FirebaseApp.initializeApp(null);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public static boolean validacion(String titulo, String autor, String fLimite, String hLimite, String descripcion, String correos)
    {
        if(titulo.equals(""))
        {
            return false;
        }
        else if(autor.equals(""))
        {
            return false;
        }
        else if(fLimite.equals(""))
        {
            return false;
        }
        else if(hLimite.equals(""))
        {
            return false;
        }
        else if(descripcion.equals(""))
        {
            return false;
        }
        else if(correos.equals(""))
        {
            return false;
        }

        else
        {
            return true;
        }
    }

    ////////////////////////////////////////////////////////////////////

    public static void insertarUsuario(String nombre, String apellidos, String correo, String clave, boolean rol)
    {
        Usuario usuarioNuevo = new Usuario(nombre, apellidos, correo, clave, rol);
        listaUsuarios.add(usuarioNuevo);
    }

    public static void editarUsuario(String nombre, String apellidos, String correo, String clave, boolean rol) {
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getCorreo() == correo) {
                listaUsuarios.get(i).setNombre(nombre);
                listaUsuarios.get(i).setApellidos(apellidos);
                listaUsuarios.get(i).setCorreo(correo);
                listaUsuarios.get(i).setClave(clave);
                listaUsuarios.get(i).setRol(rol);
            }
        }
    }

    public static Usuario buscarUsuario(String correo)
    {
        Usuario usuario = null;
        for (int i = 0; i < listaUsuarios.size(); i++)
        {
            if (listaUsuarios.get(i).getCorreo().equals(correo))
            {
                usuario = listaUsuarios.get(i);
            }
        }
        return usuario;
    }

    public static ArrayList<Usuario> getListaUsuarios()
    {
        return listaUsuarios;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void insertarPropuesta(String titulo, String autor, String fechaLimite,
                                         String horaLimite, String descripcion, String correoAutor)
    {
        //Propuesta propuestaNueva = new Propuesta(titulo, autor, fechaLimite, horaLimite, descripcion, correoAutor);
        //listaPropuestas.add(propuestaNueva);
    }

    public static void editarPropuesta(String titulo, String autor, String fechaLimite,
                                         String horaLimite, String descripcion, String correoAutor)
    {
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaPropuestas.get(i).getTitulo() == titulo) {
                listaPropuestas.get(i).setTitulo(titulo);
                listaPropuestas.get(i).setAutor(autor);
                listaPropuestas.get(i).setFechaLimite(fechaLimite);
                listaPropuestas.get(i).setHoraLimite(horaLimite);
                listaPropuestas.get(i).setDescripcion(descripcion);
                listaPropuestas.get(i).setCorreos(correoAutor);
            }
        }
    }

    public static Propuesta buscarPropuesta(String titulo)
    {
        Propuesta propuesta = null;
        for (int i = 0; i < listaPropuestas.size(); i++)
        {
            if (listaPropuestas.get(i).getTitulo() == titulo)
            {
                return listaPropuestas.get(i);
            }
        }
        return propuesta;
    }

    public static ArrayList<Propuesta> getListaPropuestas()
    {
        return listaPropuestas;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void registrarVoto(String tituloPropuesta, String correoVotante, int voto)
    {
        for (int i = 0; i < listaPropuestas.size(); i++)
        {
            if (listaPropuestas.get(i).getTitulo() == tituloPropuesta)
            {
                if (voto == 1)
                {
                    //listaPropuestas.get(i).aprueban++;
                }
                else if (voto == 2)
                {
                    //listaPropuestas.get(i).rechazan++;
                }
                else if (voto == 3)
                {
                    //listaPropuestas.get(i).nulos++;
                }
                else if (voto == 4)
                {
                    //listaPropuestas.get(i).blancos++;
                }
            }
        }
        Voto votoNuevo = new Voto(tituloPropuesta, correoVotante);
        listaVotos.add(votoNuevo);
    }

}
