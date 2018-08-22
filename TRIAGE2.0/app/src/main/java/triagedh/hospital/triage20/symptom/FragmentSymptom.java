package triagedh.hospital.triage20.symptom;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import triagedh.hospital.triage20.R;
import triagedh.hospital.triage20.model.IpWS;
import triagedh.hospital.triage20.model.Symptom;

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


public class FragmentSymptom extends Fragment {

    private Button btnAgregar;
    private ArrayList<Symptom> symptoms = new ArrayList<Symptom>();
    public static int MILISECONDS_WAITING = 2000;
    private ProgressDialog progressDialog;
    ListView lData;
    IpWS ip = new IpWS();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_symptom, container, false);
        btnAgregar = (Button) view.findViewById(R.id.btnSymptomFrag);
        lData = (ListView) view.findViewById(R.id.listSymptom);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Espere un momento...");
        progressDialog.show();
        colsultaLista(MILISECONDS_WAITING);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SymptomActivity.class);
                i.putExtra("id", "");
                i.putExtra("disease", "");
                i.putExtra("level", "");
                i.putExtra("description", "");
                i.putExtra("btnSave", "1" );
                i.putExtra("btnDelete", "0" );
                i.putExtra("btnUpdate", "0" );
                startActivity(i);
            }
        });


        lData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int posicion = position;
                String itemValue = (String) lData.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(),SymptomActivity.class);
                for (int i = 0; i < symptoms.size(); i++) {
                    if(i == posicion){
                        String symptomsId =  "" + symptoms.get(i).getProperty(0);
                        String description = ""+ symptoms.get(i).getProperty(1);
                        String level = ""+ symptoms.get(i).getProperty(2);
                        String disease = ""+ symptoms.get(i).getProperty(3);
                        intent.putExtra("id", symptomsId);
                        intent.putExtra("disease", disease);
                        intent.putExtra("level", level);
                        intent.putExtra("description", description);
                        intent.putExtra("btnSave", "0" );
                        intent.putExtra("btnDelete", "1" );
                        intent.putExtra("btnUpdate", "1" );
                    }
                }
                startActivity(intent);
            }
        });

        return view;
    }

    public void colsultaLista(int milisegundos) {
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
        static final String METHOD_NAME = "getAllSintomas";
        final String URL = "http://"+ip.getIpWebService()+":8080/SintomaWSService/SintomaWS?xsd=1";
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
                        Symptom symptom = new Symptom();
                        symptom.setProperty(0, Integer.parseInt(objSoap.getProperty("sintoma_id").toString()));
                        symptom.setProperty(1, objSoap.getProperty("descripcion").toString());
                        symptom.setProperty(2, Integer.parseInt(objSoap.getProperty("nivel").toString()));
                        symptom.setProperty(3, Integer.parseInt(objSoap.getProperty("enfermedad").toString()));
                        symptoms.add(symptom);
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
                    symptom.setProperty(2, Integer.parseInt(objSoap.getProperty("nivel").toString()));
                    symptom.setProperty(3, Integer.parseInt(objSoap.getProperty("enfermedad").toString()));
                    symptoms.add(symptom);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                final String[] datas = new String[symptoms.size()];
                for (int i = 0; i < symptoms.size(); i++) {
                    datas[i] = " "+symptoms.get(i).getProperty(1);
                }
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1, datas);
                lData.setAdapter(adaptador);
            } else{
                Toast.makeText(getContext(), "No se encontraron datos.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


}
