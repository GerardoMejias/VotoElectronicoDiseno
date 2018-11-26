package cr.ac.itcr.votoelectronico1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class EditPropuestaCamposActivity extends AppCompatActivity {

    //Variables para referenciar los campos de texto de la interfaz
    EditText tituloEditText;
    EditText autorEditText;
    EditText fLimiteEditText;
    EditText hLimiteEditText;
    EditText descripcionEditText;
    EditText correosEditText;
    CheckBox finalizadoCheckBox;

    //Variables para referenciar la base de datos Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //Variable temporal que contiene la Propuesta que se va a editar
    public Propuesta propuestaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_propuesta_campos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializa las referencias a la base de datos.
        // Este método está definido más adelante.
        inicializarFirebase();

        //Inicializa la variable temporal que contiene a la Propuesta a editar
        propuestaSeleccionada = Almacenamiento.propuestaActual;

        //Muestra los datos actuales de la Propuesta que se va a editar
        //en los campos correspondientes.
        tituloEditText = (EditText) findViewById(R.id.editText3);
        autorEditText = (EditText) findViewById(R.id.editText4);
        fLimiteEditText = (EditText) findViewById(R.id.editText7);
        hLimiteEditText = (EditText) findViewById(R.id.editText8);
        descripcionEditText = (EditText) findViewById(R.id.editText9);
        correosEditText = (EditText) findViewById(R.id.editText10);
        finalizadoCheckBox = (CheckBox) findViewById(R.id.checkBox60);

        tituloEditText.setText(propuestaSeleccionada.getTitulo());
        autorEditText.setText(propuestaSeleccionada.getAutor());
        fLimiteEditText.setText(propuestaSeleccionada.getFechaLimite());
        hLimiteEditText.setText(propuestaSeleccionada.getHoraLimite());
        descripcionEditText.setText(propuestaSeleccionada.getDescripcion());
        correosEditText.setText(propuestaSeleccionada.getCorreos());

        Button atras = (Button) findViewById(R.id.button9);
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), EditPropuestaListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        //Aquí se define el comportamiento del botón editar.
        //Incluye verificar que los campos de texto no estén vacíos,
        //capturar los datos de los campos de texto,
        //crear un objeto Propuesta e insertarlo en la base de datos
        //para que SOOBRESCRIBA AL QUE SE VA A EDITAR
        //con la referencia inicializada anteriormente.
        Button editar = (Button) findViewById(R.id.button10);
        editar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String titulo = tituloEditText.getText().toString();
                String autor = autorEditText.getText().toString();
                String fLimite = fLimiteEditText.getText().toString();
                String hLimite = hLimiteEditText.getText().toString();
                String descripcion = descripcionEditText.getText().toString();
                String correos = correosEditText.getText().toString();
                boolean finalizado = false;
                if (finalizadoCheckBox.isChecked())
                {
                    finalizado = true;
                }

                if (titulo.equals("") || autor.equals("") || fLimite.equals("") || hLimite.equals("")
                        || descripcion.equals("") || correos.equals("")) {
                    validacion(titulo, autor, fLimite, hLimite, descripcion, correos);
                }
                else
                {
                    Propuesta p = new Propuesta();
                    p.setUid(propuestaSeleccionada.getUid());
                    p.setTitulo(titulo);
                    p.setAutor(autor);
                    p.setFechaLimite(fLimite);
                    p.setHoraLimite(hLimite);
                    p.setDescripcion(descripcion);
                    p.setCorreos(correos);
                    p.setFinalizado(finalizado);
                    p.setAprueban(propuestaSeleccionada.getAprueban());
                    p.setRechazan(propuestaSeleccionada.getRechazan());
                    p.setNulos(propuestaSeleccionada.getNulos());
                    p.setBlancos(propuestaSeleccionada.getBlancos());

                    databaseReference.child("Propuesta").child(p.getUid()).setValue(p);
                    Almacenamiento.propuestaActual = null;

                    Intent myIntent = new Intent(view.getContext(), EditPropuestaExitoActivity.class);
                    startActivityForResult(myIntent, 0);
                }
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
    private void validacion(String titulo, String autor, String fLimite, String hLimite, String descripcion, String correos)
    {
        if(titulo.equals(""))
        {
            tituloEditText.setError("Requerido");
        }
        else if(autor.equals(""))
        {
            autorEditText.setError("Requerido");
        }
        else if(fLimite.equals(""))
        {
            fLimiteEditText.setError("Requerido");
        }
        else if(hLimite.equals(""))
        {
            hLimiteEditText.setError("Requerido");
        }
        else if(descripcion.equals(""))
        {
            descripcionEditText.setError("Requerido");
        }
        else if(correos.equals(""))
        {
            correosEditText.setError("Requerido");
        }
    }

}
