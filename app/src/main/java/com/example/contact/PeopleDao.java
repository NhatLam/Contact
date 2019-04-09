package com.example.contact;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PeopleDao {
    @Query("SELECT * FROM data")
    List<People> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(People people);
    @Update
    void updateUser(People... people);


}
