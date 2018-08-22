package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class State implements KvmSerializable{

    private int stateId;
    private String description;

    public State(int stateId, String description) {
        this.stateId = stateId;
        this.description = description;
        //this.pais_id = pais_id;
    }

    public State() {
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
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
                return stateId;
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
                stateId = Integer.parseInt(o.toString());
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
