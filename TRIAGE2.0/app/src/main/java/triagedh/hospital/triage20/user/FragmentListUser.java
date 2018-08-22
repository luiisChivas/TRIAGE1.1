package triagedh.hospital.triage20.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import triagedh.hospital.triage20.R;
import triagedh.hospital.triage20.model.IpWS;
import triagedh.hospital.triage20.model.User;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


public class FragmentListUser extends Fragment {
    private Button btnAdd;
    private ArrayList<User> users = new ArrayList<User>();
    public static int MILISEGUNDOS_WAITING = 3000;
    private ProgressDialog progressDialog;
    IpWS ip = new IpWS();
    View view;
    ListView lData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_list_user, container, false);
        final FragmentManager fm=getActivity().getSupportFragmentManager();

        btnAdd = (Button) view.findViewById(R.id.btnAddUserFrag);
        lData = (ListView) view.findViewById(R.id.listUser);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Espere un momento...");
        progressDialog.show();
        colsultList(MILISEGUNDOS_WAITING);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // fm.beginTransaction().replace(R.id.contenedor_fragment, new FragmentUsuarios()).commit();
                Intent i = new Intent(getActivity(), UserActivity.class);
                i.putExtra("id", "");
                i.putExtra("user", "");
                i.putExtra("pass", "");
                i.putExtra("name", "");
                i.putExtra("lastName", "");
                i.putExtra("secondLastName", "");
                i.putExtra("curp", "");
                i.putExtra("btnSave", "1" );
                i.putExtra("btnDelete", "0" );
                i.putExtra("btnUpdate", "0" );
                startActivity(i);
            }
        });


        lData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int posicion = position;
                String itemValue = (String) lData.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(),UserActivity.class);
                for (int i = 0; i < users.size(); i++) {
                    if(i == posicion){
                        String userId =  "" + users.get(i).getProperty(0);
                        String userU = ""+ users.get(i).getProperty(1);
                        String passU = ""+ users.get(i).getProperty(2);
                        String nameU = ""+users.get(i).getProperty(5);
                        String lastNameU = ""+users.get(i).getProperty(6);
                        String secondLastNameU = ""+users.get(i).getProperty(7);
                        String curpU = "" + users.get(i).getProperty(8);
                        intent.putExtra("id", userId);
                        intent.putExtra("user", userU);
                        intent.putExtra("pass", passU);
                        intent.putExtra("name", nameU);
                        intent.putExtra("lastName", lastNameU);
                        intent.putExtra("secondLastName", secondLastNameU);
                        intent.putExtra("curp", curpU);
                        intent.putExtra("btnSave", "0" );
                        intent.putExtra("btnDelete", "1" );
                        intent.putExtra("btnUpdate", "1" );
                    }
                }
                startActivity(intent);
            }
        });

    return view;
    }


   public void colsultList(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                TaskWSConsulted consulted = new TaskWSConsulted();
                consulted.execute();
                registerForContextMenu(lData);
                progressDialog.dismiss();
            }
        }, milisegundos);
    }


    private class TaskWSConsulted extends AsyncTask<String, Integer, Boolean> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "getAllUsers";
        final String URL = "http://"+ip.getIpWebService()+":8080/UserWSService/UserWS?xsd=1";
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
                        User user = new User();
                        user.setProperty(0, Integer.parseInt(objSoap.getProperty("usuario_id").toString()));
                        user.setProperty(1, objSoap.getProperty("user").toString());
                        user.setProperty(2, objSoap.getProperty("pass").toString());
                        user.setProperty(3, objSoap.getProperty("status").toString());
                        user.setProperty(4, Integer.parseInt(objSoap.getProperty("persona_id").toString()));
                        user.setProperty(5, objSoap.getProperty("nombre").toString());
                        user.setProperty(6, objSoap.getProperty("apellido_paterno").toString());
                        user.setProperty(7, objSoap.getProperty("apellido_materno").toString());
                        user.setProperty(8, objSoap.getProperty("curp").toString());
                        users.add(user);
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
                    User user = new User();
                    user.setProperty(0, Integer.parseInt(objSoap.getProperty("usuario_id").toString()));
                    user.setProperty(1, objSoap.getProperty("user").toString());
                    user.setProperty(2, objSoap.getProperty("pass").toString());
                    user.setProperty(3, objSoap.getProperty("status").toString());
                    user.setProperty(4, Integer.parseInt(objSoap.getProperty("persona_id").toString()));
                    user.setProperty(5, objSoap.getProperty("nombre").toString());
                    user.setProperty(6, objSoap.getProperty("apellido_paterno").toString());
                    user.setProperty(7, objSoap.getProperty("apellido_materno").toString());
                    user.setProperty(8, objSoap.getProperty("curp").toString());
                    users.add(user);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                final String[] datos = new String[users.size()];
                for (int i = 0; i < users.size(); i++) {
                    datos[i] =  users.get(i).getProperty(5)+ " "
                            + users.get(i).getProperty(6)+ " "
                            + users.get(i).getProperty(7);
                }
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1, datos);
                lData.setAdapter(adaptador);
            } else {
                Toast.makeText(getContext(), "No se encontraron datos.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


}
