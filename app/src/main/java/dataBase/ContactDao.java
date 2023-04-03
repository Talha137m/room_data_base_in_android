package dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    long insetContact(ContactEntity contactEntity);

    @Update
    void updateContact(ContactEntity contactEntity);

    @Delete
    void deleteContact(ContactEntity contactEntity);

    @Query("select * from contacts")
    List<ContactEntity> fetchContacts();

    @Query("select * from contacts where columnId==:contactId")
    ContactEntity getSingleContact(long contactId);
}
