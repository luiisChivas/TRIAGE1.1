package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Disease implements KvmSerializable {

    private int diseaseId;
    private String description;


    public Disease(int diseaseId, String description) {
        this.diseaseId = diseaseId;
        this.description = description;
    }

    public Disease(){

    }

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return diseaseId;
            case 1:
                return description;
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
                diseaseId = Integer.parseInt(o.toString());
                break;
            case 1:
                description = o.toString();
                break;
             }
        }


    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
