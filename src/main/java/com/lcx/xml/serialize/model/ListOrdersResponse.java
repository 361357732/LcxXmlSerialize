package com.lcx.xml.serialize.model;

public class ListOrdersResponse {

    private ListOrdersResult ListOrdersResult;

    public ListOrdersResult getListOrdersResult() {
        return ListOrdersResult;
    }

    public void setListOrdersResult(ListOrdersResult listOrdersResult) {
        ListOrdersResult = listOrdersResult;
    }

    @Override
    public String toString() {
        return "ListOrdersResponse [ListOrdersResult=" + ListOrdersResult + "]";
    }

}
