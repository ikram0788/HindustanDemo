package com.nadeem.hindustan.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.nadeem.hindustan.database.dao.MerchantDao;
import com.nadeem.hindustan.database.dao.UserDao;
import com.nadeem.hindustan.database.entities.Merchant;
import com.nadeem.hindustan.database.entities.User;

/**
 * Created by ikram on 30/1/18.
 */
@Database(entities = {User.class, Merchant.class}, version = 1, exportSchema = false)
//@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "hindustan_nadeem";
    /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'guide' "
                    + " ADD COLUMN 'is_booked' INTEGER NOT NULL DEFAULT 0");
        }
    };*/
    private static final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context, final AppExecutors executors) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                /*INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                                .build();*/
                INSTANCE = buildDatabase(context.getApplicationContext(), executors);
                INSTANCE.updateDatabaseCreated(context.getApplicationContext());
            }
        }
        return INSTANCE;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                //.addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        setDatabaseCreated();
                    }
                })//.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    private static void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public abstract UserDao getUserDao();
    public abstract MerchantDao getMerchantDao();


    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

}