package com.cntclst.idv5.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ItemAddRequest {

    private String itemName;
    private String itemDesc;
    private Integer itemQuan;

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
