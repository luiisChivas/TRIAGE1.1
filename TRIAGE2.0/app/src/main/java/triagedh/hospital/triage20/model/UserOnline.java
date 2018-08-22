package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class UserOnline implements KvmSerializable {

    private String name;
    private String lastName;
    private String secondLastName;


    public UserOnline(String name, String lastName, String secondLastName) {
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
    }

    public UserOnline() {
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

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return name;
            case 1:
                return lastName;
            case 2:
                return secondLastName;
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
                name = o.toString();
                break;
            case 1:
                lastName = o.toString();
                break;
            case 2:
                secondLastName = o.toString();
                break;
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
