package com.lcx.xml.serialize.model;

public class Address {

    /**
     * 名称
     */
    private String Name;

    /**
     * 街道地址
     */
    private String AddressLine1;

    /**
     * 其他街道地址信息（如果需要）
     */
    private String AddressLine2;

    /**
     * 其他街道地址信息（如果需要）
     */
    private String AddressLine3;

    /**
     * 城市
     */
    private String City;

    /**
     * 区县
     */
    private String County;

    /**
     * 区
     */
    private String District;

    /**
     * 省/自治区/直辖市或地区
     */
    private String StateOrRegion;

    /**
     * 邮政编码
     */
    private String PostalCode;

    /**
     * 两位数国家/地区代码。格式为 ISO 3166-1-alpha 2
     */
    private String CountryCode;

    /**
     * 电话号码
     */
    private String Phone;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddressLine1() {
        return AddressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        AddressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return AddressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        AddressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return AddressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        AddressLine3 = addressLine3;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getStateOrRegion() {
        return StateOrRegion;
    }

    public void setStateOrRegion(String stateOrRegion) {
        StateOrRegion = stateOrRegion;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    @Override
    public String toString() {
        return "Address [Name=" + Name + ", AddressLine1=" + AddressLine1 + ", AddressLine2=" + AddressLine2
                + ", AddressLine3=" + AddressLine3 + ", City=" + City + ", County=" + County + ", District=" + District
                + ", StateOrRegion=" + StateOrRegion + ", PostalCode=" + PostalCode + ", CountryCode=" + CountryCode
                + ", Phone=" + Phone + "]";
    }

}
