package com.lcx.xml.serialize.model;

public class Order {

    /**
     * 亚马逊所定义的订单编码，格式为 3-7-7。
     */
    private String AmazonOrderId;

    /**
     * 卖家所定义的订单编码。
     */
    private String SellerOrderId;

    /**
     * 创建订单的日期。
     */
    private String PurchaseDate;

    /**
     * 订单的最后更新日期。
     */
    private String LastUpdateDate;

    /**
     * 当前的订单状态。
     */
    private String OrderStatus;

    /**
     * 订单配送方式：亚马逊配送 (AFN) 或卖家自行配送 (MFN)。
     */
    private String FulfillmentChannel;

    /**
     * 订单中第一件商品的销售渠道。
     */
    private String SalesChannel;

    /**
     * 订单中第一件商品的订单渠道。
     */
    private String OrderChannel;

    /**
     * 货件服务水平。
     */
    private String ShipServiceLevel;

    /**
     * 订单的配送地址。
     */
    private Address ShippingAddress;

    /**
     * 订单的总费用。
     */
    private Money OrderTotal;

    /**
     * 已配送的商品数量。
     */
    private int NumberOfItemsShipped;

    /**
     * 未配送的商品数量。
     */
    private int NumberOfItemsUnshipped;

    private PaymentMethodDetails PaymentMethodDetails;

    /**
     * 货到付款 (COD) 订单的次级付款方式的相关信息
     */
    private PaymentExecutionDetailItem PaymentExecutionDetail;

    private boolean IsReplacementOrder;

    /**
     * 订单的主要付款方式。
    PaymentMethod 值：

    COD - 货到付款。仅适用于中国 (CN) 和日本 (JP)。
    CVS - 便利店。仅适用于日本 (JP)。
    Other - COD 和 CVS 之外的付款方式。
     */
    private String PaymentMethod;

    /**
     * 订单生成所在商城的匿名编码。
     */
    private String MarketplaceId;

    /**
     * 买家的匿名电子邮件地址。
     */
    private String BuyerEmail;

    /**
     * 买家姓名。
     */
    private String BuyerName;

    /**
     * 订单的配送服务级别分类。
    ShipmentServiceLevelCategory 值：

    Expedited
    FreeEconomy
    NextDay
    SameDay
    SecondDay
    Scheduled
    Standard
     */
    private String ShipmentServiceLevelCategory;

    /**
     * 指明订单配送方是否是亚马逊配送 (Amazon TFM) 服务。
     */
    private String ShippedByAmazonTFM;

    /**
     * 亚马逊 TFM订单的状态。仅当ShippedByAmazonTFM = True时返回。请注意：即使当 ShippedByAmazonTFM = True 时，如果您还没有创建货件，也不会返回 TFMShipmentStatus。
    TFMShipmentStatus 值：

    PendingPickUp
    LabelCanceled
    PickedUp
    AtDestinationFC
    Delivered
    RejectedByBuyer
    Undeliverable
    ReturnedToSeller
     */
    private String TFMShipmentStatus;

    /**
     * 卖家自定义的配送方式，属于Checkout by Amazon (CBA) 所支持的四种标准配送设置中的一种。
     */
    private String CbaDisplayableShippingLabel;

    /**
     * 订单类型。
    OrderType 值：

    StandardOrder - 包含当前有库存商品的订单。
    Preorder -所含预售商品（发布日期晚于当前日期）的订单。
     */
    private String OrderType;

    /**
     * 您承诺的订单发货时间范围的第一天。日期格式为 ISO 8601。
     */
    private String EarliestShipDate;

    /**
     * 您承诺的订单发货时间范围的最后一天。日期格式为 ISO 8601。
     */
    private String LatestShipDate;

    /**
     * 您承诺的订单送达时间范围的第一天。日期格式为 ISO 8601。
     */
    private String EarliestDeliveryDate;

    /**
     * 您承诺的订单送达时间范围的最后一天。日期格式为 ISO 8601。
     */
    private String LatestDeliveryDate;

    private boolean IsBusinessOrder;

    private boolean IsPremiumOrder;

    private boolean IsPrime;

    public boolean isIsPrime() {
        return IsPrime;
    }

    public void setIsPrime(boolean isPrime) {
        IsPrime = isPrime;
    }

    public boolean isIsPremiumOrder() {
        return IsPremiumOrder;
    }

    public void setIsPremiumOrder(boolean isPremiumOrder) {
        IsPremiumOrder = isPremiumOrder;
    }

    public boolean isIsBusinessOrder() {
        return IsBusinessOrder;
    }

    public void setIsBusinessOrder(boolean isBusinessOrder) {
        IsBusinessOrder = isBusinessOrder;
    }

    public String getAmazonOrderId() {
        return AmazonOrderId;
    }

    public void setAmazonOrderId(String amazonOrderId) {
        AmazonOrderId = amazonOrderId;
    }

    public String getSellerOrderId() {
        return SellerOrderId;
    }

    public void setSellerOrderId(String sellerOrderId) {
        SellerOrderId = sellerOrderId;
    }

    public String getPurchaseDate() {
        return PurchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        PurchaseDate = purchaseDate;
    }

    public String getLastUpdateDate() {
        return LastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        LastUpdateDate = lastUpdateDate;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getFulfillmentChannel() {
        return FulfillmentChannel;
    }

    public void setFulfillmentChannel(String fulfillmentChannel) {
        FulfillmentChannel = fulfillmentChannel;
    }

    public String getSalesChannel() {
        return SalesChannel;
    }

    public void setSalesChannel(String salesChannel) {
        SalesChannel = salesChannel;
    }

    public String getOrderChannel() {
        return OrderChannel;
    }

    public void setOrderChannel(String orderChannel) {
        OrderChannel = orderChannel;
    }

    public String getShipServiceLevel() {
        return ShipServiceLevel;
    }

    public void setShipServiceLevel(String shipServiceLevel) {
        ShipServiceLevel = shipServiceLevel;
    }

    public Address getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public Money getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(Money orderTotal) {
        OrderTotal = orderTotal;
    }

    public int getNumberOfItemsShipped() {
        return NumberOfItemsShipped;
    }

    public void setNumberOfItemsShipped(int numberOfItemsShipped) {
        NumberOfItemsShipped = numberOfItemsShipped;
    }

    public int getNumberOfItemsUnshipped() {
        return NumberOfItemsUnshipped;
    }

    public void setNumberOfItemsUnshipped(int numberOfItemsUnshipped) {
        NumberOfItemsUnshipped = numberOfItemsUnshipped;
    }

    public PaymentExecutionDetailItem getPaymentExecutionDetail() {
        return PaymentExecutionDetail;
    }

    public void setPaymentExecutionDetail(PaymentExecutionDetailItem paymentExecutionDetail) {
        PaymentExecutionDetail = paymentExecutionDetail;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getMarketplaceId() {
        return MarketplaceId;
    }

    public void setMarketplaceId(String marketplaceId) {
        MarketplaceId = marketplaceId;
    }

    public String getBuyerEmail() {
        return BuyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        BuyerEmail = buyerEmail;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getShipmentServiceLevelCategory() {
        return ShipmentServiceLevelCategory;
    }

    public void setShipmentServiceLevelCategory(String shipmentServiceLevelCategory) {
        ShipmentServiceLevelCategory = shipmentServiceLevelCategory;
    }

    public String getShippedByAmazonTFM() {
        return ShippedByAmazonTFM;
    }

    public void setShippedByAmazonTFM(String shippedByAmazonTFM) {
        ShippedByAmazonTFM = shippedByAmazonTFM;
    }

    public String getTFMShipmentStatus() {
        return TFMShipmentStatus;
    }

    public void setTFMShipmentStatus(String tFMShipmentStatus) {
        TFMShipmentStatus = tFMShipmentStatus;
    }

    public String getCbaDisplayableShippingLabel() {
        return CbaDisplayableShippingLabel;
    }

    public void setCbaDisplayableShippingLabel(String cbaDisplayableShippingLabel) {
        CbaDisplayableShippingLabel = cbaDisplayableShippingLabel;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getEarliestShipDate() {
        return EarliestShipDate;
    }

    public void setEarliestShipDate(String earliestShipDate) {
        EarliestShipDate = earliestShipDate;
    }

    public String getLatestShipDate() {
        return LatestShipDate;
    }

    public void setLatestShipDate(String latestShipDate) {
        LatestShipDate = latestShipDate;
    }

    public String getEarliestDeliveryDate() {
        return EarliestDeliveryDate;
    }

    public void setEarliestDeliveryDate(String earliestDeliveryDate) {
        EarliestDeliveryDate = earliestDeliveryDate;
    }

    public String getLatestDeliveryDate() {
        return LatestDeliveryDate;
    }

    public void setLatestDeliveryDate(String latestDeliveryDate) {
        LatestDeliveryDate = latestDeliveryDate;
    }

    public PaymentMethodDetails getPaymentMethodDetails() {
        return PaymentMethodDetails;
    }

    public void setPaymentMethodDetails(PaymentMethodDetails paymentMethodDetails) {
        PaymentMethodDetails = paymentMethodDetails;
    }

    public boolean isIsReplacementOrder() {
        return IsReplacementOrder;
    }

    public void setIsReplacementOrder(boolean isReplacementOrder) {
        IsReplacementOrder = isReplacementOrder;
    }

}
