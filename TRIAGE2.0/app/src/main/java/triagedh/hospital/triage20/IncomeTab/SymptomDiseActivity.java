package triagedh.hospital.triage20.IncomeTab;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import triagedh.hospital.triage20.menu.MainActivity;
import triagedh.hospital.triage20.model.Symptom;

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

public class SymptomDiseActivity extends ListActivity {

    private ArrayList<Symptom> symptoms = new ArrayList<Symptom>();
    List<Integer> idlistaSin;
    private String respuesta = "";
    public static int MILISEGUNDOS_ESPERA = 5000;
    public static int MILISEGUNDOS_ESPERANIVEL = 2000;
    private ProgressDialog progressDialog;
    private int idEnfermedad= -1;

    private String strNivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_dise);

        idEnfermedad=getIntent().getExtras().getInt("id_enfermedad");
        progressDialog = new ProgressDialog(SymptomDiseActivity.this);
        progressDialog.setMessage("Espere un momento...");
        progressDialog.show();
        idlistaSin= new ArrayList<>();
        colsultaLista(MILISEGUNDOS_ESPERA);
    }


    public void colsultaLista(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                TaskWSConsulted consulted = new TaskWSConsulted();
                consulted.execute();
                registerForContextMenu(getListView());
                progressDialog.dismiss();
            }
        }, milisegundos);
    }

    public void colsultaNivel(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SymptomDiseActivity.this);
                builder.setTitle(R.string.emergencylevel);

                strNivel = "URGENTE";
                builder.setMessage(strNivel);

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(SymptomDiseActivity.this, MainActivity.class));
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MainActivity.this, "Hola", Toast.LENGTH_SHORT);
                        Bundle parmetros = new Bundle();
                        parmetros.putInt("id_enfermedad", idEnfermedad);
                        startActivity(new Intent(SymptomDiseActivity.this, SymptomDiseActivity.class).putExtras(parmetros));
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
                System.out.println("1 El nivelTRIAGE=="+strNivel);
            }
        }, milisegundos);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        System.out.println("El seleccionado fk=="+idlistaSin.get(position));
        Bundle parmetros = new Bundle();
        parmetros.putInt("id_sintoma", idlistaSin.get(position));
        new updateFichaIngreSin().execute(
                String.valueOf(idlistaSin.get(position)));

        new loadLevel().execute(
                String.valueOf(idlistaSin.get(position)));

        colsultaNivel(MILISEGUNDOS_ESPERANIVEL);
    }

    private class TaskWSConsulted extends AsyncTask<String, Integer, Boolean> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "getAllSintoma";
        static final String URL = "http://192.168.43.39:8080/FichaIngresoWS/FichaIngresoWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            idEnfermedad = 5;
            request.addProperty("enfermedad_id", idEnfermedad);
            final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();
                if (response != null) {
                    for (SoapObject objSoap : response) {
                        Symptom symptom = new Symptom();
                        symptom.setProperty(0, Integer.parseInt(objSoap.getProperty("sintoma_id").toString()));
                        symptom.setProperty(1, objSoap.getProperty("descripcion").toString());
                        symptoms.add(symptom);
                        idlistaSin.add(Integer.parseInt(objSoap.getProperty("sintoma_id").toString()));
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
                    Symptom symptom = new Symptom();
                    symptom.setProperty(0, Integer.parseInt(objSoap.getProperty("sintoma_id").toString()));
                    symptom.setProperty(1, objSoap.getProperty("descripcion").toString());
                    symptoms.add(symptom);
                    idlistaSin.add(Integer.parseInt(objSoap.getProperty("sintoma_id").toString()));
                    Log.d("HOOOOOOOLALALA", "---------" + idlistaSin);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                final String[] datos = new String[symptoms.size()];
                for (int i = 0; i < symptoms.size(); i++) {
                    datos[i] = symptoms.get(i).getProperty(0) + " - "
                            + symptoms.get(i).getProperty(1);
                }
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        SymptomDiseActivity.this,
                        android.R.layout.simple_list_item_1, datos);
                setListAdapter(adaptador);
            } else {
                Toast.makeText(getApplicationContext(), "No se encontraron datos.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class updateFichaIngreSin extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "updateFichaIngreSin";
        static final String URL = "http://192.168.43.39:8080/FichaIngresoWS/FichaIngresoWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("sintoma_id",strings[0]);

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

    private class loadLevel extends AsyncTask<String, Integer, String> {

        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "loadLevel";
        static final String URL = "http://192.168.43.39:8080/SintomaWS/SintomaWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        protected String doInBackground(String... params) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id_sintoma",params[0]);
            String result = "";
            final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                strNivel = response.toString();
            } catch (Exception e) {
                Log.d("exception ", e.getMessage());
                strNivel = e.getMessage();
            }
            return strNivel;
        }
    }
}
