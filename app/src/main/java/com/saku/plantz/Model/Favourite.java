package com.saku.plantz.Model;

public class Favourite {

    private String add_Id;
    private String flag;

    public Favourite(String add_Id, String flag) {
        this.add_Id = add_Id;
        this.flag = flag;
    }

    public Favourite() {
    }

    public String getAdd_Id() {
        return add_Id;
    }

    public String getFlag() {
        return flag;
    }

    public void setAdd_Id(String add_Id) {
        this.add_Id = add_Id;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
