package cr.ac.itcr.votoelectronico1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class VotarActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Propuesta propuestaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializarFirebase();
        propuestaSeleccionada = Almacenamiento.propuestaActual;

        Button votar = (Button) findViewById(R.id.button44);
        votar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                try {
                    CheckBox apruebo = (CheckBox) findViewById(R.id.checkBox10);
                    CheckBox rechazo = (CheckBox) findViewById(R.id.checkBox11);
                    CheckBox nulo = (CheckBox) findViewById(R.id.checkBox12);
                    CheckBox enBlanco = (CheckBox) findViewById(R.id.checkBox13);

                    Propuesta p = new Propuesta();
                    p.setUid(propuestaSeleccionada.getUid());
                    p.setTitulo(propuestaSeleccionada.getTitulo());
                    p.setAutor(propuestaSeleccionada.getAutor());
                    p.setFechaLimite(propuestaSeleccionada.getFechaLimite());
                    p.setHoraLimite(propuestaSeleccionada.getHoraLimite());
                    p.setDescripcion(propuestaSeleccionada.getDescripcion());
                    p.setCorreos(propuestaSeleccionada.getCorreos());
                    p.setFinalizado(propuestaSeleccionada.getFinalizado());
                    p.setAprueban(propuestaSeleccionada.getAprueban());
                    p.setRechazan(propuestaSeleccionada.getRechazan());
                    p.setNulos(propuestaSeleccionada.getNulos());
                    p.setBlancos(propuestaSeleccionada.getBlancos());

                    if (apruebo.isChecked())
                    {
                        p.setAprueban(propuestaSeleccionada.getAprueban() + 1);
                    }
                    else if (rechazo.isChecked())
                    {
                        p.setRechazan(propuestaSeleccionada.getRechazan() + 1);
                    }
                    else if (nulo.isChecked())
                    {
                        p.setNulos(propuestaSeleccionada.getNulos() + 1);
                    }
                    else if (enBlanco.isChecked())
                    {
                        p.setBlancos(propuestaSeleccionada.getBlancos() + 1);
                    }

                    databaseReference.child("Propuesta").child(p.getUid()).setValue(p);

                    VotoRegistrado v = new VotoRegistrado();
                    v.setUid(UUID.randomUUID().toString());
                    v.setUidPersona(Almacenamiento.propuestaActual.getUid());
                    v.setUidPropuesta(Almacenamiento.logeadoLocal.getUid());
                    databaseReference.child("VotoRegistrado").child(v.getUid()).setValue(v);

                    Intent myIntent = new Intent(view.getContext(), VotarExitoActivity.class);
                    startActivityForResult(myIntent, 0);
                } catch (Exception e) {}
            }
        });
        //////////////////////////////////////////////////////////////////




        Button atras = (Button) findViewById(R.id.button43);
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), VotanteActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}
