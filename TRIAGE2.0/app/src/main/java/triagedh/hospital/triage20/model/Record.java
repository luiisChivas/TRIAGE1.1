package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Record implements KvmSerializable {

    private int patientId;
    private String name;
    private String lastName;
    private String secondLastName;
    private String disease;
    private String symptom;
    private int fc;
    private int fr;
    private int g_apertura_ocular;
    private int g_respuesta_motora;
    private int g_respuesta_verbal;
    private int g_total;
    private int ta_sistolica;
    private int ta_diastolica;
    private String observations;

    public Record(int patientId, String name, String lastName, String secondLastName, String disease, String symptom, int fc, int fr, int g_apertura_ocular, int g_respuesta_motora, int g_respuesta_verbal, int g_total, int ta_sistolica, int ta_diastolica, String observations) {
        this.patientId = patientId;
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.disease = disease;
        this.symptom = symptom;
        this.fc = fc;
        this.fr = fr;
        this.g_apertura_ocular = g_apertura_ocular;
        this.g_respuesta_motora = g_respuesta_motora;
        this.g_respuesta_verbal = g_respuesta_verbal;
        this.g_total = g_total;
        this.ta_sistolica = ta_sistolica;
        this.ta_diastolica = ta_diastolica;
        this.observations = observations;
    }

    public Record() {
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public int getFc() {
        return fc;
    }

    public void setFc(int fc) {
        this.fc = fc;
    }

    public int getFr() {
        return fr;
    }

    public void setFr(int fr) {
        this.fr = fr;
    }

    public int getG_apertura_ocular() {
        return g_apertura_ocular;
    }

    public void setG_apertura_ocular(int g_apertura_ocular) {
        this.g_apertura_ocular = g_apertura_ocular;
    }

    public int getG_respuesta_motora() {
        return g_respuesta_motora;
    }

    public void setG_respuesta_motora(int g_respuesta_motora) {
        this.g_respuesta_motora = g_respuesta_motora;
    }

    public int getG_respuesta_verbal() {
        return g_respuesta_verbal;
    }

    public void setG_respuesta_verbal(int g_respuesta_verbal) {
        this.g_respuesta_verbal = g_respuesta_verbal;
    }

    public int getG_total() {
        return g_total;
    }

    public void setG_total(int g_total) {
        this.g_total = g_total;
    }

    public int getTa_sistolica() {
        return ta_sistolica;
    }

    public void setTa_sistolica(int ta_sistolica) {
        this.ta_sistolica = ta_sistolica;
    }

    public int getTa_diastolica() {
        return ta_diastolica;
    }

    public void setTa_diastolica(int ta_diastolica) {
        this.ta_diastolica = ta_diastolica;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return patientId;
            case 1:
                return name;
            case 2:
                return lastName;
            case 3:
                return lastName;
            case 4:
                return disease;
            case 5:
                return symptom;
            case 6:
                return fc;
            case 7:
                return fr;
            case 8:
                return g_apertura_ocular;
            case 9:
                return g_respuesta_motora;
            case 10:
                return g_respuesta_verbal;
            case 11:
                return g_total;
            case 12:
                return ta_sistolica;
            case 13:
                return ta_diastolica;
            case 14:
                return observations;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                patientId = Integer.parseInt(o.toString());
                break;
            case 1:
                name = o.toString();
                break;
            case 2:
                lastName = o.toString();
                break;
            case 3:
                secondLastName = o.toString();
                break;
            case 4:
                disease = o.toString();
                break;
            case 5:
                symptom = o.toString();
                break;
            case 6:
                fc = Integer.parseInt(o.toString());
                break;
            case 7:
                fr =  Integer.parseInt(o.toString());
                break;
            case 8:
                g_apertura_ocular= Integer.parseInt(o.toString());
                break;
            case 9:
                g_respuesta_motora = Integer.parseInt(o.toString());
                break;
            case 10:
                g_respuesta_verbal = Integer.parseInt(o.toString());
                break;
            case 11:
                g_total = Integer.parseInt(o.toString());
                break;
            case 12:
                ta_sistolica = Integer.parseInt(o.toString());
                break;
            case 13:
                ta_diastolica = Integer.parseInt(o.toString());
                break;
            case 14:
                observations = o.toString();
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
