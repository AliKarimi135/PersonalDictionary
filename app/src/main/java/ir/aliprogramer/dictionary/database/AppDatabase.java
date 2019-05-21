package ir.aliprogramer.dictionary.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Dictionary.class,version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DictionaryDao dao();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if(instance==null)
            instance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"dictionary_database")
                    .allowMainThreadQueries().build();

        return instance;
    }
}
