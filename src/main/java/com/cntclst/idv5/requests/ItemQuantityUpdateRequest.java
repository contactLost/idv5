package com.cntclst.idv5.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect
public class ItemQuantityUpdateRequest {

    private Integer itemid;
    private String itemName;
    private String itemDesc;
    private Integer itemQuan;

    public Integer getItemid() {
        return itemid;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public Integer getItemQuan() {
        return itemQuan;
    }
}

