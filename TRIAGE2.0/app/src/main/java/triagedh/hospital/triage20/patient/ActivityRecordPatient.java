package triagedh.hospital.triage20.patient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import triagedh.hospital.triage20.R;
import triagedh.hospital.triage20.menu.MainActivity;
import triagedh.hospital.triage20.model.IpWS;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ActivityRecordPatient extends AppCompatActivity {
    private EditText patientId;
    private EditText name;
    private EditText lastName;
    private EditText secondLastName;
    private EditText disease;
    private EditText symptom;
    private EditText fc;
    private EditText fr;
    private EditText ocular;
    private EditText motorboat;
    private EditText verbal;
    private EditText total;
    private EditText sistolica;
    private EditText diastolica;
    private Button btnUpdate;
    private TextView observations;
    private String answer = "";
    IpWS ip = new IpWS();
    public static int MILISEGUNDOS_ESPERA = 5000;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_patient);

        patientId= (EditText) findViewById(R.id.edtPatientId);
        name  = (EditText) findViewById(R.id.edtPatientName);
        lastName = (EditText) findViewById(R.id.edtLastNamePatient);
        secondLastName = (EditText) findViewById(R.id.edtSecondLastNamePatient);
        disease = (EditText) findViewById(R.id.edtDiseasePatient);
        symptom = (EditText) findViewById(R.id.edtSymptomPatient);
        fc = (EditText) findViewById(R.id.edtFCPatient);
        fr = (EditText) findViewById(R.id.edtFRPatient);
        ocular = (EditText) findViewById(R.id.edtOcularPatient);
        motorboat = (EditText) findViewById(R.id.edtMotoraPatient);
        verbal = (EditText) findViewById(R.id.edtVerbalPatient);
        total = (EditText) findViewById(R.id.edtTotalPatient);
        sistolica = (EditText) findViewById(R.id.edtSistolicaPatient);
        diastolica = (EditText) findViewById(R.id.edtDiastolicaPatient);
        btnUpdate = (Button) findViewById(R.id.btnUpdatePatient);
        observations = (TextView) findViewById(R.id.edtObservationsPatient);
        patientId.setVisibility(View.GONE);

        receiveData();

        progressDialog = new ProgressDialog(this);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validContent()) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.show();
                    new updatePatient().execute(patientId.getText().toString().trim(),
                            observations.getText().toString().trim());
                    answerUpdatePatient(MILISEGUNDOS_ESPERA);
                    waitingReturnFrament(MILISEGUNDOS_ESPERA);
                }
            }
        });
    }

    private void receiveData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String id_paciente =  extras.getString("id");
            String nombrei = extras.getString("name");
            String paternoi = extras.getString("lasName");
            String maternoi =extras.getString("secondLastName");
            String enfermedadi = extras.getString("disease");
            String sintomai = extras.getString("symptom");
            String fci =extras.getString("fc");
            String fri =extras.getString("fr");
            String oculari = extras.getString("ocular");
            String motorai = extras.getString("motora");
            String verbali=extras.getString("verbal");
            String totali = extras.getString("total");
            String sistolicai = extras.getString("sistolica");
            String diastolicai = extras.getString("diastolica");
            String observationsP = extras.getString("observations");

            patientId.setText(id_paciente);
            name.setText(nombrei);
            lastName.setText(paternoi);
            secondLastName.setText(maternoi);
            disease.setText(enfermedadi);
            symptom.setText(sintomai);
            fc.setText(fci);
            fr.setText(fri);
            ocular.setText(oculari);
            motorboat.setText(motorai);
            verbal.setText(verbali);
            total.setText(totali);
            sistolica.setText(sistolicai);
            diastolica.setText(diastolicai);
            observations.setText(observationsP);
        }
    }


    private boolean validContent(){
        if(observations.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar las observaciones", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
     * THE METHOD RETURNS ME TO THE FRAGMENT CORRESPONDING LIST OF THE ACTIVITY IN USE
     */
    private void returnFragment() {
        Intent intent = new Intent(ActivityRecordPatient.this, /*MainActivity*/MainActivity.class);
        intent.putExtra("fragment", "5");
        // Aquí pasaremos el parámetro de la intención creada previamente
        startActivity(intent);
    }

    public void clean(){
        observations.setText("");
    }

    public void answerUpdatePatient(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                if(answer.equals("exito")){
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"PACIENTE ACTUALIZADO", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"ERROR AL ACTUALIZAR EL PACIENTE", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }


    /**
     *THE METHOD IMPLEMENTS THE RETURN ARROW TO THE PREVIOUS VIEW IN THE HEAD OF THE ACTIVITY
     */
    public void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



    private class updatePatient extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "actualizaPaciente";
        final String URL = "http://"+ip.getIpWebService()+":8080/HistorialPacWS/HistorialPacWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("paciente_id", strings[0]);
            request.addProperty("observaciones", strings[1]);
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
