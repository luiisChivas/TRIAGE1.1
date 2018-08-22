package triagedh.hospital.triage20.IncomeTab;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import triagedh.hospital.triage20.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class VitalSignsActivity extends AppCompatActivity {

    private String respuesta= "";
    private ProgressDialog progressDialog;
    public static int MILISEGUNDOS_ESPERA = 5000;
    private EditText edtTotal;
    private EditText edtAo;
    private EditText edtRv;
    private EditText edtRm;
    private Button btnSave;
    private EditText edtFr;
    private EditText edtFc;
    private EditText edtTem;
    private EditText edtTaSis;
    private EditText edtTaDi;

    CharSequence icharSequence [] = {"Espontanea", "Verbal", "Dolor", "No hay"};
    CharSequence icharSequenceResver [] = {"Coherente", "Desorientado", "Palabras Inapropiadas",
            "Ruido Incomprensible","No hay"};
    CharSequence icharSequenceResmo [] = {"Obedece Orden", "Localiza Orden", "Retira al Dolor",
            "Estiron inapropiado", "Extencion Inapropiada","No hay"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs);

        edtTotal = (EditText) findViewById(R.id.edtTotal);
        edtFc = (EditText) findViewById(R.id.edtFc);
        edtFr = (EditText)findViewById(R.id.edtFr);
        edtTem = (EditText)findViewById(R.id.edtTem);
        edtTaSis = (EditText) findViewById(R.id.edtTaSis);
        edtTaDi = (EditText)findViewById(R.id.edtTaDi);
        edtAo = (EditText) findViewById(R.id.edtAo);
        edtRv = (EditText) findViewById(R.id.edtRv);
        edtRm = (EditText) findViewById(R.id.edtRm);
        edtTotal = (EditText) findViewById(R.id.edtTotal);
        btnSave = (Button) findViewById(R.id.btnGuardarSignos);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new insertSignosVitales().execute(
                        edtFc.getText().toString().trim(),
                        edtFr.getText().toString().trim(),
                        edtTem.getText().toString().trim(),
                        edtTaSis.getText().toString().trim(),
                        edtTaDi.getText().toString().trim(),
                        edtAo.getText().toString().trim(),
                        edtRv.getText().toString().trim(),
                        edtRm.getText().toString().trim(),
                        edtTotal.getText().toString().trim());
                Intent intent = new Intent(VitalSignsActivity.this,  PainLevelActivity.class);
                startActivity(intent);
            }
        });
    }

    public void repuestaInsertaSignos(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
               /* if(respuesta.equals("exito")){
                    //limpiaCampos();
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Usuario insertado", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    progressDialog.dismiss();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error de insercción", Toast.LENGTH_SHORT);
                    toast1.show();
                }*/
            }
        }, milisegundos);
    }

    public void click(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VitalSignsActivity.this);
        //builder.setMessage("Mensaje de Alerta");
        builder.setTitle("Apertura Ocular");
        builder.setIcon(R.drawable.ojo);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(VitalSignsActivity.this, VitalSignsActivity.class));
            }
        });

        builder.setMultiChoiceItems(icharSequence, new boolean[icharSequence.length], new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                //Toast.makeText(MainActivity.this, icharSequence[i] + (b?"Checked":"Uncheck"),
                //      Toast.LENGTH_SHORT).show();
                if (icharSequence[i] == (b?"Espontanea":"Uncheck")){
                    edtAo.setText("4");
                }else if (icharSequence[i] == (b?"Verbal":"Uncheck")){
                    edtAo.setText("3");
                }else if (icharSequence[i] == (b?"Dolor":"Uncheck")){
                    edtAo.setText("2");
                }else if (icharSequence[i] == (b?"No hay":"Uncheck")){
                    edtAo.setText("1");
                }
                //edtAo.setText(icharSequence[i] + (b?"Checked":"Uncheck"));

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void verbalResponse(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VitalSignsActivity.this);
        builder.setTitle("Respuesta Verbal");
        builder.setIcon(R.drawable.boca);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(VitalSignsActivity.this, VitalSignsActivity.class));
            }
        });

        builder.setMultiChoiceItems(icharSequenceResver, new boolean[icharSequenceResver.length], new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (icharSequenceResver[i] == (b?"Coherente":"Uncheck")){
                    edtRv.setText("5");
                }else if (icharSequenceResver[i] == (b?"Desorientado":"Uncheck")){
                    edtRv.setText("4");
                }else if (icharSequenceResver[i] == (b?"Palabras Inapropiadas":"Uncheck")){
                    edtRv.setText("3");
                }else if (icharSequenceResver[i] == (b?"Ruido Incomprensible":"Uncheck")){
                    edtRv.setText("2");
                }else if (icharSequenceResver[i] == (b?"No hay":"Uncheck")){
                    edtRv.setText("1");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void motorResponse(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VitalSignsActivity.this);
        builder.setTitle("Respuesta Motora");
        builder.setIcon(R.drawable.dolor);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                calcular();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(VitalSignsActivity.this, VitalSignsActivity.class));
            }
        });

        builder.setMultiChoiceItems(icharSequenceResmo, new boolean[icharSequenceResmo.length], new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (icharSequenceResmo[i] == (b?"Obedece Orden":"Uncheck")){
                    edtRm.setText("6");
                }else if (icharSequenceResmo[i] == (b?"Localiza Orden":"Uncheck")){
                    edtRm.setText("5");
                }else if (icharSequenceResmo[i] == (b?"Retira al Dolor":"Uncheck")){
                    edtRm.setText("4");
                }else if (icharSequenceResmo[i] == (b?"Estiron Inapropiado":"Uncheck")){
                    edtRm.setText("3");
                }else if (icharSequenceResmo[i] == (b?"Extencion Inapropiada":"Uncheck")){
                    edtRm.setText("2");
                }else if (icharSequenceResmo[i] == (b?"No hay":"Uncheck")){
                    edtRm.setText("1");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void calcular() {
        String total =String.valueOf(Integer.parseInt(edtAo.getText().toString())+
                Integer.parseInt(edtRv.getText().toString())+
                Integer.parseInt(edtRm.getText().toString()));
        edtTotal.setText(total);
    }

    public void siguiente(View view) {
        Intent intent = new Intent(VitalSignsActivity.this, /*MainActivity*/VitalSignsActivity.class);
        startActivity(intent);
    }

    private class insertSignosVitales extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "insertSignosVitales";
        static final String URL = "http://192.168.43.39:8080/SignosVitalesWS/SignosVitalesWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("fc", strings[0]);
            request.addProperty("fr", strings[1]);
            request.addProperty("temperatura", strings[2]);
            request.addProperty("ta_sistolica", strings[3]);
            request.addProperty("ta_diastolica", strings[4]);
            request.addProperty("g_apertura_ocular", strings[5]);
            request.addProperty("g_respuesta_verval", strings[6]);
            request.addProperty("g_respuesta_motora", strings[7]);
            request.addProperty("g_total", strings[8]);

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
