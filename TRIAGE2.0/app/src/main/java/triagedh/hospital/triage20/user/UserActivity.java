package triagedh.hospital.triage20.user;

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
import triagedh.hospital.triage20.model.User;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private Button btnSave;
    private Button btnDelete;
    private Button btnUpdate;
    private EditText edtNameUser;
    private EditText edtLastName;
    private EditText edtSecondLastName;
    private EditText edtCURP;
    private EditText edtUser;
    private EditText edtPassword;
    private String answer = "";
    private EditText edtIdUser;
    private TextView txtInfo;
    private ProgressDialog progressDialog;
    public static int MILISEGUNDOS_WAITING = 5000;
    private ArrayList<User> usuario = new ArrayList<User>();
    IpWS ip = new IpWS();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnSave = (Button) findViewById(R.id.btnSaveUser);
        btnUpdate = (Button) findViewById(R.id.btnUpdateUser);
        btnDelete = (Button) findViewById(R.id.btnDeleteUser);
        edtNameUser = (EditText) findViewById(R.id.edtNameUser);
        edtLastName = (EditText) findViewById(R.id.edtLastNameUser);
        edtSecondLastName = (EditText) findViewById(R.id.edtSecondLastNameUser);
        edtCURP = (EditText) findViewById(R.id.edtCURP);
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPasswordUser);
        edtIdUser = (EditText) findViewById(R.id.edtIDUser);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        edtIdUser.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(UserActivity.this);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Espere un momento...");
                progressDialog.show();
                new deleteUser().execute(edtIdUser.getText().toString().trim());
                answerDeleteUser(MILISEGUNDOS_WAITING);
                waitingReturnFrament(MILISEGUNDOS_WAITING);
            }
        });

        receiveData();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validContent()) {
                    progressDialog.setMessage("Epere un momento...");
                    progressDialog.show();
                    new insertUser().execute(edtNameUser.getText().toString().trim(),
                            edtLastName.getText().toString().trim(),
                            edtSecondLastName.getText().toString().trim(),
                            edtCURP.getText().toString().trim(),
                            edtUser.getText().toString().trim(),
                            edtPassword.getText().toString().trim());
                    answerInsertUsert(MILISEGUNDOS_WAITING);
                    waitingReturnFrament(MILISEGUNDOS_WAITING);
                }
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validContent()) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.show();
                    new updateUser().execute(edtNameUser.getText().toString().trim(),
                            edtLastName.getText().toString().trim(),
                            edtSecondLastName.getText().toString().trim(),
                            edtCURP.getText().toString().trim(),
                            edtUser.getText().toString().trim(),
                            edtPassword.getText().toString().trim(),
                            edtIdUser.getText().toString().trim());
                    answerUpdateUser(MILISEGUNDOS_WAITING);
                    waitingReturnFrament(MILISEGUNDOS_WAITING);
                }
            }
        });
    }//FIN DEL ONCREATE

    private boolean validContent(){
        if(edtUser.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar el usuario", Toast.LENGTH_SHORT).show();
            edtUser.setFocusable(true);
            return false;
        }

        if(edtPassword.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar la contraseña", Toast.LENGTH_SHORT).show();
            edtPassword.setFocusable(true);
            return false;
        }
        if(edtCURP.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar la CURP", Toast.LENGTH_SHORT).show();
            edtCURP.setFocusable(true);
            return false;
        }
        if(edtSecondLastName.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar la apellido materno", Toast.LENGTH_SHORT).show();
            edtSecondLastName.setFocusable(true);
            return false;
        }
        if(edtLastName.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar la apellido Paterno", Toast.LENGTH_SHORT).show();
            edtLastName.setFocusable(true);
            return false;
        }
        if(edtNameUser.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar el nombre del usuario", Toast.LENGTH_SHORT).show();
            edtNameUser.setFocusable(true);
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
        Intent intent = new Intent(UserActivity.this, /*MainActivity*/MainActivity.class);
        intent.putExtra("fragment", "1");
        startActivity(intent);
    }

    /**
     * THE METHOD RECEIVES THE DATA WHEN A LIST OF USERS 'LIST IS CLICKED
     */
    private void receiveData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String id = extras.getString("id");
            String userU = extras.getString("user");
            String passU = extras.getString("pass");
            String nameU = extras.getString("name");
            String lastNameU = extras.getString("lastName");
            String secondLastNameU = extras.getString("secondLastName");
            String curpU = extras.getString("curp");
            String btnSaveU = extras.getString("btnSave");
            if(btnSaveU.equals("0")){
                btnSave.setVisibility(View.GONE);
                edtIdUser.setText(id);
                edtUser.setText(userU);
                edtPassword.setText(passU);
                edtNameUser.setText(nameU);
                edtLastName.setText(lastNameU);
                edtSecondLastName.setText(secondLastNameU);
                edtCURP.setText(curpU);
                txtInfo.setText(R.string.delete_update_user);
            }else{
                btnUpdate.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                txtInfo.setText(R.string.add_user);
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
     * CLEAN THE TEXT BOXES WHEN A PERFORMANCE IS COMPLETED
     */
    public void clean() {
        edtNameUser.setText("");
        edtLastName.setText("");
        edtSecondLastName.setText("");
        edtCURP.setText("");
        edtUser.setText("");
        edtPassword.setText("");
        edtIdUser.setText("");
    }


    /**
     * METHOD WAITING FOR THE WEB SERVICE RESPONSE INSERT USER
     * @param milisegundos
     */
    public void answerInsertUsert(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (answer.equals("exito")) {
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Usuario insertado", Toast.LENGTH_SHORT);
                    toast1.show();
                } else {
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Error de insercción", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }

    /**
     * METHOD WAITING FOR THE WEB SERVICE RESPONSE UPDATES USER
     * @param milisegundos
     */
    public void answerDeleteUser(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                if(answer.equals("1")){
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Usuario Eliminado", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error al Eliminar el Usuario", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }


    /**
     * METHOD WAITING FOR THE WEB SERVICE RESPONSE UPDATES USER
     * @param milisegundos
     */
    public void answerUpdateUser(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(answer.equals("exito")){
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Usuario Actualizado", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error al Actualizar Usuario", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }


    /**
     * METHOD THAT USER INSERT THROUGH A WEB SERVICE CALLED INSERT USER
     */
    private class insertUser extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "insertUser";
        final String URL = "http://"+ip.getIpWebService()+":8080/UserWSService/UserWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("nombrePersona", strings[0]);
            request.addProperty("aPaterno", strings[1]);
            request.addProperty("aMaterno", strings[2]);
            request.addProperty("curp", strings[3]);
            request.addProperty("nomUser", strings[4]);
            request.addProperty("contrasena", strings[5]);
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
        private class deleteUser extends AsyncTask<String, String, String> {
            static final String NAMESPACE = "http://webservice.ws.utng.com/";
            static final String METHOD_NAME = "deleteUser";
            final String URL = "http://"+ip.getIpWebService()+":8080/UserWSService/UserWS?xsd=1";
            static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

            @Override
            protected String doInBackground(String... strings) {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("idUser", strings[0]);
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
    private class updateUser extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "updateUser";
        final String URL = "http://"+ip.getIpWebService()+":8080/UserWSService/UserWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("nombrePersona", strings[0]);
            request.addProperty("aPaterno", strings[1]);
            request.addProperty("aMaterno", strings[2]);
            request.addProperty("curp", strings[3]);
            request.addProperty("nombreUsuario", strings[4]);
            request.addProperty("contrasena", strings[5]);
            request.addProperty("usuarioId",strings[6]);

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

