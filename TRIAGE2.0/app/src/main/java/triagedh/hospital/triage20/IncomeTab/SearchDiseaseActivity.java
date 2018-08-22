package triagedh.hospital.triage20.IncomeTab;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import triagedh.hospital.triage20.R;
import triagedh.hospital.triage20.model.Disease;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class SearchDiseaseActivity extends ListActivity {

    private ArrayList<Disease> diseases = new ArrayList<Disease>();
    List<Integer> idlist;
    private String respuesta = "";
    public static int MILISEGUNDOS_ESPERA = 5000;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disease);

        //datos = (ListView) findViewById(R.id.listEn);

        progressDialog = new ProgressDialog(SearchDiseaseActivity.this);
        progressDialog.setMessage("Espere un momento...");
        progressDialog.show();
        colsultaLista(MILISEGUNDOS_ESPERA);
        //getListView().setOnItemClickListener(this);
        idlist= new ArrayList<>();
    }

    public void colsultaLista(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                TaskWSConsulted consulted = new TaskWSConsulted();
                consulted.execute();
                registerForContextMenu(getListView());
                progressDialog.dismiss();
            }
        }, milisegundos);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Bundle param = new Bundle();
        param.putInt("diseaseId", idlist.get(position));


        new updateFichaIngreEn().execute(
                String.valueOf(idlist.get(position))
        );


        Intent i = new Intent(SearchDiseaseActivity.this, SymptomDiseActivity.class);
        i.putExtras(param);
        startActivity(i);
    }

    private class TaskWSConsulted extends AsyncTask<String, Integer, Boolean> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "getAllDisease";
        static final String URL = "http://192.168.43.39:8080/DiseaseWSService/DiseaseWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        protected Boolean doInBackground(String... params) {

            boolean result = true;


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();
                if (response != null) {
                    for (SoapObject objSoap : response) {
                        Disease disease = new Disease();
                        disease.setProperty(0, Integer.parseInt(objSoap.getProperty("enfermedad_id").toString()));
                        disease.setProperty(1, objSoap.getProperty("descripcion").toString());
                        diseases.add(disease);
                        idlist.add(Integer.parseInt(objSoap.getProperty("enfermedad_id").toString()));
                    }
                }

            } catch (XmlPullParserException e) {
                Log.e("Error XMLPullParser", e.toString());
                result = false;
            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());

                result = false;
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
                result = false;
            } catch (ClassCastException e) {
                try {
                    SoapObject objSoap = (SoapObject) envelope.getResponse();
                    Disease disease = new Disease();
                    disease.setProperty(0, Integer.parseInt(objSoap.getProperty("enfermedad_id").toString()));
                    disease.setProperty(1, objSoap.getProperty("descripcion").toString());
                    diseases.add(disease);
                    idlist.add(Integer.parseInt(objSoap.getProperty("enfermedad_id").toString()));
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                final String[] datos = new String[diseases.size()];
                for (int i = 0; i < diseases.size(); i++) {
                    datos[i] = diseases.get(i).getProperty(0) + " - "
                            + diseases.get(i).getProperty(1);
                }
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        SearchDiseaseActivity.this,
                        android.R.layout.simple_list_item_1, datos);
                setListAdapter(adaptador);
            } else {
                Toast.makeText(getApplicationContext(), "No se encontraron datos.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class updateFichaIngreEn extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "updateFichaIngreEn";
        static final String URL = "http://192.168.43.39:8080/FichaIngresoWS/FichaIngresoWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("enfermedad_id",strings[0]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            Log.d("Transporte", request.toString());

            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                respuesta = response.toString();
                Log.d("reps", response.toString());
            } catch (Exception e) {
                Log.d("exception ", e.getMessage());
                respuesta = e.getMessage();
            }
            return respuesta;
        }
    }
}
