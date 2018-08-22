package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Patient implements KvmSerializable {
    private int id;
    private int personFk;
    private String name;
    private String lastName;
    private String secondLastName;
    private boolean status;


    public Patient(int id, int personFk, String name, String lastName, String secondLastName, boolean status) {
        this.id = id;
        this.personFk = personFk;
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.status = status;
    }

    public Patient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonFk() {
        return personFk;
    }

    public void setPersonFk(int personFk) {
        this.personFk = personFk;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return id;
            case 1:
                return personFk;
            case 2:
                return name;
            case 3:
                return lastName;
            case 4:
                return secondLastName;
            case 5:
                return status;
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
                id =Integer.parseInt(o.toString());
                break;
            case 1:
                personFk = Integer.parseInt(o.toString());
                break;
            case 2:
                name= o.toString();
                break;
            case 3:
                lastName = o.toString();
                break;
            case 4:
                secondLastName = o.toString();
                break;
            case 5:
                status = Boolean.parseBoolean(o.toString());
                break;

        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
