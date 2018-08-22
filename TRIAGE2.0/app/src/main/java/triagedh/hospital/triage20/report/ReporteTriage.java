package triagedh.hospital.triage20.report;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import harmony.java.awt.Color;


public class ReporteTriage extends Activity {

    private final static String NOMBRE_DIRECTORIO = "MiPdf";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";

    Button btnGenerar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_triage);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        }

        btnGenerar = (Button) findViewById(R.id.btnGenerar);

        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //generarPdf();
            }
        });

    }

    public void generarPdf(String[] datos) {
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

            pa = new Paragraph(datos[1],//PATERNO
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
            pa = new Paragraph(datos[2],//MATERNO
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
            pa = new Paragraph(datos[0],//NOMBRE
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

            pa = new Paragraph(datos[3],//SEXO
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

            pa = new Paragraph(datos[4],//FECHA NAC
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

            pa = new Paragraph(datos[5],//ALERGIA
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

            pa = new Paragraph(datos[26], //TIPO SANGRE
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

            pa = new Paragraph(datos[6],//CURP
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

            pa = new Paragraph(datos[8],//CALLE
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

            pa = new Paragraph(datos[9],//NUM
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

            pa = new Paragraph(datos[10],//COLONIA
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

            pa = new Paragraph(datos[11],//MUNICIPIO
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

            pa = new Paragraph(datos[25],//ESTADO
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

            pa = new Paragraph(datos[12],//TELEFONO
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

            pa = new Paragraph(datos[13],//ENFERMEDAD
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

            pa = new Paragraph(datos[14],//SINTOMA
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

            pa = new Paragraph(datos[15],//DOLOR
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

            pa = new Paragraph(datos[24],//TOTALG
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

            pa = new Paragraph(datos[16],//FC
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

            pa = new Paragraph(datos[17],//FR
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

            pa = new Paragraph(datos[18],//TEMPERATURA
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

            pa = new Paragraph(datos[19],//ASTOLICA
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

            pa = new Paragraph(datos[20],//DIASTOLICA
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
