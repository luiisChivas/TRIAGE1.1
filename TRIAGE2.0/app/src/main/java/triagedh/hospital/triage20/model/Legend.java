package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Legend implements KvmSerializable {

    private int legendId;
    private String name;
    private String description;

    public Legend(int legendId, String name, String description) {
        this.legendId = legendId;
        this.name = name;
        this.description = description;
    }

    public Legend() {
    }

    public int getLegendId() {
        return legendId;
    }

    public void setLegendId(int legendId) {
        this.legendId = legendId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                return legendId;
            case 1:
                return name;
            case 2:
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
        switch (i){
            case 0:
                legendId =Integer.parseInt(o.toString());
                break;
            case 1:
                name = o.toString();
                break;
            case 2:
                description = o.toString();
                break;
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
