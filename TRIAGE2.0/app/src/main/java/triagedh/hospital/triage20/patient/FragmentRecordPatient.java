package triagedh.hospital.triage20.patient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import triagedh.hospital.triage20.R;
import triagedh.hospital.triage20.model.IpWS;
import triagedh.hospital.triage20.model.Record;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


public class FragmentRecordPatient extends Fragment {

    private ArrayList<Record> records = new ArrayList<Record>();
    public static int MILISEGUNDOS_ESPERA = 3000;
    private ProgressDialog progressDialog;
    IpWS ip = new IpWS();
    ListView lData;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_record_patient, container, false);
            lData = (ListView) view.findViewById(R.id.listRecordPatients);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Espere un momento...");
        progressDialog.show();
        colsultList(MILISEGUNDOS_ESPERA);

        lData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int posicion = position;
                String itemValue = (String) lData.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(),ActivityRecordPatient.class);
                for (int i = 0; i < records.size(); i++) {
                    if(i == posicion){
                        String idPatient =  "" + records.get(i).getProperty(0);
                        String nameP = ""+ records.get(i).getProperty(1);
                        String lastNameP = ""+ records.get(i).getProperty(2);
                        String secondLastNameP = ""+ records.get(i).getProperty(3);
                        String diseaseP = ""+ records.get(i).getProperty(4);
                        String symptomP = ""+ records.get(i).getProperty(5);
                        String fc = ""+ records.get(i).getProperty(6);
                        String fr = ""+ records.get(i).getProperty(7);
                        String ocular = ""+ records.get(i).getProperty(8);
                        String motora = ""+ records.get(i).getProperty(9);
                        String verbal = ""+ records.get(i).getProperty(10);
                        String total = ""+ records.get(i).getProperty(11);
                        String sistolica = ""+ records.get(i).getProperty(12);
                        String diastolica = ""+ records.get(i).getProperty(13);
                        String observationsP = ""+ records.get(i).getProperty(14);
                        intent.putExtra("id", idPatient);
                        intent.putExtra("name", nameP);
                        intent.putExtra("lasName", lastNameP);
                        intent.putExtra("secondLastName", secondLastNameP);
                        intent.putExtra("disease", diseaseP);
                        intent.putExtra("symptom", symptomP);
                        intent.putExtra("fc", fc);
                        intent.putExtra("fr", fr);
                        intent.putExtra("ocular", ocular);
                        intent.putExtra("motora", motora);
                        intent.putExtra("verbal", verbal);
                        intent.putExtra("total", total);
                        intent.putExtra("sistolica", sistolica);
                        intent.putExtra("diastolica", diastolica);
                        intent.putExtra("observations", observationsP);
                    }
                }
                startActivity(intent);
            }
        });

        return view;
    }

    public void colsultList(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                TaskWSConsulted consulted = new TaskWSConsulted();
                consulted.execute();
                registerForContextMenu(lData);
                progressDialog.dismiss();
            }
        }, milisegundos);
    }


    private class TaskWSConsulted extends AsyncTask<String, Integer, Boolean> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "getAllHistorial";
        final String URL = "http://"+ip.getIpWebService()+":8080/HistorialPacWS/HistorialPacWS?xsd=1";
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
                        Record record = new Record();
                        record.setProperty(0, Integer.parseInt(objSoap.getProperty("patientId").toString()));
                        record.setProperty(1, objSoap.getProperty("name").toString());
                        record.setProperty(2, objSoap.getProperty("lastName").toString());
                        record.setProperty(3, objSoap.getProperty("secondLastName").toString());
                        record.setProperty(4, objSoap.getProperty("disease").toString());
                        record.setProperty(5, objSoap.getProperty("symptom").toString());
                        record.setProperty(6, Integer.parseInt(objSoap.getProperty("fc").toString()));
                        record.setProperty(7, Integer.parseInt(objSoap.getProperty("fr").toString()));
                        record.setProperty(8, Integer.parseInt(objSoap.getProperty("g_apertura_ocular").toString()));
                        record.setProperty(9, Integer.parseInt(objSoap.getProperty("g_respuesta_motora").toString()));
                        record.setProperty(10, Integer.parseInt(objSoap.getProperty("g_respuesta_verbal").toString()));
                        record.setProperty(11, Integer.parseInt(objSoap.getProperty("g_total").toString()));
                        record.setProperty(12, Integer.parseInt(objSoap.getProperty("ta_sistolica").toString()));
                        record.setProperty(13, Integer.parseInt(objSoap.getProperty("ta_diastolica").toString()));
                        records.add(record);
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
                    Record record = new Record();
                    record.setProperty(0, Integer.parseInt(objSoap.getProperty("patientId").toString()));
                    record.setProperty(1, objSoap.getProperty("name").toString());
                    record.setProperty(2, objSoap.getProperty("lastName").toString());
                    record.setProperty(3, objSoap.getProperty("secondLastName").toString());
                    record.setProperty(4, objSoap.getProperty("disease").toString());
                    record.setProperty(5, objSoap.getProperty("symptom").toString());
                    record.setProperty(6, Integer.parseInt(objSoap.getProperty("fc").toString()));
                    record.setProperty(7, Integer.parseInt(objSoap.getProperty("fr").toString()));
                    record.setProperty(8, Integer.parseInt(objSoap.getProperty("g_apertura_ocular").toString()));
                    record.setProperty(9, Integer.parseInt(objSoap.getProperty("g_respuesta_motora").toString()));
                    record.setProperty(10, Integer.parseInt(objSoap.getProperty("g_respuesta_verbal").toString()));
                    record.setProperty(11, Integer.parseInt(objSoap.getProperty("g_total").toString()));
                    record.setProperty(12, Integer.parseInt(objSoap.getProperty("ta_sistolica").toString()));
                    record.setProperty(13, Integer.parseInt(objSoap.getProperty("ta_diastolica").toString()));
                    records.add(record);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                final String[] datos = new String[records.size()];
                for (int i = 0; i < records.size(); i++) {
                    datos[i] = records.get(i).getProperty(0) + " "
                            + records.get(i).getProperty(1) +  " "
                            + records.get(i).getProperty(2) + " ";
                }
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1, datos);
                lData.setAdapter(adaptador);
            } else {
                Toast.makeText(getContext(), "No se encontraron datos.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
