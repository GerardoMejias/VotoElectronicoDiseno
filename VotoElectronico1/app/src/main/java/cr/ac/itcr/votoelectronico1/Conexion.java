package cr.ac.itcr.votoelectronico1;

import android.support.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class Conexion {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Conexion()
    {

    }

    public void inicializarFirebase() {
        FirebaseApp.initializeApp(null);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void insertarPropuestaFirebase(String titulo, String autor, String fLimite, String hLimite,
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

    public ArrayList<Propuesta> capturarPropuestas() {
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

    public boolean validacion(String titulo, String autor, String fLimite, String hLimite, String descripcion, String correos)
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

    public Propuesta buscarPropuesta(ArrayList<Propuesta> listPropuesta, String titulo,
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

}
