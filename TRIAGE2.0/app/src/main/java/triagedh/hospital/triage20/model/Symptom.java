package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Symptom implements KvmSerializable {

    private int symptomId;
    private String description;
    private int level;
    private int disease;

    public Symptom(int symptomId, String description, int level, int disease) {
        this.symptomId = symptomId;
        this.description = description;
        this.level = level;
        this.disease = disease;
    }

    public Symptom() {
    }

    public int getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(int symptomId) {
        this.symptomId = symptomId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDisease() {
        return disease;
    }

    public void setDisease(int disease) {
        this.disease = disease;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return symptomId;
            case 1:
                return description;
            case 2:
                return level;
            case 3:
                return disease;
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
                symptomId = Integer.parseInt(o.toString());
                break;
            case 1:
                description = o.toString();
                break;
            case 2:
                level =  Integer.parseInt(o.toString());
                break;
            case 3:
                disease = Integer.parseInt(o.toString());
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
