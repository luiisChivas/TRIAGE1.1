package triagedh.hospital.triage20.legend;

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
import triagedh.hospital.triage20.model.Legend;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class LegendActivity extends AppCompatActivity {

    private Button btnSave;
    private Button btnDelete;
    private Button btnUpdate;
    private EditText edtName;
    private EditText edtDescription;
    public static int MILISEGUNDOS_WAITING= 5000;
    private String answer = "";
    private EditText edtIdLegend;
    private ProgressDialog progressDialog;
    private ArrayList<Legend> legends = new ArrayList<Legend>();
    IpWS ip = new IpWS();
    private TextView txtTittleLegend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legends);

        btnSave = (Button) findViewById(R.id.btnSaveLegend);
        btnUpdate = (Button) findViewById(R.id.btnUpdateLegend);
        btnDelete = (Button) findViewById(R.id.btnDeleteLegend);
        edtName = (EditText) findViewById(R.id.edtNameLegend);
        edtDescription = (EditText) findViewById(R.id.edtDescriptionLegend);
        edtIdLegend = (EditText) findViewById(R.id.edtIdLegend);
        txtTittleLegend = (TextView) findViewById(R.id.txtTittleLegend);
        progressDialog = new ProgressDialog(LegendActivity.this);
        edtIdLegend.setVisibility(View.GONE);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Espere un momento...");
                progressDialog.show();
                new deleteLegend().execute(edtIdLegend.getText().toString().trim());
                answerDeleteLegend(MILISEGUNDOS_WAITING);
                waitReturnFragment(MILISEGUNDOS_WAITING);
            }
        });
        receiveData();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validContent()) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.show();
                    new insertLegend().execute(edtName.getText().toString().trim(),
                            edtDescription.getText().toString().trim());
                    answerInsertLegend(MILISEGUNDOS_WAITING);
                    waitReturnFragment(MILISEGUNDOS_WAITING);
                }
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validContent()){
                progressDialog.setMessage("Espere un momento...");
                progressDialog.show();
                new updateLegend().execute(edtIdLegend.getText().toString().trim(),
                        edtName.getText().toString().trim(),
                        edtDescription.getText().toString().trim());
                answerUpdateLegend(MILISEGUNDOS_WAITING);
                    waitReturnFragment(MILISEGUNDOS_WAITING);
                }
            }
        });
    }

    /**
     * METHOD THAT VALID THE CONTENT
     * @return
     */
    private boolean validContent(){
        if(edtName.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Falta ingresar el nombre", Toast.LENGTH_SHORT).show();
            edtName.setFocusable(true);
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
     * FOR AFTER GOING TO THE LIST
     * @param milisegundos
     */
    public void waitReturnFragment(int milisegundos) {
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
        Intent intent = new Intent(LegendActivity.this, /*MainActivity*/MainActivity.class);
        intent.putExtra("fragment", "2");
        startActivity(intent);
    }

    /**
     *  THE METHOD RECEIVES THE DATA WHEN A LIST OF USERS 'LIST IS CLICKED
     */
    private void receiveData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String idL = extras.getString("id");
            String nameL = extras.getString("name");
            String descriptionL = extras.getString("description");
            String btnSaveL = extras.getString("btnSave");
            if(btnSaveL.equals("0")){
                btnSave.setVisibility(View.GONE);
                edtIdLegend.setText(idL);
                edtName.setText(nameL);
                edtDescription.setText(descriptionL);
                txtTittleLegend.setText(R.string.delete_update_legend);
            }else{
                btnUpdate.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                txtTittleLegend.setText(R.string.add_legend);
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
     * METHOD FOR CLEAN DE EDITTEXT
     */
    public void clean(){
        edtName.setText("");
        edtDescription.setText("");
        edtIdLegend.setText("");
    }


    /**
     * METHOD THAT WAIT FOR COMPARE THE ANSWER OF THE WEBSERVICE
     * @param milisegundos
     */
    public void answerInsertLegend(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (answer.equals("exito")) {
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Leyenda Insertada", Toast.LENGTH_SHORT);
                    toast1.show();
                } else {
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Error de Insercción", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }


    /**
     * Method that is executed after a while to compare the response of the webservice Update Legend
     * @param milisegundos
     */
    public void answerUpdateLegend(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (answer.equals("exito")) {
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Leyenda Actualizada", Toast.LENGTH_SHORT);
                    toast1.show();
                } else {
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Error al Actualizar la Leyenda", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }

    /**
     * Method that is executed after a while to compare the response of the webservice delete Legend
     * @param milisegundos
     */
    public void answerDeleteLegend(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (answer.equals("exito")) {
                    clean();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Leyenda Eliminada", Toast.LENGTH_SHORT);
                    toast1.show();
                } else {
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error al Eliminar la Leyenda", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        }, milisegundos);
    }


    /**
     *METHOD THAT INSERT LEGEND
     */
    private class insertLegend extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "insertLeyenda";
        final String URL = "http://"+ip.getIpWebService()+":8080/LeyendaWSService/LeyendaWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("nombre", strings[0]);
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
         * METHOD FOR DELETE A LEGEND AND RETURNS 1 IN THE CORRECT CASE
         */
        private class deleteLegend extends AsyncTask<String, String, String> {
            static final String NAMESPACE = "http://webservice.ws.utng.com/";
            static final String METHOD_NAME = "deleteLeyenda";
            final String URL = "http://" + ip.getIpWebService() + ":8080/LeyendaWSService/LeyendaWS?xsd=1";
            static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
            @Override
            protected String doInBackground(String... strings) {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("leyenda_id", strings[0]);
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
     * METHOD THAT UPDATE LEGEND
     */
    private class updateLegend extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "updateLeyenda";
        final String URL = "http://"+ip.getIpWebService()+":8080/LeyendaWSService/LeyendaWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("leyenda_id", strings[0]);
            request.addProperty("nombre", strings[1]);
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

