package triagedh.hospital.triage20.disease;

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
import triagedh.hospital.triage20.model.Disease;
import triagedh.hospital.triage20.model.IpWS;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class DiseaseActivity extends AppCompatActivity {

    private EditText edtNameDisease;
    private EditText edtIdDisease;
    private Button btnSave;
    private Button btnDelete;
    private Button btnUpdate;
    private String answer = "";
    private TextView txtTittleDisease;
    private ProgressDialog progressDialog;
    public static int MILISECONDS_WAITING = 5000;
    private ArrayList<Disease> diseases = new ArrayList<Disease>();
    IpWS ip = new IpWS();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
        edtNameDisease = (EditText) findViewById(R.id.edtNameDisease);
        btnSave = (Button) findViewById(R.id.btnSaveDisease);
        progressDialog = new ProgressDialog(this);
        edtIdDisease = (EditText) findViewById(R.id.edtIdDisease);
        btnDelete = (Button) findViewById(R.id.btnDeleteDisiease);
        btnUpdate = (Button) findViewById(R.id.btnUpdateDisease);
        txtTittleDisease = (TextView) findViewById(R.id.txtTittleDisease);
        edtIdDisease.setVisibility(View.GONE);
        receiveData();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Espere un momento...");
                progressDialog.show();
                new deleteDisease().execute(edtIdDisease.getText().toString().trim());
                answerDeleteDisease(MILISECONDS_WAITING);
                waitReturnFrament(MILISECONDS_WAITING);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validContent()) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.show();
                    new insertDisease().execute(edtNameDisease.getText().toString().trim());
                    answerInsertDesease(MILISECONDS_WAITING);
                    waitReturnFrament(MILISECONDS_WAITING);
                }
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validContent()) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.show();
                    new updateDisease().execute(edtNameDisease.getText().toString().trim(),
                            edtIdDisease.getText().toString().trim());
                    answerUpdateDisease(MILISECONDS_WAITING);
                    waitReturnFrament(MILISECONDS_WAITING);
                }
            }
        });
    }


    /**
     * METHOD THAT VALID THE CONTENT OF THE EDITTEXT
     *
     * @return
     */
    private boolean validContent() {
        if (edtNameDisease.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Falta ingresar el nivel", Toast.LENGTH_SHORT).show();
            edtNameDisease.setFocusable(true);
            return false;
        }
        return true;
    }

    /**
     * METHOD WAITING A QUANTITY OF TIME IN WHICH THE ACTION THAT REQUIRES IT IS EXECUTED
     * FOR AFTER GOING TO THE LIST
     *
     * @param milisegundos
     */
    public void waitReturnFrament(int milisegundos) {
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
        Intent intent = new Intent(DiseaseActivity.this, /*MainActivity*/MainActivity.class);
        intent.putExtra("fragment", "4");
        startActivity(intent);
    }

    /**
     * THE METHOD RECEIVES THE DATA WHEN A LIST OF USERS 'LIST IS CLICKED
     */
    private void receiveData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("id");
            String name = extras.getString("name");
            String btnSaveD = extras.getString("btnSave");
            if (btnSaveD.equals("0")) {
                btnSave.setVisibility(View.GONE);
                edtIdDisease.setText(id);
                edtNameDisease.setText(name);
                txtTittleDisease.setText(R.string.update_delete_diseas);
            } else {
                btnUpdate.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                txtTittleDisease.setText(R.string.add_diseas);
            }
        }
    }


    /**
     * THE METHOD IMPLEMENTS THE RETURN ARROW TO THE PREVIOUS VIEW IN THE HEAD OF THE ACTIVITY
     */
    public void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * METHOD THAT CLEAN EDITEXT
     **/
    public void clean() {
        edtNameDisease.setText("");
        edtIdDisease.setText("");
    }

    /**
     * Method to wait for the action of each button to be performed
     **/
    public void answerDeleteDisease(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (answer.equals("exito")) {
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "ENFERMEDAD ELIMINADA", Toast.LENGTH_SHORT);
                    toast1.show();
                } else {
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "ERROR AL ELIMINAR LA ENFFERMEDAD", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }


    /**
     * METHOD THAT WAIT THE ANSWER OF THE WEBSERVICE
     *
     * @param milisegundos
     */
    public void answerInsertDesease(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (answer.equals("exito")) {
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "ENFERMEDAD INSERTADA", Toast.LENGTH_SHORT);
                    toast1.show();
                } else {
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "ERROR AL INSERTAR LA ENFERMEDAD", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }

    /**
     * METHOD THAT WAIT THE ANSWER OF THE WEBSERVICE
     *
     * @param milisegundos
     */
    public void answerUpdateDisease(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (answer.equals("exito")) {
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "ENFERMEDAD ACTUALIZADA", Toast.LENGTH_SHORT);
                    toast1.show();
                } else {
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "ERROR AL ACTUALIZAR LA ENFERMEDAD", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }

    /**
     * METHOD THAT INSERT LEVELS
     */
    private class insertDisease extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "insertDisease";
        final String URL = "http://" + ip.getIpWebService() + ":8080/DiseaseWSService/DiseaseWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("descripcion", strings[0]);
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
     * METHOD THAT DELETE DISEASE
     */
    private class deleteDisease extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "deleteDisease";
        final String URL = "http://" + ip.getIpWebService() + ":8080/DiseaseWSService/DiseaseWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idDisease", strings[0]);

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
     * METHOD THAT UPDATE DISEASE
     */
    private class updateDisease extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "updateDisease";
        final String URL = "http://"+ip.getIpWebService()+":8080/DiseaseWSService/DiseaseWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("descripcion", strings[0]);
            request.addProperty("enfermedadId",strings[1]);
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
