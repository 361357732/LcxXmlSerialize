package com.lcx.xml.serialize.model;

import java.util.List;

public class ListOrdersResult {

    private List<Order> Orders;

    private String CreatedBefore;

    private String NextToken;

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
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

    @Override
    public String toString() {
        return "ListOrdersResult [Orders=" + Orders + ", CreatedBefore=" + CreatedBefore + ", NextToken=" + NextToken
                + "]";
    }

}
