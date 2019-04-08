package com.example.contact;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;






@android.arch.persistence.room.Database(entities = People.class,version = Database.DATABASE_VERSION)
public abstract  class Database extends RoomDatabase {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Contact-database";

    public abstract PeopleDao dao();
    public static Database Instance;

    public static Database getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context, Database.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();
        }
            return Instance;
    }

}
