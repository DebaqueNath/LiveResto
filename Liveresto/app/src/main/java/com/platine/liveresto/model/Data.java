package com.platine.liveresto.model;

/**
 * Created by --- on 03/01/2017.
 */

public class Data {
    //private static final String[] titles  = {"Budget", "Place(s)", "Type de restaurant"} ;
    public String name;
    public int imageId;
    public boolean isSelected;

    public Data(String name){
        this.name = name;
    }

    public Data(String name, int imgId, boolean isSelected){
        this.name = name;
        this.imageId = imgId;
        this.isSelected = isSelected;
    }

    /*public static List<Item> getListData() {
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
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
