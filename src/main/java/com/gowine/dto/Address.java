package com.gowine.dto;

import jakarta.persistence.Embeddable;
import lombok.Setter;

@Embeddable
@Setter
public class Address {
    private String zipcode;
    private String addr;
    private String addrDetail;

    protected Address() {

    }

    public Address(String zipcode, String addr, String addrDetail) {
        this.zipcode = zipcode;
        this.addr = addr;
        this.addrDetail = addrDetail;
    }
}
