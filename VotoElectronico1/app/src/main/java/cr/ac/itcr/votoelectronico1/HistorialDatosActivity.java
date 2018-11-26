package cr.ac.itcr.votoelectronico1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HistorialDatosActivity extends AppCompatActivity {

    TextView titulo;
    TextView apruebanCant;
    TextView rechazanCant;
    TextView nuloCant;
    TextView enBlancoCant;
    TextView apruebanPorc;
    TextView rechazanPorc;
    TextView nuloPorc;
    TextView enBlancoPorc;

    Propuesta propuestaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_datos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        propuestaSeleccionada = Almacenamiento.propuestaActual;

        titulo = (TextView) findViewById(R.id.textView8);
        titulo.setText(propuestaSeleccionada.getTitulo());

        apruebanCant = (TextView) findViewById(R.id.textView15);
        rechazanCant = (TextView) findViewById(R.id.textView16);
        nuloCant = (TextView) findViewById(R.id.textView17);
        enBlancoCant = (TextView) findViewById(R.id.textView18);
        apruebanPorc = (TextView) findViewById(R.id.textView19);
        rechazanPorc = (TextView) findViewById(R.id.textView20);
        nuloPorc = (TextView) findViewById(R.id.textView21);
        enBlancoPorc = (TextView) findViewById(R.id.textView22);

        int apCant = propuestaSeleccionada.getAprueban();
        int reCant = propuestaSeleccionada.getRechazan();
        int nuCant = propuestaSeleccionada.getNulos();
        int ebCant = propuestaSeleccionada.getBlancos();
        int votosTotales = apCant+reCant+nuCant+ebCant;
        int apPorc = (apCant * 100) / votosTotales;
        int rePorc = (reCant * 100) / votosTotales;
        int nuPorc = (nuCant * 100) / votosTotales;
        int ebPorc = (ebCant * 100) / votosTotales;

        apruebanCant.setText(Integer.toString(apCant));
        rechazanCant.setText(Integer.toString(reCant));
        nuloCant.setText(Integer.toString(nuCant));
        enBlancoCant.setText(Integer.toString(ebCant));
        apruebanPorc.setText(Integer.toString(apPorc) + "%");
        rechazanPorc.setText(Integer.toString(rePorc) + "%");
        nuloPorc.setText(Integer.toString(nuPorc) + "%");
        enBlancoPorc.setText(Integer.toString(ebPorc) + "%");

        Button atras = (Button) findViewById(R.id.button29);
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), HistorialListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

}
