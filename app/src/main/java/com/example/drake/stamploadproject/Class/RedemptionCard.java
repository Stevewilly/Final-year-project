package com.example.drake.stamploadproject.Class;

/**
 * Created by drake on 6/18/2017.
 */

public class RedemptionCard {
    public int cardID;
    public int Merchant_id;
    public String codeNumber;
    public String Qrcode;
    public int android_id;
    public String logoCard;
    public String endDate;
    public String startDate;
    public String rewards;
    public String name;

    public String getLogoCard() {
        return logoCard;
    }

    public void setLogoCard(String logoCard) {
        this.logoCard = logoCard;
    }

    public RedemptionCard(String name, String codeNumber,  String endDate, String Rewards, String Logocard){
 this.name= name;
    this.codeNumber = codeNumber;
    this.endDate = endDate;
    this.rewards = Rewards;
    this.logoCard = Logocard;
}


    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setMerchant_id(int merchant_id) {
        Merchant_id = merchant_id;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    public void setQrcode(String qrcode) {
        Qrcode = qrcode;
    }

    public void setAndroid_id(int android_id) {
        this.android_id = android_id;
    }

    public void setLogo(String logoCard) {
        this.logoCard = logoCard;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCardID(){
        return cardID;
    }
    public String getCodeNumber(){
        return codeNumber;
    }
    public String getQrcode(){
        return Qrcode;
    }
    public String getLogo(){
        return logoCard;
    }
    public String getEndDate(){
        return endDate;
    }
    public String getRewards(){
        return rewards;
    }
    public String getStartDate(){
        return startDate;
    }
    public String getName(){
        return name;
    }
    public int getMerchant_id(){
        return Merchant_id;
    }
    public int getAndroid_id(){
        return android_id;
    }

}
