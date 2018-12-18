package ir.greencode.advicelawAndroid.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey
    int catId;
    String catName;
    String catImg;
    long timeOfCheck;
}
