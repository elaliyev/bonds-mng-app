package com.bonds.model;

public enum ApplicationStatus {
    PENDING(0),
    APPROVED(1),
    DECLINED(2);

    private final int status;

    ApplicationStatus(int status){
        this.status=status;
    }

    public int getStatus() {
        return this.status;
    }
}
