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

import java.util.ArrayList;

public class EditPropuestaListActivity extends AppCompatActivity {

    //Referencia a la lista a mostrar en la interfaz de usuario
    ListView listViewPropuesta;

    //Variables para referenciar la base de datos Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //Lista que almacenará las Propuestas cargadas desde
    //la base de datos y adaptador que se le asignará
    //a la lista referenciada de la interfaz de usuario.
    public ArrayList<Propuesta> listPropuesta = new ArrayList<>();
    public ArrayAdapter<Propuesta> arrayAdapterPropuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_propuesta_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializa las referencias a la base de datos.
        // Este método está definido más adelante.
        inicializarFirebase();

        //Inicializar la referencia a la lista en la interfaz de usuario
        listViewPropuesta = (ListView) findViewById(R.id.listView1);

        //Método que carga las Propuestas desde la base de datos
        capturarPropuestas();

        Button atras = (Button) findViewById(R.id.button21);
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        //Define el comportamiento al seleccionar la Propuesta que se desea editar:
        //se guarda en una variable local,
        //se vacían las listas locales,
        //Se llama a la interfaz de usuario para editar los datos de la Propuesta.
        listViewPropuesta.setOnItemClickListener(new AdapterView.OnItemClickListener()  {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Propuesta propuestaSeleccionada = (Propuesta) parent.getItemAtPosition(position);
                Almacenamiento.propuestaActual = propuestaSeleccionada;

                listPropuesta.clear();
                arrayAdapterPropuesta.clear();

                Intent myIntent = new Intent(view.getContext(), EditPropuestaCamposActivity.class);
                startActivityForResult(myIntent, 0);

            }
        });

    }

    //Inicializa las referencias a la base de datos Firebase
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    //Valida que ningún campo de texto esté vacío
    private void capturarPropuestas() {
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

                    arrayAdapterPropuesta = new ArrayAdapter<Propuesta>(getApplicationContext(), android.R.layout.simple_list_item_1, listPropuesta);
                    listViewPropuesta.setAdapter(arrayAdapterPropuesta);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }

}
