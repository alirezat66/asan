package ir.greencode.advicelawAndroid.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProfileDao {

    @Query("select * from  profile limit 1")
    Profile getProfile();


    @Insert(onConflict = REPLACE)
    void insert(Profile profile);


    @Query("DELETE FROM profile")
    public void nukeTable();

    @Update
    void update(Profile profile);
}
