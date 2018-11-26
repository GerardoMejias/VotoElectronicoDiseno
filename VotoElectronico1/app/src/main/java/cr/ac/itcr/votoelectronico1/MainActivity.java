package cr.ac.itcr.votoelectronico1;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.widget.Toast.*;
import static cr.ac.itcr.votoelectronico1.ConexionMetodos.conexionBDHost;

public class MainActivity extends AppCompatActivity {

    EditText correoEditText;
    EditText claveEditText;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private ArrayList<Persona> listPersona = new ArrayList<>();
    private ArrayList<Persona> listPersonaParaLogin = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Almacenamiento.insertarUsuario("Gerardo", "Mejias",
                "hgmejias@gmail.com", "1234", true);
        Almacenamiento.insertarUsuario("Daniel", "Rodrigues",
                "dgrj.tec@gmail.com", "5678", false);

        //Inicializar firebase
        inicializarFirebase();
        capturarPersonas();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //////////////LOGIN///////////////
        Button cargar = (Button) findViewById(R.id.button35);
        cargar.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correoEditText = (EditText) findViewById(R.id.editText);
                claveEditText = (EditText) findViewById(R.id.editText2);

                String correo = correoEditText.getText().toString();
                String clave = claveEditText.getText().toString();
                correoEditText.setText("");
                claveEditText.setText("");

                Persona p = validarPersona(correo, clave);
                if (p != null)
                {
                    if(!p.getConectado() && p.getActivo()) {
                        try {
                            //
                            //PONER A LA PERSONA COMO CONECTADO
                            Persona pNuevo = p;
                            pNuevo.setConectado(true);
                            databaseReference.child("Persona").child(pNuevo.getUid()).setValue(pNuevo);
                            Almacenamiento.logeadoLocal = p;
                            //
                            listPersona.clear();
                            if (p.getEsAdministrador()) {
                                Almacenamiento.usuarioActual = correo;
                                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                                startActivityForResult(myIntent, 0);
                            } else {
                                Almacenamiento.usuarioActual = correo;
                                Intent myIntent = new Intent(view.getContext(), VotanteActivity.class);
                                startActivityForResult(myIntent, 0);
                            }
                        } catch (Exception e) {

                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Usuario ya conectado", Toast.LENGTH_SHORT);
                    }
                }

                /*Usuario usuario = Almacenamiento.buscarUsuario(correo);
                if (usuario != null && usuario.getClave().equals(clave)) {
                    try {
                        if (usuario.getRol()) {
                            Almacenamiento.usuarioActual = correo;
                            Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                            startActivityForResult(myIntent, 0);
                        } else {
                            Almacenamiento.usuarioActual = correo;
                            Intent myIntent = new Intent(view.getContext(), VotanteActivity.class);
                            startActivityForResult(myIntent, 0);
                        }
                    } catch (Exception e) {

                    }
                }*/



            }
        });

    }

    private Persona validarPersona(String correo, String clave)
    {
        Persona p = null;
        for(int i = 0; i < listPersona.size(); i++)
        {
            if(correo.equals(listPersona.get(i).getCorreo()))
            {
                if(clave.equals(listPersona.get(i).getClave()))
                {
                    p = listPersona.get(i);
                    break;
                }
                else
                {
                    correoEditText.setError("Inválido");
                    claveEditText.setError("Inválido");
                    break;
                }
            }
        }
        if (p == null)
        {
            correoEditText.setText(correo);
            correoEditText.setError("Inválido");
        }
        return p;
    }

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
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }

    private void capturarPersonasParaLogin() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                listPersonaParaLogin.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren())
                {
                    Persona p = objSnapshot.getValue(Persona.class);
                    listPersonaParaLogin.add(p);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }

    public void inicializarFirebase()
    {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void insertarUsuario(String nombre, String apellidos, String correo, String clave, boolean rol)
    {
        //try {
            //firebaseDatabase = FirebaseDatabase.getInstance();
            //databaseReference = firebaseDatabase.getReference();
            inicializarFirebase();

            //Usuario usuarioNuevo = new Usuario(nombre, apellidos, correo, clave, rol);
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setNombre(nombre);
            usuarioNuevo.setApellidos(apellidos);
            usuarioNuevo.setCorreo(correo);
            usuarioNuevo.setClave(clave);
            usuarioNuevo.setRol(rol);
            usuarioNuevo.setUid(UUID.randomUUID().toString());

            //databaseReference.child("Usuario").child(usuarioNuevo.getCorreo()).setValue(usuarioNuevo);
            databaseReference.child("Usuario").child(usuarioNuevo.getUid()).setValue(usuarioNuevo);
            makeText(this, "Agregado", Toast.LENGTH_LONG).show();


            /*mAuth = FirebaseAuth.getInstance();

            DatabaseReference databaseUsuario;
            databaseUsuario = FirebaseDatabase.getInstance().getReference("Usuario");

            String id = databaseUsuario.push().getKey();
            Usuario usuarioNuevo = new Usuario(nombre, apellidos, correo, clave, rol);
            databaseUsuario.child(id).setValue(usuarioNuevo);*/



        //} catch (Exception e) {}
    }

    //CONEXION SQLSERVER
    /*public Connection conexionBD()
    {
        Connection conexion = null;


        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance(); //49706   //172.24.76.19
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://mssql1.gear.host;databaseName=votoelectronicdb;user=votoelectronicdb;password=Cb43TkB~6!DE");
            Toast.makeText(getApplicationContext(), "¡EXITO CONEXION!", Toast.LENGTH_SHORT).show();

        } catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return conexion;
    }*/

    /*public void agregarUsuario()
    {
        try
        {
            PreparedStatement pst = conexionBDHost().prepareStatement("INSERT INTO Usuario VALUES (?,?,?,?,?)");
            pst.setString(1, "Juan");
            pst.setString(2, "Perez");
            pst.setString(3, "@@@");
            pst.setString(4, "sdfsdf");
            pst.setInt(5, 1);

            pst.executeUpdate();
            Toast.makeText(getApplicationContext(), "¡EXITO!", Toast.LENGTH_SHORT).show();

        } catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
