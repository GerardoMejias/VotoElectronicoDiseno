package cr.ac.itcr.votoelectronico1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class ResultadosDatosActivity extends AppCompatActivity {

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

    PieChart pieChart;

    //private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
    //private String[] xData = {"Mitch", "Jessica", "Mohammad", "Kelsey", "Gam", "Robert", "Ashley"};

    private int[] yData;// = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
    private int[] yDataPorc;// = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
    private String[] xData = {"Aprueban", "Rechazan", "Nulos", "En blanco"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_datos);
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

        Button atras = (Button) findViewById(R.id.button39);
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ResultadosListActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        yData = new int[]{apCant, reCant, nuCant, ebCant};
        yDataPorc = new int[]{apPorc, rePorc, nuPorc, ebPorc};

        pieChart = (PieChart) findViewById(R.id.idPieChart1);

        pieChart.setDescription("Cantidades y Porcentajes de Votos");
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);

        if ((apCant + reCant + nuCant + ebCant) > 0) {
            addDataSet();
        }

        /*pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                int pos1 = e.toString().indexOf("(sum): ");
                String votos = e.toString().substring(pos1 + 7);

                for (int i = 0; i < yData.length; i++) {
                    if(yData[i] == Float.parseFloat(votos)) {
                        pos1 = i;
                        break;
                    }
                }

                String opcion = xData[pos1 + 1];
                Toast.makeText(ResultadosDatosActivity.this, votos, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected() {

            }
        });*/

    }

    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++)
        {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for (int i = 0; i < yData.length; i++)
        {
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Votos");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }

}
