package com.nadeem.hindustan.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "merchant")
public class Merchant {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String billNo;
    private String name;
    private String mobile;
    private double debit;
    private double credit;
    private long debitDate;
    private long creditDate;
    private int isFavorite;

    public Merchant() {

    }

    @Ignore
    public Merchant(String billNo, String name, String mobile, double debit, double credit, long debitDate, long creditDate) {
        this.billNo = billNo;
        this.name = name;
        this.mobile = mobile;
        this.debit = debit;
        this.credit = credit;
        this.debitDate = debitDate;
        this.creditDate = creditDate;
    }

    @Ignore
    public Merchant(long id, String billNo, String name, String mobile, double debit, double credit, long debitDate, long creditDate) {
        this.id = id;
        this.billNo = billNo;
        this.name = name;
        this.mobile = mobile;
        this.debit = debit;
        this.credit = credit;
        this.debitDate = debitDate;
        this.creditDate = creditDate;
    }

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public long getDebitDate() {
        return debitDate;
    }

    public void setDebitDate(long debitDate) {
        this.debitDate = debitDate;
    }

    public long getCreditDate() {
        return creditDate;
    }

    public void setCreditDate(long creditDate) {
        this.creditDate = creditDate;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
