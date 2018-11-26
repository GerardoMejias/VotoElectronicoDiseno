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
import android.widget.ListView;

import java.util.ArrayList;

public class ElimUsuarioListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elim_usuario_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button atras = (Button) findViewById(R.id.button27);
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        ArrayList<Usuario> listaUsuarios = ConexionMetodos.getListaU();
        int tamanoListaPropuestas = listaUsuarios.size();
        ArrayList<String> listaNombresPropuestas = new ArrayList<>();
        for(int i = 0; i < tamanoListaPropuestas; i++)
        {
            listaNombresPropuestas.add(listaUsuarios.get(i).getNombre());
        }

        ListView contenedor = (ListView) findViewById(R.id.listView130);
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listaNombresPropuestas);
        contenedor.setAdapter(adapter);

        Button eliminar = (Button) findViewById(R.id.button28);

        eliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ElimUsuarioExitoActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

}
