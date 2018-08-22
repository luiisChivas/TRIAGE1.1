package triagedh.hospital.triage20.IncomeTab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import triagedh.hospital.triage20.R;
import triagedh.hospital.triage20.model.Municipality;
import triagedh.hospital.triage20.model.Sex;
import triagedh.hospital.triage20.model.State;
import triagedh.hospital.triage20.model.TypeBlood;

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

public class IncomeTabActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private String respuesta= "";
    private ProgressDialog progressDialog;
    public static int MILISEGUNDOS_ESPERA = 5000;
    private EditText edtNameFile;
    private EditText edtLastNameFile;
    private EditText edtSecondNameFile;
    private EditText edtCurp;
    private EditText edtBirthDateFile;
    private EditText edtAgeFile;
    private EditText edtSafeNumFile;
    private EditText edtAllergyFile;
    private EditText edtDescriptionFile;
    private EditText edtColonyFile;
    private EditText edtStreetFile;
    private EditText edtAddressNumberFile;
    private EditText edtPhoneFile;

    ///CheckBox

    //
    private int idStateSel = -1;
    private int idMunicipalitySel = -1;
    private int idTypeBlood = -1;
    private int idSex = -1;

    //Spinner
    private Spinner spnStateFile;
    private Spinner spnMunicipalityFile;
    private Spinner spnTypeBloodFile;
    private Spinner spnSex;
    private Spinner spnAlergico;

    private Button btnNextFile;
    //ArrayAdapter<Estado> comboAdapter;
    private List<State> ests= null;

    //private ArrayList<Estado> enfermedades = new ArrayList<Estado>();

    //Spinner spnSate;
    List<String> listState;
    List<Integer> idlistState;
    ArrayAdapter<String> comboAdapter;

    //Spinner spnMuniciplity;
    List<String> listMunicipality;
    List<Integer> idlistMunicipality;
    ArrayAdapter<String> comboAdapterMuni;

    //SpnTypeBlood
    List<String> listTypeBlood;
    List<Integer> idlistTypeBlood;
    ArrayAdapter<String> comboAdapterTypeBlood;

    //btnSex
    List<String> listSex;
    List<Integer> idlistSex;
    ArrayAdapter<String> comboAdapterSex;
   // String nombreSexo;

    private boolean sleccionTime= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_tab);

        edtNameFile = (EditText)findViewById(R.id.edtNameFile);
        edtLastNameFile = (EditText)findViewById(R.id.edtLastNameFile);
        edtSecondNameFile = (EditText)findViewById(R.id.edtSecondNameFile);
        edtCurp = (EditText)findViewById(R.id.edtCurp);
        edtBirthDateFile = (EditText)findViewById(R.id.edtBirthDateFile);
        edtAgeFile = (EditText)findViewById(R.id.edtAgeFile);
        edtSafeNumFile = (EditText)findViewById(R.id.edtSafeNumFile);
        edtAllergyFile = (EditText)findViewById(R.id.edtAllergyFile);
        edtDescriptionFile = (EditText)findViewById(R.id.edtDescriptionFile);
        edtColonyFile = (EditText)findViewById(R.id.edtColonyFile);
        edtStreetFile = (EditText)findViewById(R.id.edtStreetFile);
        edtAddressNumberFile = (EditText)findViewById(R.id.edtAddressNumberFile);
        edtPhoneFile = (EditText)findViewById(R.id.edtPhoneFile);

        spnStateFile =(Spinner) findViewById(R.id.spnStateFile);
        spnMunicipalityFile =(Spinner) findViewById(R.id.spnMunicipalityFile);
        spnTypeBloodFile =(Spinner) findViewById(R.id.spnTypeBloodFile);
        spnSex = (Spinner) findViewById(R.id.spnSex);
        //btnSave = (Button)findViewById(R.id.btnGuardarFicaIngre);
        btnNextFile = (Button) findViewById(R.id.btnNextFile);


        //================Datos cargados desde Array Para el Estado=====================//
        spnStateFile = (Spinner) findViewById(R.id.spnStateFile);
        spnStateFile.setOnItemSelectedListener(this);
        listState = new ArrayList<>();
        idlistState = new ArrayList<>();
        listState.add("Selecciona");
        loadState loadState = new loadState();
        loadState.execute();
        comboAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, listState);
        //Cargo el spinner con los datos
        spnStateFile.setAdapter(comboAdapter);
        spnStateFile.setSelection(0);
        // Log.d("Mensaje id", ""+objSoap.getProperty("estado_id").toString());
        Log.d("Tamano ID", "" + idlistState.size());
        Log.d("Tamano", "" + listState.size());

        //================Datos cargados desde Array Para el Municipio=====================//
        spnMunicipalityFile = (Spinner) findViewById(R.id.spnMunicipalityFile);
        spnMunicipalityFile.setOnItemSelectedListener(this);
        listMunicipality = new ArrayList<>();
        idlistMunicipality = new ArrayList<>();
        listMunicipality.add("Selecciona ...");
        comboAdapterMuni = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, listMunicipality);
        //Cargo el spinner con los datos
        spnMunicipalityFile.setAdapter(comboAdapterMuni);
        spnMunicipalityFile.setSelection(0);
        Log.d("Tamano ID", "" + idlistMunicipality.size());
        Log.d("Tamano", "" + listMunicipality.size());

        //================Datos cargados desde Array Para el Tipo Sangre=====================//
        spnTypeBloodFile = (Spinner) findViewById(R.id.spnTypeBloodFile);
        spnTypeBloodFile.setOnItemSelectedListener(this);
        listTypeBlood = new ArrayList<>();
        idlistTypeBlood = new ArrayList<>();
        listTypeBlood.add("Selecciona...");
        loadTipoSangre loadTipoSangre = new loadTipoSangre();
        loadTipoSangre.execute();
        comboAdapterTypeBlood = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, listTypeBlood);
        //Cargo el spinner con los datos
        spnTypeBloodFile.setAdapter(comboAdapterTypeBlood);
        spnTypeBloodFile.setSelection(0);
        Log.d("Tamano ID", "" + idlistTypeBlood.size());
        Log.d("Tamano", "" + listTypeBlood.size());


        //================Datos cargados desde Array Para el Sexo=====================//
        spnSex = (Spinner) findViewById(R.id.spnSex);
        spnSex.setOnItemSelectedListener(this);
        listSex = new ArrayList<>();
        idlistSex = new ArrayList<>();
        listSex.add("Selecciona...");
        loadSexo loadSexo = new loadSexo();
        loadSexo.execute();
        comboAdapterSex = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, listSex);
        //Cargo el spinner con los datos
        spnSex.setAdapter(comboAdapterSex);
        spnSex.setSelection(0);
        Log.d("Tamano ID", "" + idlistSex.size());
        Log.d("Tamano", "" + listSex.size());

        btnNextFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new insertFichaIngreso().execute(
                        edtNameFile.getText().toString().trim(),
                        edtLastNameFile.getText().toString().trim(),
                        edtSecondNameFile.getText().toString().trim(),
                        edtCurp.getText().toString().trim(),
                        edtSafeNumFile.getText().toString().trim(),
                        edtBirthDateFile.getText().toString().trim(),
                        edtAgeFile.getText().toString().trim(),
                        edtAllergyFile.getText().toString().toUpperCase().trim().equals("SI")?"true":"false",
                        edtDescriptionFile.getText().toString().trim(),
                        edtPhoneFile.getText().toString().trim(),
                        edtColonyFile.getText().toString().trim(),
                        edtStreetFile.getText().toString().trim(),
                        edtAddressNumberFile.getText().toString().trim(),
                        String.valueOf(idTypeBlood), //Este es para el ek Spinner
                        String.valueOf(idMunicipalitySel),
                        String.valueOf(idSex));

                Intent intent = new Intent(IncomeTabActivity.this, VitalSignsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position >= 1) {
            switch (parent.getId()) {
                case R.id.spnStateFile:
                    idStateSel = idlistState.get(position - 1);
                    loadMunicipio loadMunicipio = new loadMunicipio();
                    loadMunicipio.execute();
                    listMunicipality.clear();
                    listMunicipality.add("Selecciona");
                    comboAdapterMuni = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listMunicipality);
                    spnMunicipalityFile.setAdapter(comboAdapterMuni);
                    spnMunicipalityFile.setSelection(0);
                    Log.d("Tamano ID", "" + idlistMunicipality.size());
                    Log.d("Tamano", "" + listMunicipality.size());
                    break;
                case R.id.spnTypeBloodFile:
                    idTypeBlood = idlistTypeBlood.get(position - 1);
                    break;
                case R.id.spnMunicipalityFile:
                    idMunicipalitySel = idlistMunicipality.get(position - 1);
                    break;
                case R.id.spnSex:
                    idSex = idlistSex.get(position - 1);
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class insertFichaIngreso extends AsyncTask<String, String, String> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "insertFichaIngreso";
        static final String URL = "http://192.168.43.39:8080/FichaIngresoWS/FichaIngresoWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("nombrePersona", strings[0]);
            request.addProperty("aPaterno", strings[1]);
            request.addProperty("aMaterno", strings[2]);
            request.addProperty("curp", strings[3]);
            request.addProperty("numero_seguro", strings[4]);
            request.addProperty("fecha_nacimiento", strings[5]);
            request.addProperty("edad", strings[6]);
            request.addProperty("alergico", strings[7]);
            request.addProperty("descripcion_alergia", strings[8]);
            request.addProperty("numero_telefono", strings[9]);
            request.addProperty("colonia", strings[10]);
            request.addProperty("calle", strings[11]);
            request.addProperty("numero", strings[12]);
            request.addProperty("tipo_sangre_id_fk", strings[13]);//No hice un Spinner
            request.addProperty("municipio_id_fk", strings[14]);
            request.addProperty("sexo_id_fk", strings[15]);


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

    private class loadState extends AsyncTask<String, Integer, Boolean> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "loadState";
        static final String URL = "http://192.168.43.39:8080/FichaIngresoWS/FichaIngresoWS?xsd=1";
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
                        State state = new State();
                        state.setProperty(0, Integer.parseInt(objSoap.getProperty("estado_id").toString()));
                        state.setProperty(1, objSoap.getProperty("descripcion").toString());
                        listState.add(state.getDescription());
                        idlistState.add(state.getStateId());
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
                    State state = new State();
                    state.setProperty(0, Integer.parseInt(objSoap.getProperty("estado_id").toString()));
                    state.setProperty(1, objSoap.getProperty("descripcion").toString());
                    listState.add(state.getDescription());
                    idlistState.add(state.getStateId());
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }
    }

    private class loadMunicipio extends AsyncTask<String, Integer, Boolean> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "loadMunicipio";
        static final String URL = "http://192.168.43.39:8080/FichaIngresoWS/FichaIngresoWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        protected Boolean doInBackground(String... params) {

            boolean result = true;


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("estado_id", idStateSel);
            final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();
                if (response != null) {
                    for (SoapObject objSoap : response) {
                        Municipality municipality = new Municipality();
                        municipality.setProperty(0, Integer.parseInt(objSoap.getProperty("municipio_id").toString()));
                        municipality.setProperty(1, objSoap.getProperty("descripcion").toString());
                        listMunicipality.add(municipality.getDescripcion());
                        idlistMunicipality.add(municipality.getMunicipalityId());
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
                    Municipality municipality = new Municipality();
                    municipality.setProperty(0, Integer.parseInt(objSoap.getProperty("municipio_id").toString()));
                    municipality.setProperty(1, objSoap.getProperty("descripcion").toString());
                    listMunicipality.add(municipality.getDescripcion());
                    idlistMunicipality.add(municipality.getMunicipalityId());
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }
    }

    private class loadTipoSangre extends AsyncTask<String, Integer, Boolean> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "loadTipoSangre";
        static final String URL = "http://192.168.43.39:8080/FichaIngresoWS/FichaIngresoWS?xsd=1";
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
                        TypeBlood typeBlood = new TypeBlood();
                        typeBlood.setProperty(0, Integer.parseInt(objSoap.getProperty("tipo_sangre_id").toString()));
                        typeBlood.setProperty(1, objSoap.getProperty("descripcion").toString());
                        listTypeBlood.add(typeBlood.getDescripcion());
                        idlistTypeBlood.add(typeBlood.getTypeBloodId());
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
                    TypeBlood typeBlood = new TypeBlood();
                    typeBlood.setProperty(0, Integer.parseInt(objSoap.getProperty("tipo_sangre_id").toString()));
                    typeBlood.setProperty(1, objSoap.getProperty("descripcion").toString());
                    listTypeBlood.add(typeBlood.getDescripcion());
                    idlistTypeBlood.add(typeBlood.getTypeBloodId());
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }
    }

    private class loadSexo extends AsyncTask<String, Integer, Boolean> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "loadSexo";
        static final String URL = "http://192.168.43.39:8080/FichaIngresoWS/FichaIngresoWS?xsd=1";
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
                        Sex sex = new Sex();
                        sex.setProperty(0, Integer.parseInt(objSoap.getProperty("sexo_id").toString()));
                        sex.setProperty(1, objSoap.getProperty("descripcion").toString());
                        listSex.add(sex.getDescription());
                        idlistSex.add(sex.getSexId());
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
                    Sex sex = new Sex();
                    sex.setProperty(0, Integer.parseInt(objSoap.getProperty("sexo_id").toString()));
                    sex.setProperty(1, objSoap.getProperty("descripcion").toString());
                    listSex.add(sex.getDescription());
                    idlistSex.add(sex.getSexId());
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }
    }
}
