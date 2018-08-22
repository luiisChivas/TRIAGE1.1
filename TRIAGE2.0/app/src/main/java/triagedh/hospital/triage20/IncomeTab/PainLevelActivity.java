package triagedh.hospital.triage20.IncomeTab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import triagedh.hospital.triage20.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class PainLevelActivity extends AppCompatActivity {

    public SeekBar seekBar;
    private TextView txtVal;
    private TextView textViewSeekBar;
    private ImageView image;
    private Button btnSig;
    private Button btnUpPac;

    private String respuesta = "";

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain_level);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textViewSeekBar = (TextView) findViewById(R.id.textView);
        textViewSeekBar.setText("0");
        txtVal = (TextView) findViewById(R.id.textVal);
        image = (ImageView) findViewById(R.id.imaged);
        // btnSig = (Button) findViewById(R.id.btnSig);



        btnUpPac = (Button) findViewById(R.id.btnUpPac);

        btnUpPac.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new updateFichaIngre().execute(
                        textViewSeekBar.getText().toString().trim(),
                        txtVal.getText().toString().trim());
                if(respuesta.equals("exito")){
                    //progressDialog.dismiss();
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Enfermedad Actualizado", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    //progressDialog.dismiss();
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Se Actualizo el Paciente", Toast.LENGTH_SHORT);

                    toast1.show();
                }

                startActivity(new Intent(PainLevelActivity.this, SearchDiseaseActivity.class));
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //la Seekbar siempre empieza en cero, si queremos que el valor mínimo sea otro podemos modificarlo
                textViewSeekBar.setText(progress + 0 + "");
                if(progress == 0) {
                    txtVal.setText(R.string.escalon0);
                    image.getResources().getDrawable(R.drawable.boca);
                }if(progress == 1){
                txtVal.setText(R.string.escalon1);
            }if(progress == 2){
                txtVal.setText(R.string.escalon1);
            }if(progress == 3){
                txtVal.setText(R.string.escalon3);
            }if(progress == 4){
                txtVal.setText(R.string.escalon4);
            }if(progress == 5){
                txtVal.setText(R.string.escalon5);
            }if(progress == 6){
                txtVal.setText(R.string.escalon6);
            }if(progress == 7){
                txtVal.setText(R.string.escalon7);
            }if(progress == 8){
                txtVal.setText(R.string.escalon8);
            }if(progress == 9){
                txtVal.setText(R.string.escalon9);
            }if(progress == 10){
                txtVal.setText(R.string.escalon10);
            }
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar arg0) { }
        });
    }

    private class updateFichaIngre extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "updateFichaIngre";
        static final String URL = "http://192.168.43.39:8080/FichaIngresoWS/FichaIngresoWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("nivel_dolor",strings[0]);
            request.addProperty("desc_dolor", strings[1]);
            //request.addProperty("paciente_id",strings[2]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            Log.d("Transporte", request.toString());

            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                respuesta = response.toString();
                Log.d("reps", response.toString());
            } catch (Exception e) {
                Log.d("exception ", e.getMessage());
                respuesta = e.getMessage();
            }
            return respuesta;
        }
    }
}
