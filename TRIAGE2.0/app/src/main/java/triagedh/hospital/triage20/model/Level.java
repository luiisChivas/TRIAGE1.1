package triagedh.hospital.triage20.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class Level implements KvmSerializable {

    private int levelId;
    private int level;
    private String description;

    public Level(int levelId, int level, String description) {
        this.levelId = levelId;
        this.level = level;
        this.description = description;
    }

    public Level(){

    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
                return levelId;
            case 1:
                return level;
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
        switch (i) {
            case 0:
                levelId = Integer.parseInt(o.toString());
                break;
            case 1:
                level = Integer.parseInt(o.toString());
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
