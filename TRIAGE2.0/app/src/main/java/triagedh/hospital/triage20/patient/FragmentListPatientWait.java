package triagedh.hospital.triage20.patient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import triagedh.hospital.triage20.R;
import triagedh.hospital.triage20.model.IpWS;
import triagedh.hospital.triage20.model.Patient;

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


public class FragmentListPatientWait extends Fragment {

    private ArrayList<Patient> patients = new ArrayList<Patient>();
    public static int MILISECONDS_WAITING = 5000;
    public static int MILISECONDS_WAITING2 = 2000;
    private ProgressDialog progressDialog;
    private String answer = "";
    IpWS ip = new IpWS();
    View view;
    ListView lv;
    private ArrayList<String> data = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_patient, container, false);
        lv = (ListView) view.findViewById(R.id.listPatientWait);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Espere un momento...");
        progressDialog.show();
        colsultList(MILISECONDS_WAITING);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;

        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < patients.size(); i++) {
                        if (i == position) {
                            progressDialog.setMessage("Eperé un momento...");
                            progressDialog.show();
                            new actualizaEstatus().execute(patients.get(i).getProperty(0).toString());
                            answerUpdateStatus(MILISECONDS_WAITING2);
                        }
                    }
                }
            });
            mainViewholder.title.setText(getItem(position));
            return convertView;
        }
    }

    public class ViewHolder {
        TextView title;
        Button button;
    }

    /**
     * METHOD WAITING FOR THE RESPONSE OF THE WEB SERVICE answerUpdateEstatus
     *
     * @param milisegundos
     */
    public void answerUpdateStatus(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (answer.equals("exito")) {
                    progressDialog.dismiss();
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentListPatientWait()).commit();
                } else {
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getContext(), "Error... Intente de nuevo", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }

    /**
     * METHOD THAT CONSULTED A LIST OF PATIENT
     * @param milisegundos
     */
    public void colsultList(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                TaskWSConsulted consulted = new TaskWSConsulted();
                consulted.execute();
                registerForContextMenu(lv);
                progressDialog.dismiss();
            }
        }, milisegundos);
    }


    /**
     * METHOD THAT CONSUMES THE WEBSERVICE
     */
    private class TaskWSConsulted extends AsyncTask<String, Integer, Boolean> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "getAllPacienteAtendidos";
        final String URL = "http://"+ip.getIpWebService()+":8080/PacienteWSService/PacienteWS?xsd=1";
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
                        Patient patient = new Patient();
                        patient.setProperty(0, Integer.parseInt(objSoap.getProperty("id").toString()));
                        patient.setProperty(1, Integer.parseInt(objSoap.getProperty("persona_fk").toString()));
                        patient.setProperty(2, objSoap.getProperty("nombre").toString());
                        patient.setProperty(3, objSoap.getProperty("apellido_paterno").toString());
                        patient.setProperty(4, objSoap.getProperty("apellido_materno").toString());
                        patient.setProperty(5, Boolean.parseBoolean(objSoap.getProperty("estatus").toString()));
                        patients.add(patient);
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
                    Patient patient = new Patient();
                    patient.setProperty(0, Integer.parseInt(objSoap.getProperty("id").toString()));
                    patient.setProperty(1, Integer.parseInt(objSoap.getProperty("persona_fk").toString()));
                    patient.setProperty(2, objSoap.getProperty("nombre").toString());
                    patient.setProperty(3, objSoap.getProperty("apellido_paterno").toString());
                    patient.setProperty(4, objSoap.getProperty("apellido_materno").toString());
                    patient.setProperty(5, Boolean.parseBoolean(objSoap.getProperty("estatus").toString()));
                    patients.add(patient);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                final String[] datas = new String[patients.size()];
                for (int i = 0; i < patients.size(); i++) {
                    data.add( " "+patients.get(i).getProperty(0)+" "+patients.get(i).getProperty(2)
                            + " "+patients.get(i).getProperty(3)
                            +" "+patients.get(i).getProperty(4));
                    datas[i] = " "+patients.get(i).getProperty(2)
                            + " "+patients.get(i).getProperty(3)
                            +" "+patients.get(i).getProperty(4);

                }
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1, datas);
                lv.setAdapter(new MyListAdaper(getContext(), R.layout.list_item2, data));
            } else{
                Toast.makeText(getContext(), "No se encontraron datos.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * METHOD THAT USER INSERT THROUGH A WEB SERVICE CALLED insertUser
     */
    private class actualizaEstatus extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "actualizaEstatus";
        final String URL = "http://"+ip.getIpWebService()+":8080/PacienteWSService/PacienteWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("paciente_id", strings[0]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            Log.d("Transporte", request.toString());
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                answer = response.toString();
                Log.d("reps", response.toString());
            } catch (Exception e) {
                Log.d("exception ", e.getMessage());
                answer = e.getMessage();
            }
            return answer;
        }
    }



}
