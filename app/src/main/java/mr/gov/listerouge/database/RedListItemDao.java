package mr.gov.listerouge.database;

// RedListItemDao.java

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import mr.gov.listerouge.models.RedListItem;

@Dao
public interface RedListItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RedListItem> items);

    @Query("SELECT * FROM red_list_items")
    LiveData<List<RedListItem>> getAllItems();

    @Query("DELETE FROM red_list_items")
    void deleteAllItems();


    @Query("DELETE FROM red_list_items WHERE _id = :id")
    void deleteItemById(String id);
}
