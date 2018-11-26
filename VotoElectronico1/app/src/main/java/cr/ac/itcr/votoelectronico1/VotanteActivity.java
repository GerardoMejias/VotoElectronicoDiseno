package cr.ac.itcr.votoelectronico1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLException;
import java.util.ArrayList;

public class VotanteActivity extends AppCompatActivity {

    ListView listViewPropuesta;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public ArrayList<VotoRegistrado> listVotoRegistrado = new ArrayList<>();

    public ArrayList<Propuesta> listPropuesta = new ArrayList<>();
    public ArrayAdapter<Propuesta> arrayAdapterPropuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votante);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializarFirebase();
        listViewPropuesta = (ListView) findViewById(R.id.listView27);
        capturarPropuestas();

        capturarVotoRegistrados();

        Button salir = (Button) findViewById(R.id.button11);
        salir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Persona pNuevo = Almacenamiento.logeadoLocal;
                pNuevo.setConectado(false);
                databaseReference.child("Persona").child(pNuevo.getUid()).setValue(pNuevo);
                Almacenamiento.logeadoLocal = null;
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        ListView lista = (ListView) findViewById(R.id.listView27);
        lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                Propuesta propuestaSeleccionada = (Propuesta) parent.getItemAtPosition(position);
                Almacenamiento.propuestaActual = propuestaSeleccionada;



                listPropuesta.clear();
                arrayAdapterPropuesta.clear();

                Intent myIntent = new Intent(view.getContext(), VotarActivity.class);
                startActivityForResult(myIntent, 0);

            }
        });

    }

    private void inicializarFirebase() {

        FirebaseApp.initializeApp(null);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void capturarPropuestas() {
        databaseReference.child("Propuesta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                listPropuesta.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {
                    Propuesta p = objSnapshot.getValue(Propuesta.class);
                    if(!verificarParticipacion(Almacenamiento.logeadoLocal.getUid(), p.getUid())
                            &&!(p.getFinalizado()))
                    {
                        listPropuesta.add(p);
                    }
                    //if (!listPropuesta.isEmpty()) {
                        arrayAdapterPropuesta = new ArrayAdapter<Propuesta>(getApplicationContext(), android.R.layout.simple_list_item_1, listPropuesta);
                        listViewPropuesta.setAdapter(arrayAdapterPropuesta);
                    //}
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }

    private void capturarVotoRegistrados() {
        databaseReference.child("VotoRegistrado").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                listVotoRegistrado.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {
                    VotoRegistrado v = objSnapshot.getValue(VotoRegistrado.class);
                    listVotoRegistrado.add(v);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }

    private boolean verificarParticipacion(String uidPersona, String uidPropuesta)
    {
        //VotoRegistrado v = null;
        for (int i = 0; i < listVotoRegistrado.size(); i++)
        {
            if(listVotoRegistrado.get(i).getUidPersona().equals(uidPersona)
                    && listVotoRegistrado.get(i).getUidPropuesta().equals(uidPropuesta))
            {
                //v = listVotoRegistrado.get(i);
                //break;
                return true;
            }
        }
        /*if (v == null)
        {
            return false;
        }*/
        return false;
    }

}
