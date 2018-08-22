package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Municipality implements KvmSerializable {

    private int municipalityId;
    private String descripcion;

    public Municipality(int municipalityId, String descripcion) {
        this.municipalityId = municipalityId;
        this.descripcion = descripcion;
    }

    public Municipality() {
    }

    public int getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(int municipalityId) {
        this.municipalityId = municipalityId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return municipalityId;
            case 1:
                return descripcion;
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
                municipalityId = Integer.parseInt(o.toString());
                break;
            case 1:
                descripcion = o.toString();
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
