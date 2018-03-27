package com.lcx.xml.serialize.model;

public class PaymentExecutionDetailItem {

    /**
     * 使用同级PaymentMethod响应元素指明的次级付款方式支付的金额。
     */
    private Money Payment;

    /**
     * COD 订单的次级付款方式。
    PaymentMethod 值：

    COD - 货到付款。仅适用于中国 (CN) 和日本 (JP)。
    GC - 礼品卡 仅适用于中国 (CN) 和日本 (JP)。
    PointsAccount - 亚马逊积分。仅适用于日本 (JP)。
     */
    private String PaymentMethod;

    public Money getPayment() {
        return Payment;
    }

    public void setPayment(Money payment) {
        Payment = payment;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "PaymentExecutionDetailItem [Payment=" + Payment + ", PaymentMethod=" + PaymentMethod + "]";
    }

}
