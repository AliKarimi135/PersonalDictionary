package ir.aliprogramer.dictionary.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DictionaryDao {

    @Query("SELECT * FROM dictionary WHERE word LIKE :search OR definition LIKE :search")
    List<Dictionary> find(String search);

    @Query("SELECT * FROM dictionary WHERE word =:search LIMIT 1")
    Dictionary findWord(String search);

    @Query("SELECT * FROM dictionary WHERE definition LIKE :search")
    Dictionary findDefinition(String search);

    @Query("SELECT * FROM dictionary ORDER BY word ")
    List<Dictionary> getAllWords();

    @Insert
    void insertWord(Dictionary word);

    @Update
    void update(Dictionary word);

    @Query("DELETE FROM dictionary WHERE id = :id")
    void deleteWord(int id);


}
