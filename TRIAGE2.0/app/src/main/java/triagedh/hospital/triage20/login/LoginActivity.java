package triagedh.hospital.triage20.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import triagedh.hospital.triage20.R;
import triagedh.hospital.triage20.menu.MainActivity;
import triagedh.hospital.triage20.model.IpWS;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class LoginActivity extends Activity {
    Button btnEnter;
    EditText edtUser,edtPassword;
    public static int MILISEGUNDOS_ESPERA = 5000;
    private String answer = "";
    private ProgressDialog progressDialog;
    IpWS ip = new IpWS();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        intentContent();
    }

    private void intentContent(){
        btnEnter = (Button)findViewById(R.id.btnEnter);
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        progressDialog = new ProgressDialog(LoginActivity.this);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validContent()){
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.show();
                    new consultUser().execute(edtUser.getText().toString().trim(),
                            edtPassword.getText().toString().trim());
                    answerConsultUser(MILISEGUNDOS_ESPERA);
                }
            }
        });

    }

    /**
     *Wait for a response from the user's query     *
     * @param milisegundos
     */
    public void answerConsultUser(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (answer.equals("exito")) {
                   progressDialog.dismiss();
                   cleanLogin();
                   Intent intent = new Intent(LoginActivity.this, /*MainActivity*/MainActivity.class);
                   startActivity(intent);
                   finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        }, milisegundos);
    }

    private boolean validContent(){
        if(edtUser.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar el usuario", Toast.LENGTH_SHORT).show();
            edtUser.setFocusable(true);
            return false;
        }

        if(edtPassword.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar la constraseña", Toast.LENGTH_SHORT).show();
            edtPassword.setFocusable(true);
            return false;
        }

        return true;
    }

    public void cleanLogin(){
        edtUser.setText("");
        edtPassword.setText("");
    }


    private class consultUser extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "consultUser";
        final String URL = "http://"+ip.getIpWebService()+":8080/UserWSService/UserWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("userName", strings[0]);
            request.addProperty("password", strings[1]);

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
