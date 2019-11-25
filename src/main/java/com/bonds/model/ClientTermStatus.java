package com.bonds.model;

public enum ClientTermStatus {
    INITIAL_TERM(0),
    INCREASED_TERM(1),
    DECREASED_TERM(2);

    private final int status;

    ClientTermStatus(int status){
        this.status=status;
    }

    public int getClientTermStatus() {
        return this.status;
    }
}
