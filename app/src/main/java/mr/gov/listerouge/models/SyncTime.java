// SyncTime.java
package mr.gov.listerouge.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sync_time")
public class SyncTime {

    @PrimaryKey
    private int id; // We'll use a single row with id = 1

    private long lastSyncTime;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public long getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(long lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }
}
