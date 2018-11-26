package cr.ac.itcr.votoelectronico1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ParticipacionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participacion_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Propuesta> listaPropuestas = ConexionMetodos.getPropuestas();
        int tamanoListaPropuestas = listaPropuestas.size();
        ArrayList<String> listaNombresPropuestas = new ArrayList<>();
        for(int i = 0; i < tamanoListaPropuestas; i++)
        {
            //listaNombresPropuestas.add(listaPropuestas.get(i).titulo);
        }

        ListView contenedor = (ListView) findViewById(R.id.listView25);
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listaNombresPropuestas);
        contenedor.setAdapter(adapter);


        Button atras = (Button) findViewById(R.id.button37);
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AdminActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        ListView lista = (ListView) findViewById(R.id.listView25);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(view.getContext(), ParticipacionDatosActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

}
