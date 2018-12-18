package ir.greencode.advicelawAndroid.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CategoryDao {

    @Query("select * from  category")
    List<Category> listOfCats();


    @Insert(onConflict = REPLACE)
    void insertCat(Category category);

    @Query("DELETE FROM category")
    public void nukeTable();
}
