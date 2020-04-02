package com.example.fpgins;

public class MenuModel {

    public String menuName, idName;
    public boolean hasChildren, isGroup;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, String idName) {

        this.menuName = menuName;
        this.idName = idName;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }

}
