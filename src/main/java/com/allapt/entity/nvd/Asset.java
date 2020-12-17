package com.allapt.entity.nvd;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/10/26 17:05
 */
@NodeEntity
public class Asset {
    @Id
    @GeneratedValue
    private Long id;
    private String lable="Platform";
    private String part;
    private String vendor;
    private String product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
