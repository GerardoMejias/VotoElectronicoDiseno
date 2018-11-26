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
import android.widget.RadioButton;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUsuarioCamposActivity extends AppCompatActivity {

    //Variables para referenciar los campos de texto de la interfaz
    EditText nombreEditText;
    EditText apellidosEditText;
    EditText correoEditText;
    EditText claveEditText;
    CheckBox rolA;
    CheckBox rolB;

    ////Variables para referenciar la base de datos Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //Variable temporal que contiene la Persona que se va a editar
    Persona personaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_usuario_campos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializa las referencias a la base de datos.
        // Este método está definido más adelante.
        inicializarFirebase();

        //Inicializa la variable temporal que contiene a la Persona a editar
        personaSeleccionada = Almacenamiento.personaActual;

        //Muestra los datos actuales de la Persona que se va a editar
        //en los campos correspondientes.
        if (personaSeleccionada != null)
        {
            nombreEditText = (EditText) findViewById(R.id.editText3);
            apellidosEditText = (EditText) findViewById(R.id.editText4);
            correoEditText = (EditText) findViewById(R.id.editText5);
            claveEditText = (EditText) findViewById(R.id.editText6);
            rolA = (CheckBox) findViewById(R.id.radioButton);
            rolB = (CheckBox) findViewById(R.id.radioButton2);

            nombreEditText.setText(personaSeleccionada.getNombre());
            apellidosEditText.setText(personaSeleccionada.getApellidos());
            correoEditText.setText(personaSeleccionada.getCorreo());
            claveEditText.setText(personaSeleccionada.getClave());
            if (personaSeleccionada.getEsAdministrador())
            {
                rolA.toggle();
            }
            if (personaSeleccionada.getActivo())
            {
                rolB.toggle();
            }

        }

        Button atras = (Button) findViewById(R.id.button4);
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), EditUsuarioListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        //Aquí se define el comportamiento del botón editar.
        //Incluye verificar que los campos de texto no estén vacíos,
        //capturar los datos de los campos de texto,
        //crear un objeto Persona e insertarlo en la base de datos
        //para que SOOBRESCRIBA AL QUE SE VA A EDITAR
        //con la referencia inicializada anteriormente.
        Button editar = (Button) findViewById(R.id.button5);
        editar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String uid = personaSeleccionada.getUid();
                String nombre = nombreEditText.getText().toString();
                String apellidos = apellidosEditText.getText().toString();
                String correo = correoEditText.getText().toString();
                String clave = claveEditText.getText().toString();
                boolean esAdministrador = false;
                boolean activo = false;
                if(rolA.isChecked())
                {
                    esAdministrador = true;
                }
                if(rolB.isChecked())
                {
                    activo = true;
                }

                if (nombre.equals("") || apellidos.equals("") || correo.equals("") || clave.equals(""))
                {
                    validacion(nombre, apellidos, correo, clave, esAdministrador);
                }
                else
                {
                    Persona p = new Persona();
                    p.setUid(uid);
                    p.setNombre(nombre);
                    p.setApellidos(apellidos);
                    p.setCorreo(correo);
                    p.setClave(clave);
                    p.setEsAdministrador(esAdministrador);
                    p.setActivo(activo);

                    databaseReference.child("Persona").child(p.getUid()).setValue(p);
                    personaSeleccionada = null;
                    Almacenamiento.personaActual = null;

                    Intent myIntent = new Intent(view.getContext(), EditUsuarioExitoActivity.class);
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
    private void validacion(String nombre, String apellidos, String correo, String clave, boolean rol)
    {
        if(nombre.equals(""))
            nombreEditText.setError("Requerido");
        else if(apellidos.equals(""))
            apellidosEditText.setError("Requerido");
        else if(correo.equals(""))
            correoEditText.setError("Requerido");
        else if(clave.equals(""))
            claveEditText.setError("Requerido");
    }

}
