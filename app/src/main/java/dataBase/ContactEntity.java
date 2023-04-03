package dataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class ContactEntity {
    @ColumnInfo(name = "columnName")
    private String name;

    @ColumnInfo(name = "columnEmail")
    private String Email;

    @ColumnInfo(name = "columnId")
    @PrimaryKey(autoGenerate = true)
    private int id;

    public ContactEntity() {
    }

    public ContactEntity(String name, String email, int id) {
        this.name = name;
        Email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
