package com.platine.liveresto.model;

/**
 * Created by --- on 03/01/2017.
 */

public class Data {
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
