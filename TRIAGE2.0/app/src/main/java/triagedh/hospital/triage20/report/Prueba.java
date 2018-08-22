package triagedh.hospital.triage20.report;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import triagedh.hospital.triage20.R;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import harmony.java.awt.Color;
import triagedh.hospital.triage20.model.IpWS;
import triagedh.hospital.triage20.model.Report;


public class Prueba extends ListActivity {
    private Button btnAgregar;
    private ArrayList<Report> usuarios = new ArrayList<Report>();
    public static int MILISEGUNDOS_ESPERA = 3000;
    private ProgressDialog progressDialog;
    IpWS ip = new IpWS();
    private Button generar;
    private String result;
    private final static String NOMBRE_DIRECTORIO = "MiPdf";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";
    Button btnGenerar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_triage);
        generar = (Button) findViewById(R.id.btnGenerar);
        progressDialog = new ProgressDialog(Prueba.this);
        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarPdf();
               /* progressDialog.setMessage("Espere un momento...");
                progressDialog.show();
                colsultaLista(MILISEGUNDOS_ESPERA);*/
            }
        });
    }


    public void colsultaLista(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                String id = "1";
                new TaskWSConsulted().execute(id);
                registerForContextMenu(getListView());
                progressDialog.dismiss();
            }
        }, milisegundos);
    }

    private class TaskWSConsulted extends AsyncTask<String, Integer, Boolean> {
        static final String NAMESPACE = "http://webservice.ws.utng.com/";
        static final String METHOD_NAME = "getResultTriage";
        final String URL = "http://"+ip.getIpWebService()+":8080/resultTriageWSService/resultTriageWS?xsd=1";
        static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        protected Boolean doInBackground(String... params) {
            boolean result = true;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            //request.addProperty("id", params[0]);
            final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();
                if (response != null) {
                    for (SoapObject objSoap : response) {
                        Report report = new Report();
                        report.setProperty(0, objSoap.getProperty("nombre").toString());
                        report.setProperty(1, objSoap.getProperty("apellido_paterno").toString());
                        report.setProperty(2, objSoap.getProperty("apellido_materno").toString());
                        report.setProperty(3, objSoap.getProperty("sexo").toString());
                        report.setProperty(4, objSoap.getProperty("fecha_nacimiento").toString());
                        report.setProperty(5, objSoap.getProperty("descripcion_alergia").toString());
                        report.setProperty(6, objSoap.getProperty("curp").toString());
                        report.setProperty(7, objSoap.getProperty("numero_seguro").toString());
                        report.setProperty(8, objSoap.getProperty("calle").toString());
                        report.setProperty(9, Integer.parseInt(objSoap.getProperty("numero").toString()));
                        report.setProperty(10, objSoap.getProperty("colonia").toString());
                        report.setProperty(11, objSoap.getProperty("municipio").toString());
                        report.setProperty(12, objSoap.getProperty("numero_telefono").toString());
                        report.setProperty(13, objSoap.getProperty("enfermedad").toString());
                        report.setProperty(14, objSoap.getProperty("sintoma").toString());
                        report.setProperty(15, objSoap.getProperty("desc_dolor").toString());
                        report.setProperty(16, Integer.parseInt(objSoap.getProperty("fc").toString()));
                        report.setProperty(17, Integer.parseInt(objSoap.getProperty("fr").toString()));
                        report.setProperty(18, Integer.parseInt(objSoap.getProperty("temperatura").toString()));
                        report.setProperty(19, Integer.parseInt(objSoap.getProperty("ta_sistolica").toString()));
                        report.setProperty(20, Integer.parseInt(objSoap.getProperty("ta_diastolica").toString()));
                        report.setProperty(21, Integer.parseInt(objSoap.getProperty("g_apertura_ocular").toString()));
                        report.setProperty(22, Integer.parseInt(objSoap.getProperty("g_respuesta_motora").toString()));
                        report.setProperty(23, Integer.parseInt(objSoap.getProperty("g_respuesta_verbal").toString()));
                        report.setProperty(24, Integer.parseInt(objSoap.getProperty("g_total").toString()));
                        report.setProperty(25, objSoap.getProperty("estado").toString());
                        report.setProperty(26, objSoap.getProperty("tipo_sangre").toString());
                        report.setProperty(27, Integer.parseInt(objSoap.getProperty("nivel_dolor").toString()));
                        usuarios.add(report);
                        Log.d("GENERA DATOS*****",  "" + report);

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
                    Report report = new Report();
                    report.setProperty(0, objSoap.getProperty("nombre").toString());
                    report.setProperty(1, objSoap.getProperty("apellido_paterno").toString());
                    report.setProperty(2, objSoap.getProperty("apellido_materno").toString());
                    report.setProperty(3, objSoap.getProperty("sexo").toString());
                    report.setProperty(4, objSoap.getProperty("fecha_nacimiento").toString());
                    report.setProperty(5, objSoap.getProperty("descripcion_alergia").toString());
                    report.setProperty(6, objSoap.getProperty("curp").toString());
                    report.setProperty(7, objSoap.getProperty("numero_seguro").toString());
                    report.setProperty(8, objSoap.getProperty("calle").toString());
                    report.setProperty(9, Integer.parseInt(objSoap.getProperty("numero").toString()));
                    report.setProperty(10, objSoap.getProperty("colonia").toString());
                    report.setProperty(11, objSoap.getProperty("municipio").toString());
                    report.setProperty(12, objSoap.getProperty("numero_telefono").toString());
                    report.setProperty(13, objSoap.getProperty("enfermedad").toString());
                    report.setProperty(14, objSoap.getProperty("sintoma").toString());
                    report.setProperty(15, objSoap.getProperty("desc_dolor").toString());
                    report.setProperty(16, Integer.parseInt(objSoap.getProperty("fc").toString()));
                    report.setProperty(17, Integer.parseInt(objSoap.getProperty("fr").toString()));
                    report.setProperty(18, Integer.parseInt(objSoap.getProperty("temperatura").toString()));
                    report.setProperty(19, Integer.parseInt(objSoap.getProperty("ta_sistolica").toString()));
                    report.setProperty(20, Integer.parseInt(objSoap.getProperty("ta_diastolica").toString()));
                    report.setProperty(21, Integer.parseInt(objSoap.getProperty("g_apertura_ocular").toString()));
                    report.setProperty(22, Integer.parseInt(objSoap.getProperty("g_respuesta_motora").toString()));
                    report.setProperty(23, Integer.parseInt(objSoap.getProperty("g_respuesta_verbal").toString()));
                    report.setProperty(24, Integer.parseInt(objSoap.getProperty("g_total").toString()));
                    report.setProperty(25, objSoap.getProperty("estado").toString());
                    report.setProperty(26, objSoap.getProperty("tipo_sangre").toString());
                    report.setProperty(27, Integer.parseInt(objSoap.getProperty("nivel_dolor").toString()));
                    Log.d("GENERA REPORTE----",  "" + report);
                    usuarios.add(report);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {

                final String[] datosReporte = new String[30];
                final String[] datos = new String[usuarios.size()];

                for (int i = 0; i < usuarios.size(); i++) {
                    datos[i] =  usuarios.get(i).getProperty(10)+ " ";
                    datosReporte[0] =  usuarios.get(i).getProperty(0)+ " ";
                    datosReporte[1] =  usuarios.get(i).getProperty(1)+ " ";
                    datosReporte[2] =  usuarios.get(i).getProperty(2)+ " ";
                    datosReporte[3] =  usuarios.get(i).getProperty(3)+ " ";
                    datosReporte[4] =  usuarios.get(i).getProperty(4)+ " ";
                    datosReporte[5] =  usuarios.get(i).getProperty(5)+ " ";
                    datosReporte[6] =  usuarios.get(i).getProperty(6)+ " ";
                    datosReporte[7] =  usuarios.get(i).getProperty(7)+ " ";
                    datosReporte[8] =  usuarios.get(i).getProperty(8)+ " ";
                    datosReporte[9] =  usuarios.get(i).getProperty(9)+ " ";
                    datosReporte[10] =  usuarios.get(i).getProperty(10)+ " ";
                    datosReporte[11] =  usuarios.get(i).getProperty(11)+ " ";
                    datosReporte[12] =  usuarios.get(i).getProperty(12)+ " ";
                    datosReporte[13] =  usuarios.get(i).getProperty(13)+ " ";
                    datosReporte[14] =  usuarios.get(i).getProperty(14)+ " ";
                    datosReporte[15] =  usuarios.get(i).getProperty(15)+ " ";
                    datosReporte[16] =  usuarios.get(i).getProperty(16)+ " ";
                    datosReporte[17] =  usuarios.get(i).getProperty(17)+ " ";
                    datosReporte[18] =  usuarios.get(i).getProperty(18)+ " ";
                    datosReporte[19] =  usuarios.get(i).getProperty(19)+ " ";
                    datosReporte[20] =  usuarios.get(i).getProperty(20)+ " ";
                    datosReporte[21] =  usuarios.get(i).getProperty(21)+ " ";
                    datosReporte[22] =  usuarios.get(i).getProperty(22)+ " ";
                    datosReporte[23] =  usuarios.get(i).getProperty(23)+ " ";
                    datosReporte[24] =  usuarios.get(i).getProperty(24)+ " ";
                    datosReporte[25] =  usuarios.get(i).getProperty(25)+ " ";
                    datosReporte[26] =  usuarios.get(i).getProperty(26)+ " ";
                    datosReporte[27] =  usuarios.get(i).getProperty(27)+ " ";
                }
                Log.d("GENERA DATOS REPORTE",  "" + datosReporte);
               // generarPdf(datosReporte);
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        Prueba.this,
                        android.R.layout.simple_list_item_1, datos);
                setListAdapter(adaptador);
            } else {
                Toast.makeText(getApplicationContext(), "No se encontraron datos.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void generarPdf() {
        Document documento = new Document();
        try {
            File f = crearFichero(NOMBRE_DOCUMENTO);
            FileOutputStream ficheroPdf = new FileOutputStream(f.getAbsolutePath());
            PdfWriter.getInstance(documento, ficheroPdf);
            documento.open();
            PdfPCell cell;
            Paragraph pa = new Paragraph("");
            pa = new Paragraph("RESULTADO TRIAGE\n\n\n",
                    new Font(Font.HELVETICA, 14, Font.BOLD, Color.BLUE));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            documento.add(pa);

            float[] anchotablan = {2f, 1.7f, 0.3f};
            PdfPTable tablan = new PdfPTable(anchotablan);
            tablan.setTotalWidth(550);
            tablan.setLockedWidth(true);
            tablan.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablan.addCell(cell);
            pa = new Paragraph("Nivel: ",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablan.addCell(cell);

            String nivel = "1";
            if(nivel  == "1") {
                pa = new Paragraph("",//RESULTADO URGENTE,MEDIO,ALTO
                        new Font(Font.HELVETICA, 7, Font.NORMAL));
                cell = new PdfPCell(pa);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                //cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(Color.BLUE);
                tablan.addCell(cell);
                documento.add(tablan);
            }if(nivel  == "2"){
                pa = new Paragraph("",//RESULTADO URGENTE,MEDIO,ALTO
                        new Font(Font.HELVETICA, 7, Font.NORMAL));
                cell = new PdfPCell(pa);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_TOP);
                //cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(Color.RED);
                tablan.addCell(cell);
                documento.add(tablan);
            }

            Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 11,
                    Font.BOLD, Color.BLACK);
            documento.add(new Paragraph("DATOS GENERALES\n\n", font1));


            float[] anchotablage = {0.5f, 0.1f, 0.5f, 0.1f, 0.5f};
            PdfPTable tablage = new PdfPTable(anchotablage);
            tablage.setTotalWidth(550);
            tablage.setLockedWidth(true);
            tablage.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("",//PATERNO
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablage.addCell(cell);
            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablage.addCell(cell);
            pa = new Paragraph("",//MATERNO
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablage.addCell(cell);
            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablage.addCell(cell);
            pa = new Paragraph("",//NOMBRE
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablage.addCell(cell);
            documento.add(tablage);

            float[] anchotablaga = {0.5f, 0.1f, 0.5f, 0.1f, 0.5f};
            PdfPTable tablaga = new PdfPTable(anchotablaga);
            tablaga.setTotalWidth(550);
            tablaga.setLockedWidth(true);
            tablaga.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("Apellido Paterno",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tablaga.addCell(cell);
            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablaga.addCell(cell);
            pa = new Paragraph("Apellido Materno",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tablaga.addCell(cell);
            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablaga.addCell(cell);
            pa = new Paragraph("Nombre(s)",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tablaga.addCell(cell);
            documento.add(tablaga);

            pa = new Paragraph("\n\n",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            documento.add(pa);


            float[] anchotabla2a = {0.3f, 0.05f, 0.2f, 0.05f, 0.3f, 0.05f, 0.2f};
            PdfPTable tabla2a = new PdfPTable(anchotabla2a);
            tabla2a.setTotalWidth(550);
            tabla2a.setLockedWidth(true);
            tabla2a.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("",//SEXO
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabla2a.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabla2a.addCell(cell);

            pa = new Paragraph("",//FECHA NAC
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabla2a.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabla2a.addCell(cell);

            pa = new Paragraph("",//ALERGIA
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabla2a.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabla2a.addCell(cell);

            pa = new Paragraph("", //TIPO SANGRE
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabla2a.addCell(cell);
            documento.add(tabla2a);


            float[] anchotabla3 = {0.3f, 0.05f, 0.2f, 0.05f, 0.3f, 0.05f, 0.2f};
            PdfPTable tabla3 = new PdfPTable(anchotabla3);
            tabla3.setTotalWidth(550);
            tabla3.setLockedWidth(true);
            tabla3.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("Sexo",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tabla3.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabla3.addCell(cell);

            pa = new Paragraph("Fecha de Nacimiento",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tabla3.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabla3.addCell(cell);

            pa = new Paragraph("Alergias",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tabla3.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabla3.addCell(cell);

            pa = new Paragraph("Tipo sanguineo",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tabla3.addCell(cell);

            documento.add(tabla3);

            pa = new Paragraph("\n\n",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            documento.add(pa);

            float[] anchotabla4 = {0.5f, 1f};
            PdfPTable tabla4 = new PdfPTable(anchotabla4);
            tabla4.setTotalWidth(550);
            tabla4.setLockedWidth(true);
            tabla4.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("",//CURP
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabla4.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(27);
            tabla4.addCell(cell);


            documento.add(tabla4);

            float[] anchotabla5 = {0.5f, 1f};
            PdfPTable tabla5 = new PdfPTable(anchotabla5);
            tabla5.setTotalWidth(550);
            tabla5.setLockedWidth(true);
            tabla5.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("CURP",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tabla5.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(27);
            tabla5.addCell(cell);
            documento.add(tabla5);

            pa = new Paragraph("\n\n",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            documento.add(pa);

            Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 11,
                    Font.BOLD, Color.BLACK);
            documento.add(new Paragraph("DOMICILIO\n\n", font2));


            float[] anchotabladom = {0.5f, 0.1f, 0.1f, 0.1f, 0.5f, 0.1f, 0.2f};
            PdfPTable tabladom = new PdfPTable(anchotabladom);
            tabladom.setTotalWidth(550);
            tabladom.setLockedWidth(true);
            tabladom.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("",//CALLE
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom.addCell(cell);

            pa = new Paragraph("",//NUM
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom.addCell(cell);

            pa = new Paragraph("",//COLONIA
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom.addCell(cell);

            pa = new Paragraph("",//CP
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom.addCell(cell);

            documento.add(tabladom);

            float[] anchotabladom1 = {0.5f, 0.1f, 0.1f, 0.1f, 0.5f, 0.1f, 0.2f};
            PdfPTable tabladom1 = new PdfPTable(anchotabladom1);
            tabladom1.setTotalWidth(550);
            tabladom1.setLockedWidth(true);
            tabladom1.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("Calle",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tabladom1.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom1.addCell(cell);

            pa = new Paragraph("Num.",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tabladom1.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom1.addCell(cell);

            pa = new Paragraph("Colonia",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tabladom1.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom1.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom1.addCell(cell);

            documento.add(tabladom1);

            pa = new Paragraph("\n\n",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            documento.add(pa);

            float[] anchotabladom2 = {0.3f, 0.1f, 0.3f, 0.1f, 0.3f, 0.1f};
            PdfPTable tabladom2 = new PdfPTable(anchotabladom2);
            tabladom2.setTotalWidth(550);
            tabladom2.setLockedWidth(true);
            tabladom2.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("",//MUNICIPIO
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom2.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom2.addCell(cell);

            pa = new Paragraph("",//ESTADO
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom2.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom2.addCell(cell);

            pa = new Paragraph("",//TELEFONO
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom2.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom2.addCell(cell);


            documento.add(tabladom2);

            float[] anchotabladom3 = {0.3f, 0.1f, 0.3f, 0.1f, 0.3f, 0.1f};
            PdfPTable tabladom3 = new PdfPTable(anchotabladom3);
            tabladom3.setTotalWidth(550);
            tabladom3.setLockedWidth(true);
            tabladom3.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("Municipio",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tabladom3.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom3.addCell(cell);

            pa = new Paragraph("Estado",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tabladom3.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom3.addCell(cell);

            pa = new Paragraph("Telefono",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tabladom3.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tabladom3.addCell(cell);


            documento.add(tabladom3);

            pa = new Paragraph("\n\n",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            documento.add(pa);


            Font font3 = FontFactory.getFont(FontFactory.HELVETICA, 11,
                    Font.BOLD, Color.BLACK);
            documento.add(new Paragraph("RESULTADO\n\n", font2));


            float[] anchotablares = {0.6f, 0.1f, 0.6f, 0.1f, 0.3f, 0.1f, 0.3f, 1f};
            PdfPTable tablares = new PdfPTable(anchotablares);
            tablares.setTotalWidth(550);
            tablares.setLockedWidth(true);
            tablares.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("",//ENFERMEDAD
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablares.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablares.addCell(cell);

            pa = new Paragraph("",//SINTOMA
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablares.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablares.addCell(cell);

            pa = new Paragraph("",//DOLOR
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablares.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablares.addCell(cell);

            pa = new Paragraph("",//TOTALG
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablares.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(27);
            tablares.addCell(cell);

            documento.add(tablares);

            float[] anchotablares1 = {0.6f, 0.1f, 0.6f, 0.1f, 0.3f, 0.1f, 0.3f, 1f};
            PdfPTable tablares1 = new PdfPTable(anchotablares1);
            tabladom1.setTotalWidth(550);
            tabladom1.setLockedWidth(true);
            tablares1.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("Enfermedad",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tablares1.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablares1.addCell(cell);

            pa = new Paragraph("Sintoma.",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tablares1.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablares1.addCell(cell);

            pa = new Paragraph("Nivel Dolor",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tablares1.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablares1.addCell(cell);

            pa = new Paragraph("Glasgow",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.TOP);
            tablares1.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(27);
            tablares1.addCell(cell);

            documento.add(tablares1);

            pa = new Paragraph("\n\n",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            documento.add(pa);


            float[] anchotablasig = {0.05f, 0.1f, 0.08F, 1f};
            PdfPTable tablasig = new PdfPTable(anchotablasig);
            tablasig.setTotalWidth(550);
            tablasig.setLockedWidth(true);
            tablasig.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("FC:",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablasig.addCell(cell);

            pa = new Paragraph("",//FC
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.BOTTOM);
            tablasig.addCell(cell);

            pa = new Paragraph("x min",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablasig.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(27);
            tablasig.addCell(cell);
            documento.add(tablasig);


            //--------------


            PdfPTable tablasig1 = new PdfPTable(anchotablasig);
            tablasig1.setTotalWidth(550);
            tablasig1.setLockedWidth(true);
            tablasig1.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("FR:",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablasig1.addCell(cell);

            pa = new Paragraph("",//FR
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.BOTTOM);
            tablasig1.addCell(cell);

            pa = new Paragraph("x min",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablasig1.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(27);
            tablasig1.addCell(cell);
            documento.add(tablasig1);


            //------------------

            float[] anchotablasig1 = {0.2f, 0.1f, 0.05F, 1f};
            PdfPTable tablasig2 = new PdfPTable(anchotablasig1);
            tablasig2.setTotalWidth(550);
            tablasig2.setLockedWidth(true);
            tablasig2.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("Temperatura:",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablasig2.addCell(cell);

            pa = new Paragraph("",//TEMPERATURA
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.BOTTOM);
            tablasig2.addCell(cell);

            pa = new Paragraph("Â°C",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablasig2.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(27);
            tablasig2.addCell(cell);

            documento.add(tablasig2);

            PdfPTable tablasig3 = new PdfPTable(anchotablasig);
            tablasig3.setTotalWidth(550);
            tablasig3.setLockedWidth(true);
            tablasig3.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("TAS:",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablasig3.addCell(cell);

            pa = new Paragraph("",//ASTOLICA
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.BOTTOM);
            tablasig3.addCell(cell);

            pa = new Paragraph("mm/hgl",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablasig3.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(27);
            tablasig3.addCell(cell);

            documento.add(tablasig3);

            PdfPTable tablasig4 = new PdfPTable(anchotablasig);
            tablasig4.setTotalWidth(550);
            tablasig4.setLockedWidth(true);
            tablasig4.getDefaultCell().setBorder(PdfPCell.BOX);

            pa = new Paragraph("TAD:",
                    new Font(Font.HELVETICA, 7, Font.BOLD));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablasig4.addCell(cell);

            pa = new Paragraph("",//DIASTOLICA
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.BOTTOM);
            tablasig4.addCell(cell);

            pa = new Paragraph("mm/hgl",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            tablasig4.addCell(cell);

            pa = new Paragraph("",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(27);
            tablasig4.addCell(cell);
            documento.add(tablasig4);


            float[] anchotablass = {0.7f};
            PdfPTable tablass = new PdfPTable(anchotablass);
            tablass.setTotalWidth(550);
            tablass.setLockedWidth(true);
            tablass.getDefaultCell().setBorder(PdfPCell.BOX);


            //FILA EN BLANCO
            pa = new Paragraph("\n",
                    new Font(Font.HELVETICA, 8, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(27);
            tablass.addCell(cell);


            pa = new Paragraph(" OTRAS OBSERVACIONES:",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(Rectangle.NO_BORDER);
            tablass.addCell(cell);

            //FILA EN BLANCO
            pa = new Paragraph("\n",
                    new Font(Font.HELVETICA, 8, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(27);
            tablass.addCell(cell);


            pa = new Paragraph("\n\n\n\n\n\n\n",
                    new Font(Font.HELVETICA, 7, Font.NORMAL));
            cell = new PdfPCell(pa);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tablass.addCell(cell);
            documento.add(tablass);

        } catch (DocumentException e) {
            Log.e(ETIQUETA_ERROR, e.getMessage());
        } catch (IOException e) {
            Log.e(ETIQUETA_ERROR, e.getMessage());
        } finally {
            documento.close();
        }
    }


    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    public static File getRuta() {
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }

        return ruta;
    }


}