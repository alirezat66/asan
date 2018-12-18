package ir.greencode.advicelawAndroid.controler;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ir.greencode.advicelawAndroid.database.Category;
import ir.greencode.advicelawAndroid.database.CategoryDao;
import ir.greencode.advicelawAndroid.database.Profile;
import ir.greencode.advicelawAndroid.database.ProfileDao;

@Database(entities = {Category.class, Profile.class}, version =1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract CategoryDao categoryDao();
    public abstract ProfileDao profileDao();


    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,AppDatabase.class.getName())
                            // To simplify the codelab, allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
