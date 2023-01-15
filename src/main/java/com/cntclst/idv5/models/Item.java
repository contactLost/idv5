package com.cntclst.idv5.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {
    private Integer id;

    private String itemName;

    private String itemDescription;

    private Integer itemQuantity;

    private User itemOwner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    public Integer getId() {
        return id;
    }

    public Item setId(Integer id) {
        this.id = id;
        return this;
    }

    @Column(name = "item_name", nullable = false)
    public String getItemName() {
        return itemName;
    }

    public Item setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    @Column(name = "item_description", nullable = false)
    public String getItemDescription() {
        return itemDescription;
    }

    public Item setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
        return this;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "item_owner", nullable = false)
    public User getItemOwner() {
        return itemOwner;
    }

    public Item setItemOwner(User itemOwner) {
        this.itemOwner = itemOwner;
        return this;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}