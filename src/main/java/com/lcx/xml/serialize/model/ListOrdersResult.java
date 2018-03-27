package com.lcx.xml.serialize.model;

import java.util.ArrayList;

public class ListOrdersResult {

    private ArrayList<Order> Orders;

    private String CreatedBefore;

    private String NextToken;

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ArrayList<Order> getOrders() {
        return Orders;
    }

    public void setOrders(ArrayList<Order> orders) {
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
