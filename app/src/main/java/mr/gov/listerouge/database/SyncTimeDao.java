// SyncTimeDao.java
package mr.gov.listerouge.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import mr.gov.listerouge.models.SyncTime;

@Dao
public interface SyncTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(SyncTime syncTime);

    @Query("SELECT * FROM sync_time WHERE id = 1")
    SyncTime getSyncTime();
}
