package com.lcx.xml.serialize.model;

public class ListOrdersResult {

    private Order[] Orders;

    private String CreatedBefore;

    private String NextToken;

    private String errorMessage;

    public Order[] getOrders() {
        return Orders;
    }

    public void setOrders(Order[] orders) {
        Orders = orders;
    }

    public String getCreatedBefore() {
        return CreatedBefore;
    }

    public void setCreatedBefore(String createdBefore) {
        CreatedBefore = createdBefore;
    }

    public String getNextToken() {
        return NextToken;
    }

    public void setNextToken(String nextToken) {
        NextToken = nextToken;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
