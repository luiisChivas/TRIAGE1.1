package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class User implements KvmSerializable {

    private int userId;
    private String user;
    private String pass;
    private boolean status;
    private int personId;
    private String name;
    private String lastName;
    private String secondLastName;
    private String curp;


    public User(int userId, String user, String pass, boolean status, int personId, String name, String lastName, String secondLastName, String curp) {
        this.userId = userId;
        this.user = user;
        this.pass = pass;
        this.status = status;
        this.personId = personId;
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.curp = curp;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return userId;
            case 1:
                return user;
            case 2:
                return pass;
            case 3:
                return status;
            case 4:
                return personId;
            case 5:
                return name;
            case 6:
                return lastName;
            case 7:
                return secondLastName;
            case 8:
                return curp;
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
                userId =Integer.parseInt(o.toString());
                break;
           case 1:
                user = o.toString();
                break;
            case 2:
                pass = o.toString();
                break;
            case 3:
                status = Boolean.parseBoolean(o.toString());
                break;
            case 4:
                personId = Integer.parseInt(o.toString());
                break;
            case 5:
                name = o.toString();
            case 6:
                lastName = o.toString();
                break;
            case  7:
                secondLastName = o.toString();
                break;
            case 8:
                curp = o.toString();
                break;

        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }
}
