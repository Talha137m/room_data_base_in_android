package dataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {ContactEntity.class},version = 1)
public abstract class ContactDataBase extends RoomDatabase {
    //Linking the DAO with our dataBase

    public abstract ContactDao getContactDao();
}
