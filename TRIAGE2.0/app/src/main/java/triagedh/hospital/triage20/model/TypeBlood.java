package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class TypeBlood implements KvmSerializable {

    private int typeBloodId;
    private String description;

    public TypeBlood(int typeBloodId, String description) {
        this.typeBloodId = typeBloodId;
        this.description = description;
    }

    public TypeBlood() {
    }

    public int getTypeBloodId() {
        return typeBloodId;
    }

    public void setTypeBloodId(int typeBloodId) {
        this.typeBloodId = typeBloodId;
    }

    public String getDescripcion() {
        return description;
    }

    public void setDescripcion(String descripcion) {
        this.description = descripcion;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return typeBloodId;
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
                typeBloodId = Integer.parseInt(o.toString());
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
