package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Sex implements KvmSerializable {


    private int sexId;
    private String description;

    public Sex(int sexId, String description) {
        this.sexId = sexId;
        this.description = description;
    }

    public Sex() {
    }

    public int getSexId() {
        return sexId;
    }

    public void setSexId(int sexId) {
        this.sexId = sexId;
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
                return sexId;
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
                sexId = Integer.parseInt(o.toString());
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
