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
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditUsuarioListActivity extends AppCompatActivity {

    //Referencia a la lista a mostrar en la interfaz de usuario
    ListView listViewPersona;

    //Variables para referenciar la base de datos Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //Lista que almacenará las Personas cargadas desde
    //la base de datos y adaptador que se le asignará
    //a la lista referenciada de la interfaz de usuario.
    public ArrayList<Persona> listPersona = new ArrayList<>();
    public ArrayAdapter<Persona> arrayAdapterPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_usuario_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializa las referencias a la base de datos.
        // Este método está definido más adelante.
        inicializarFirebase();

        //Inicializar la referencia a la lista en la interfaz de usuario
        listViewPersona = (ListView) findViewById(R.id.listView120);

        //Método que carga las Propuestas desde la base de datos
        capturarPersonas();

        Button atras = (Button) findViewById(R.id.button20);
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        //Define el comportamiento al seleccionar la Persona que se desea editar:
        //se guarda en una variable local,
        //se vacían las listas locales,
        //Se llama a la interfaz de usuario para editar los datos de la Persona.
        listViewPersona.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                Persona personaSeleccionada = (Persona) parent.getItemAtPosition(position);
                Almacenamiento.personaActual = personaSeleccionada;

                listPersona.clear();
                arrayAdapterPersona.clear();

                Intent myIntent = new Intent(view.getContext(), EditUsuarioCamposActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }
    //Valida que ningún campo de texto esté vacío
    private void capturarPersonas() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                listPersona.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {
                    Persona p = objSnapshot.getValue(Persona.class);
                    listPersona.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Persona>(getApplicationContext(), android.R.layout.simple_list_item_1, listPersona);
                    listViewPersona.setAdapter(arrayAdapterPersona);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }

    //Inicializa las referencias a la base de datos Firebase
    public void inicializarFirebase()
    {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}
