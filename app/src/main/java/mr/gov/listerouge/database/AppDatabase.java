// AppDatabase.java
package mr.gov.listerouge.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import mr.gov.listerouge.database.SyncTimeDao;
import mr.gov.listerouge.models.RedListItem;
import mr.gov.listerouge.models.SyncTime;

@Database(entities = {RedListItem.class, SyncTime.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract RedListItemDao redListItemDao();
    public abstract SyncTimeDao syncTimeDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized(AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "red_list_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
