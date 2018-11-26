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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializarFirebase();

        Button salir = (Button) findViewById(R.id.button1);
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

        final Button cbIngrProp = (Button) findViewById(R.id.checkBox1);
        final Button cbEditProp = (Button) findViewById(R.id.checkBox2);
        //final Button cbElimProp = (Button) findViewById(R.id.checkBox3);
        final Button cbIngrUsua = (Button) findViewById(R.id.checkBox4);
        final Button cbEditUsua = (Button) findViewById(R.id.checkBox5);
        //final Button cbElimUsua = (Button) findViewById(R.id.checkBox6);
        final Button cbVerResul = (Button) findViewById(R.id.checkBox7);
        final Button cbVerHist = (Button) findViewById(R.id.checkBox8);
        final Button cbVerPart = (Button) findViewById(R.id.checkBox9);

        cbIngrProp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                myIntent = new Intent(view.getContext(), IngPropuestaActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        cbEditProp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                myIntent = new Intent(view.getContext(), EditPropuestaListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        /*cbElimProp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                myIntent = new Intent(view.getContext(), ElimPropuestaListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });*/

        cbIngrUsua.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                myIntent = new Intent(view.getContext(), IngUsuarioActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        cbEditUsua.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                myIntent = new Intent(view.getContext(), EditUsuarioListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        /*cbElimUsua.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                myIntent = new Intent(view.getContext(), ElimUsuarioListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });*/

        cbVerResul.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                myIntent = new Intent(view.getContext(), ResultadosListActivity.class);
                startActivityForResult(myIntent, 0);

            }
        });

        cbVerHist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                myIntent = new Intent(view.getContext(), HistorialListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        cbVerPart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                myIntent = new Intent(view.getContext(), ParticipacionListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    public void inicializarFirebase()
    {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}
