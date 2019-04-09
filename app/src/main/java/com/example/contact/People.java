package com.example.contact;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.io.Serializable;

@Entity(tableName = "data")
public class People implements Serializable {
    @ColumnInfo(name = "id")
    @NonNull
    @PrimaryKey
    String id;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "numberphone")

    String numberPhone;
    @ColumnInfo(name = "mail")
    String mail;
    @ColumnInfo(name = "url")
    String urlPicture;

    @Ignore
    public People(@NonNull String id, String name, String numberPhone, String mail, String urlPicture) {
        this.id = id;
        this.name = name;
        this.numberPhone = numberPhone;
        this.mail = mail;
        this.urlPicture = urlPicture;
    }

    public People() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
