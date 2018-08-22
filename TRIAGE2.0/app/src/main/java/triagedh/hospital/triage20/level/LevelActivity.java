package triagedh.hospital.triage20.level;

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

public class LevelActivity extends AppCompatActivity {

    private EditText edtLevel;
    private EditText edtIdLevel;
    private TextView txtTittleLevel;
    private EditText edtDescription;
    private Button btnSave;
    private Button btnDelete;
    private Button btnUpdate;
    private String answer = "";
    public static int MILISECONDS_WAITING = 5000;
    private ProgressDialog progressDialog;
    IpWS ip = new IpWS();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        btnSave = (Button) findViewById(R.id.btnSaveLevel);
        btnUpdate = (Button) findViewById(R.id.btnUpdateLevel);
        btnDelete = (Button) findViewById(R.id.btnDeleteLevel);
        edtLevel = (EditText) findViewById(R.id.edtLevel);
        edtDescription = (EditText) findViewById(R.id.edtDecriptionLevel);
        edtIdLevel = (EditText) findViewById(R.id.edtIdLevel);
        txtTittleLevel = (TextView) findViewById(R.id.txtTittleLevel);
        edtIdLevel.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(this);
        receiveData();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Espere un momento...");
                progressDialog.show();
                new deleteLevel().execute(edtIdLevel.getText().toString().trim());
                answerDeleteLevel(MILISECONDS_WAITING);
                waitReturnFrament(MILISECONDS_WAITING);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validContent()) {
                    progressDialog.setMessage("Eperé un momento...");
                    progressDialog.show();
                    new insertLevel().execute(edtLevel.getText().toString().trim(),
                            edtDescription.getText().toString().trim());
                    answerInsertLevel(MILISECONDS_WAITING);
                    waitReturnFrament(MILISECONDS_WAITING);
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validContent()) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.show();
                    new updateLevel().execute(edtIdLevel.getText().toString().trim(),
                            edtLevel.getText().toString().trim(),
                            edtDescription.getText().toString().trim());
                    answerUpdateLevel(MILISECONDS_WAITING);
                    waitReturnFrament(MILISECONDS_WAITING);
                }
            }
        });
    }

    /**
     * METHOD THAT VALID THE CONTENT OF THE EDITTEXT
     * @return
     */
    private boolean validContent(){
        if(edtLevel.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar el nivel", Toast.LENGTH_SHORT).show();
            edtLevel.setFocusable(true);
            return false;
        }
        if(edtDescription.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar la descripción", Toast.LENGTH_SHORT).show();
            edtDescription.setFocusable(true);
            return false;
        }
        return true;
    }

    /**
     * METHOD WAITING A QUANTITY OF TIME IN WHICH THE ACTION THAT REQUIRES IT IS EXECUTED
     * FOR AFTER GOING TO THE LIST
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
        Intent intent = new Intent(LevelActivity.this, /*MainActivity*/MainActivity.class);
        intent.putExtra("fragment", "3");
        startActivity(intent);
    }

    /**
     *  THE METHOD RECEIVES THE DATA WHEN A LIST OF USERS 'LIST IS CLICKED
     */
    private void receiveData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String id = extras.getString("id");
            String name = extras.getString("name");
            String description = extras.getString("description");
            String btnGuardar = extras.getString("btnSave");
            if(btnGuardar.equals("0")){
                btnSave.setVisibility(View.GONE);
                edtIdLevel.setText(id);
                edtLevel.setText(name);
                edtDescription.setText(description);
                txtTittleLevel.setText(R.string.delete_update_level);
            }else{
                btnUpdate.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                txtTittleLevel.setText(R.string.add_level);
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
     */
    public void clean(){
        edtIdLevel.setText("");
        edtDescription.setText("");
        edtLevel.setText("");
    }


    /**
     * METHOD THAT COMPARE THE ANSWER THAT RETURN THE WEBSERVICE INSERT LEVEL
     * @param milisegundos
     */
    public void answerInsertLevel(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(answer.equals("exito")){
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"NIVEL INSERTADO", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"ERROR AL INSERTAR NIVEL", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }

    /**
     * METHOD THAT COMPARE THE ANSWER THAT RETURN THE WEBSERVICE DELETE LEVEL
     * @param milisegundos
     */
    public void answerDeleteLevel(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(answer.equals("exito")){
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"NIVEL ELIMINADO", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"ERROR AL ELIMINAR EL NIVEL", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }


    /**
     * METHOD THAT COMPARE THE ANSWER THAT RETURN THE WEBSERVICE UPDATE LEVEL
     * @param milisegundos
     */
    public void answerUpdateLevel(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(answer.equals("exito")){
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"NIVEL ACTUALIZADO", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"ERROR AL ACTUALIZAR EL NIVEL", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }



    /**
     * METHOD THAT CONSUMES THE WEBSERVICE INSERT LEVEL
     */
    private class insertLevel extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "insertLevel";
        final String URL = "http://"+ip.getIpWebService()+":8080/LevelWSService/LevelWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("nivel", strings[0]);
            request.addProperty("descripcion", strings[1]);
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
     * METHOD THAT CONSUMES THE WEBSERVICE DELETE LEVEL
     */
    private class deleteLevel extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "deleteLevel";
        final String URL = "http://"+ip.getIpWebService()+":8080/LevelWSService/LevelWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idLevel", strings[0]);
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
     * METHOD THAT CONSUMES THE WEBSERVICE UPDATE LEVEL
     */
    private class updateLevel extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "updatelevel";
        final String URL = "http://"+ip.getIpWebService()+":8080/LevelWSService/LevelWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("prioridad_id", strings[0]);
            request.addProperty("nivel", strings[1]);
            request.addProperty("descripcion", strings[2]);
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
