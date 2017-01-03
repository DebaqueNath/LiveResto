package com.platine.liveresto.model;

import com.platine.liveresto.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by --- on 03/01/2017.
 */

public class Data {
    private static final String[] titles  = {"Budget", "Place(s)", "Type de restaurant"} ;

    public static List<Item> getListData() {
        List<Item> data = new ArrayList<>();

        Item item_budget = new Item();
        item_budget.setTitle(titles[0]);
        //item_budget.setImageResId(R.id.im_icon_flower);
        data.add(item_budget);

        Item item_place = new Item();
        item_place.setTitle(titles[1]);
        //item_budget.setImageResId(R.drawable.red_flower);
        data.add(item_place);

        Item item_type = new Item();
        item_type.setTitle(titles[2]);
        //item_budget.setImageResId(R.drawable.red_flower);
        data.add(item_type);

        return data;
    }
}
