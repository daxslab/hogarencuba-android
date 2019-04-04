package com.f4adaxs.apps.hogarencuba.adapters.filter;

import android.widget.Filter;

import com.f4adaxs.apps.hogarencuba.adapters.PublicacionAdapter;
import com.f4adaxs.apps.hogarencuba.db.entity.Publicacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rigo on 4/6/18.
 */

public class PublicacionFilter extends Filter {

    private PublicacionAdapter adapter;
    private List<Publicacion> filterList;

    public PublicacionFilter(List<Publicacion> filterList, PublicacionAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if(constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<Publicacion> filteredPublicaciones = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                if(filterList.get(i).getCode().toUpperCase().contains(constraint)
                        || filterList.get(i).getDescription().toUpperCase().contains(constraint)
                        || filterList.get(i).getTitle().toUpperCase().contains(constraint)
                        || filterList.get(i).getLocation().toUpperCase().contains(constraint)
                        || filterList.get(i).getBathroomsCount().toUpperCase().contains(constraint)
                        || filterList.get(i).getBedroomsCount().toUpperCase().contains(constraint)
                        || filterList.get(i).getGarageCount().toUpperCase().contains(constraint)
                        || filterList.get(i).getPrice().toUpperCase().contains(constraint)
                        || filterList.get(i).getType().toUpperCase().contains(constraint)
                ) {
                    filteredPublicaciones.add(filterList.get(i));
                }
            }

            results.count = filteredPublicaciones.size();
            results.values = filteredPublicaciones;
        } else {
            results.count = filterList.size();
            results.values = filterList;

        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.setPublicacionList((List<Publicacion>) results.values);

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}