package com.f4adaxs.apps.hogarencuba.models.transformer;

import com.f4adaxs.apps.hogarencuba.db.entity.Publicacion;
import com.f4adaxs.apps.hogarencuba.models.responsive.data.PublicacionData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rigo on 3/27/18.
 */

public class PublicacionTransformer {

    public static Publicacion transform(PublicacionData publicacionData) {
        Publicacion publicacionEntity = new Publicacion();
        publicacionEntity.setCode(publicacionData.getCode());
        publicacionEntity.setTitle(publicacionData.getTitle());
        publicacionEntity.setBedroomsCount(publicacionData.getBedroomsCount());
        publicacionEntity.setBathroomsCount(publicacionData.getBathroomsCount());
        publicacionEntity.setGarageCount(publicacionData.getGarageCount());
        publicacionEntity.setAreaCount(publicacionData.getAreaCount());
        publicacionEntity.setMetadata(publicacionData.getMetadata());
        publicacionEntity.setDescription(publicacionData.getDescription());
        publicacionEntity.setLocation(publicacionData.getLocation());
        publicacionEntity.setPrice(publicacionData.getPrice());
        publicacionEntity.setRanking(publicacionData.getRanking());
        publicacionEntity.setCreated_at(publicacionData.getCreated_at());
        publicacionEntity.setFirstImageUrl(publicacionData.getFirstImageUrl());
        publicacionEntity.setType(publicacionData.getType());
        return publicacionEntity;
    }


    public static List<Publicacion> transform(List<PublicacionData> publicacionDatas) {
        List<Publicacion> publicacionesEntities = new ArrayList<>();
        for (PublicacionData publicacionData: publicacionDatas) {
            publicacionesEntities.add(PublicacionTransformer.transform(publicacionData));
        }
        return  publicacionesEntities;
    }
}
