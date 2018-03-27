package com.lcx.xml.serialize.model;

public class Money {

    /**
     * 三位数的货币代码。格式为 ISO 4217
     */
    private String CurrencyCode;

    /**
     * 货币金额
     */
    private String Amount;

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    @Override
    public String toString() {
        return "Money [CurrencyCode=" + CurrencyCode + ", Amount=" + Amount + "]";
    }

}
