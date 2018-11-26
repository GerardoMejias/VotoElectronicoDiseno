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

import java.util.UUID;

public class IngUsuarioActivity extends AppCompatActivity {

    //Variables para referenciar los campos de texto de la interfaz
    EditText nombreEditText;
    EditText apellidosEditText;
    EditText correoEditText;
    EditText claveEditText;
    CheckBox rolA;

    //Variables para referenciar la base de datos Firebase
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ing_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializa las referencias a la base de datos.
        // Este método está definido más adelante.
        inicializarFirebase();

        Button atras = (Button) findViewById(R.id.button4);
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        //Aquí se define el comportamiento del botón insertar.
        //Incluye verificar que los campos de texto no estén vacíos,
        //capturar los datos de los campos de texto,
        //crear un objeto Persona e insertarlo en la base de datos
        //con la referencia inicializada anteriormente.
        Button insertar = (Button) findViewById(R.id.button5);
        insertar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                nombreEditText = (EditText) findViewById(R.id.editTextA);
                apellidosEditText = (EditText) findViewById(R.id.editTextB);
                correoEditText = (EditText) findViewById(R.id.editTextC);
                claveEditText = (EditText) findViewById(R.id.editTextD);
                rolA = (CheckBox) findViewById(R.id.radioButtonA);

                String nombre = nombreEditText.getText().toString();
                String apellidos = apellidosEditText.getText().toString();
                String correo = correoEditText.getText().toString();
                String clave = claveEditText.getText().toString();
                boolean esAdministrador = false;
                if(rolA.isChecked())
                {
                    esAdministrador = true;
                }

                if (nombre.equals("") || apellidos.equals("") || correo.equals("") || clave.equals(""))
                {
                    validacion(nombre, apellidos, correo, clave, esAdministrador);
                }
                else
                {
                    Persona p = new Persona();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellidos(apellidos);
                    p.setCorreo(correo);
                    p.setClave(clave);
                    p.setEsAdministrador(esAdministrador);
                    p.setConectado(false);
                    p.setActivo(true);

                    databaseReference.child("Persona").child(p.getUid()).setValue(p);

                    Intent myIntent = new Intent(view.getContext(), IngUsuarioExitoActivity.class);
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
