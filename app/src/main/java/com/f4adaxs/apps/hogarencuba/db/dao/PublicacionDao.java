package com.f4adaxs.apps.hogarencuba.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.f4adaxs.apps.hogarencuba.db.entity.Publicacion;

import java.util.List;


import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PublicacionDao {

    @Query("SELECT * FROM publicacion ORDER BY ranking, created_at DESC")
    LiveData<List<Publicacion>> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Publicacion> publicaciones);

    @Insert(onConflict = REPLACE)
    void insert(Publicacion publicacion);

}
