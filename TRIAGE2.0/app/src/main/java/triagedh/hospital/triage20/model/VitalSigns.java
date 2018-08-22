package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class VitalSigns implements KvmSerializable {

    private int signsId;
    private int patientId;
    private float fc;
    private float fr;
    private float temperature;
    private float taSystolic;
    private float taDiastolic;
    private int gApertureOcular;
    private int gVerbalResponse;
    private int gMotorResponse;
    private int g_total;

    public VitalSigns(int signsId, int patientId, float fc, float fr, float temperature, float taSystolic,
                      float taDiastolic, int gApertureOcular, int gVerbalResponse, int gMotorResponse, int g_total) {
        this.signsId = signsId;
        this.patientId = patientId;
        this.fc = fc;
        this.fr = fr;
        this.temperature = temperature;
        this.taSystolic = taSystolic;
        this.taDiastolic = taDiastolic;
        this.gApertureOcular = gApertureOcular;
        this.gVerbalResponse = gVerbalResponse;
        this.gMotorResponse = gMotorResponse;
        this.g_total = g_total;
    }


    public VitalSigns() {
    }

    public int getSignsId() {
        return signsId;
    }

    public void setSignsId(int signsId) {
        this.signsId = signsId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public float getFc() {
        return fc;
    }

    public void setFc(float fc) {
        this.fc = fc;
    }

    public float getFr() {
        return fr;
    }

    public void setFr(float fr) {
        this.fr = fr;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getTaSystolic() {
        return taSystolic;
    }

    public void setTaSystolic(float taSystolic) {
        this.taSystolic = taSystolic;
    }

    public float getTaDiastolic() {
        return taDiastolic;
    }

    public void setTaDiastolic(float taDiastolic) {
        this.taDiastolic = taDiastolic;
    }

    public int getgApertureOcular() {
        return gApertureOcular;
    }

    public void setgApertureOcular(int gApertureOcular) {
        this.gApertureOcular = gApertureOcular;
    }

    public int getgVerbalResponse() {
        return gVerbalResponse;
    }

    public void setgVerbalResponse(int gVerbalResponse) {
        this.gVerbalResponse = gVerbalResponse;
    }

    public int getgMotorResponse() {
        return gMotorResponse;
    }

    public void setgMotorResponse(int gMotorResponse) {
        this.gMotorResponse = gMotorResponse;
    }

    public int getG_total() {
        return g_total;
    }

    public void setG_total(int g_total) {
        this.g_total = g_total;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return signsId;
            case 1:
                return patientId;
            case 2:
                return fc;
            case 3:
                return fr;
            case 4:
                return temperature;
            case 5:
                return taSystolic;
            case 6:
                return taDiastolic;
            case 7:
                return gApertureOcular;
            case 8:
                return gVerbalResponse;
            case 9:
                return getgMotorResponse();
            case 10:
                return g_total;
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
                signsId = Integer.parseInt(o.toString());
                break;
            case 1:
                patientId = Integer.parseInt(o.toString());
                break;
            case 2:
                fc = Float.parseFloat(o.toString());
                break;
            case 3:
                fr = Float.parseFloat(o.toString());
                break;
            case 4:
                temperature = Float.parseFloat(o.toString());
                break;
            case 5:
                taSystolic = Float.parseFloat(o.toString());
                break;
            case 6:
                taDiastolic = Float.parseFloat(o.toString());
                break;
            case 7:
                gApertureOcular = Integer.parseInt(o.toString());
                break;
            case 8:
                gVerbalResponse = Integer.parseInt(o.toString());
                break;
            case 9:
                gMotorResponse = Integer.parseInt(o.toString());
                break;
            case 10:
                g_total = Integer.parseInt(o.toString());
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
