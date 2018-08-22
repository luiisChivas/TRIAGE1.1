package triagedh.hospital.triage20.symptom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import triagedh.hospital.triage20.R;
import triagedh.hospital.triage20.menu.MainActivity;
import triagedh.hospital.triage20.model.Disease;
import triagedh.hospital.triage20.model.IpWS;
import triagedh.hospital.triage20.model.Level;

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


public class SymptomActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    IpWS ip = new IpWS();
    private TextView txtTittleSymptom;
    private Button btnSave;
    private Button btnUpdate;
    private Button btnDelete;
    private EditText edtSymptom;
    private EditText edtIdSymptom;
    private int idDiseaseSelected;
    private int idLevelSelected;
    private String answer = "";
    private Spinner spnDisease;
    Spinner spnLevel;
    List<String> listDesease;
    List<Integer> listIdDisease;
    ArrayAdapter<String> comboAdapterDisease;
    List<String> listLevel;
    List<Integer> listIdLevel;
    ArrayAdapter<String> comboAdapterLevel;
    private ProgressDialog progressDialog;
    public static int MILISEGUNDOS_WAITING = 5000;
    private ArrayList<Level> levels = new ArrayList<Level>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom);
        btnSave = (Button) findViewById(R.id.btnSaveSymptom);
        btnUpdate = (Button) findViewById(R.id.btnUpdateSymptom);
        btnDelete = (Button) findViewById(R.id.btnDeleteSymptom);
        edtSymptom = (EditText) findViewById(R.id.edtSymptom);
        spnDisease = (Spinner) findViewById(R.id.spnIdDisease);
        spnLevel =(Spinner) findViewById(R.id.spnIdLevel);
        edtIdSymptom = (EditText) findViewById(R.id.edtIdSymptom);
        edtIdSymptom.setVisibility(View.GONE);
        txtTittleSymptom = (TextView) findViewById(R.id.txtTittleSymptom);
        receiveData();
        progressDialog = new ProgressDialog(SymptomActivity.this);

        // LOAD COMBO DISEASE
        spnDisease.setOnItemSelectedListener(this);
        listDesease = new ArrayList<>();
        listIdDisease = new ArrayList<>();
        listDesease.add("Selecciona");
        loadState loadState = new loadState();
        loadState.execute();
        comboAdapterDisease = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, listDesease);
        spnDisease.setAdapter(comboAdapterDisease);
        spnDisease.setSelection(0);

        //LOAD COMBO LEVEL
        spnLevel.setOnItemSelectedListener(this);
        listLevel = new ArrayList<>();
        listIdLevel = new ArrayList<>();
        listLevel.add("Selecciona");
        loadLevel loadLevel = new loadLevel();
        loadLevel.execute();
        comboAdapterLevel = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, listLevel);
        spnLevel.setAdapter(comboAdapterLevel);
        spnLevel.setSelection(0);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validContent()) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.show();
                    new insertSymptom().execute(
                            edtSymptom.getText().toString().trim(),
                            String.valueOf(idLevelSelected),
                            String.valueOf(idDiseaseSelected));
                    answerInsertSymptom(MILISEGUNDOS_WAITING);
                    waitingReturnFrament(MILISEGUNDOS_WAITING);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.show();
                    new deleteSymptom().execute(edtIdSymptom.getText().toString().trim());
                    answerDeleteSymptom(MILISEGUNDOS_WAITING);
                    waitingReturnFrament(MILISEGUNDOS_WAITING);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validContent()) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.show();
                    new updateSymptom().execute(edtIdSymptom.getText().toString().trim(),
                            edtSymptom.getText().toString().trim(),
                            String.valueOf(idLevelSelected),
                            String.valueOf(idDiseaseSelected));
                    answerUpdateSymptom(MILISEGUNDOS_WAITING);
                    waitingReturnFrament(MILISEGUNDOS_WAITING);
                }
            }
        });

    }

    public void clean() {
        edtSymptom.setText("");
    }

    private boolean validContent(){
        if(edtSymptom.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar el sintoma", Toast.LENGTH_SHORT).show();
            edtSymptom.setFocusable(true);
            return false;
        }
        if (spnDisease.getSelectedItem().toString().trim().equals("Selecciona")) {
            Toast.makeText(getApplicationContext(), "Falta seleccionar la enfermedad", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spnLevel.getSelectedItem().toString().trim().equals("Selecciona")) {
            Toast.makeText(getApplicationContext(), "Falta seleccionar el nivel", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * THE METHOD RETURNS ME TO THE FRAGMENT CORRESPONDING LIST OF THE ACTIVITY IN USE
     */
    private void returnFragment() {
        Intent intent = new Intent(SymptomActivity.this, /*MainActivity*/MainActivity.class);
        intent.putExtra("fragment", "6");
        startActivity(intent);
    }

    /**
     * METHOD WAITING A QUANTITY OF TIME IN WHICH THE ACTION THAT REQUIRES IT IS EXECUTED
     * FOR AFTER GOING TO THE LIST
     * @param milisegundos
     */
    public void waitingReturnFrament(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                returnFragment();
            }
        }, milisegundos);
    }


    /**
     * METHOD WAITING FOR THE WEB SERVICE RESPONSE UPDATES USER
     * @param milisegundos
     */
    public void answerDeleteSymptom(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(answer.equals("exito")){
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Symptom Eliminado", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error al Eliminar el Symptom", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }

    /**
     * METHOD WAITING FOR THE WEB SERVICE RESPONSE UPDATES USER
     * @param milisegundos
     */
    public void answerUpdateSymptom(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(answer.equals("exito")){
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Symptom actualizado", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error al actualizado el Symptom", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }

    /**
     * METHOD WAITING FOR THE WEB SERVICE RESPONSE UPDATES USER
     * @param milisegundos
     */
    public void answerInsertSymptom(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(answer.equals("exito")){
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Symptom insertado", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error al insertado el symptom", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }

    private void receiveData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String id = extras.getString("id");
            String description = extras.getString("description");
            String level = extras.getString("level");
            String disease = extras.getString("disease");
            String btnSaveS = extras.getString("btnSave");

            if(btnSaveS.equals("0")){
                btnSave.setVisibility(View.GONE);
                edtIdSymptom.setText(id);
                edtSymptom.setText(description);
                //spnDisease.setSelection(0);
                spnLevel.setSelection(Integer.parseInt(level));
                txtTittleSymptom.setText(R.string.update_delete_symptom);
            }else{
                btnUpdate.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                txtTittleSymptom.setText(R.string.add_symptom);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position >= 1){
            switch (parent.getId()){
                case R.id.spnIdLevel:
                    idLevelSelected = listIdLevel.get(position - 1);
                    break;
                case R.id.spnIdDisease:
                    idDiseaseSelected = listIdDisease.get(position -1);
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class loadState extends AsyncTask<String, Integer, Boolean> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "loadEnfermedadSintoma";
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
                        Disease disease = new Disease();
                        disease.setProperty(0, Integer.parseInt(objSoap.getProperty("enfermedad_id").toString()));
                        disease.setProperty(1, objSoap.getProperty("descripcion").toString());
                        listDesease.add(disease.getDescription());
                        listIdDisease.add(disease.getDiseaseId());
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
                    listDesease.add(disease.getDescription());
                    listIdDisease.add(disease.getDiseaseId());
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }
    }


    private class loadLevel extends AsyncTask<String, Integer, Boolean> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "loadNivelSintoma";
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
                        Level level = new Level();
                        level.setProperty(0, Integer.parseInt(objSoap.getProperty("nivel_id").toString()));
                        level.setProperty(2, String.valueOf(objSoap.getProperty(1).toString()));
                        listLevel.add(level.getDescription());
                        listIdLevel.add(level.getLevelId());
                        levels.add(level);
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
                    Level level = new Level();
                    level.setProperty(0, Integer.parseInt(objSoap.getProperty("nivel_id").toString()));
                    level.setProperty(2, String.valueOf(objSoap.getProperty(1).toString()));
                    listLevel.add(level.getDescription());
                    listIdLevel.add(level.getLevelId());
                    levels.add(level);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }

    }

    private class insertSymptom extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "insertSintoma";
        final String URL = "http://"+ip.getIpWebService()+":8080/SintomaWSService/SintomaWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("descripcion", strings[0]);
            request.addProperty("nivel_id_fk", strings[1]);
            request.addProperty("enfermedad_id_fk", strings[2]);
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

    /**
     * METHOD THAT ELIMINATES A USER THROUGH THE WEB SERVICE CALLING DELETE USER
     *
     */
    private class deleteSymptom extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "deleteSintoma";
        final String URL = "http://"+ip.getIpWebService()+":8080/SintomaWSService/SintomaWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idSintoma", strings[0]);
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


    /**
     * METHOD QUR UPDATES A USER THROUGH THE WEB SERVICE CALLING UPDATE USER
     */
    private class updateSymptom extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "updateSintoma";
        final String URL = "http://"+ip.getIpWebService()+":8080/SintomaWSService/SintomaWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("sintoma_id", strings[0]);
            request.addProperty("descripcion", strings[1]);
            request.addProperty("nivel_id_fk", strings[2]);
            request.addProperty("enfermedad_id_fk", strings[3]);

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