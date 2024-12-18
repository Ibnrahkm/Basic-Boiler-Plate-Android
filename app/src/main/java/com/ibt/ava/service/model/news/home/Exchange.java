package com.ibt.ava.service.model.news.home;

import com.google.gson.annotations.SerializedName;

   
public class Exchange {

   @SerializedName("currency_from")
   String currencyFrom;

   @SerializedName("amount_from")
   String amountFrom;

   @SerializedName("currency_to")
   String currencyTo;

   @SerializedName("amount_to")
   String amountTo;


    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }
    public String getCurrencyFrom() {
        return currencyFrom;
    }
    
    public void setAmountFrom(String amountFrom) {
        this.amountFrom = amountFrom;
    }
    public String getAmountFrom() {
        return amountFrom;
    }
    
    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }
    public String getCurrencyTo() {
        return currencyTo;
    }
    
    public void setAmountTo(String amountTo) {
        this.amountTo = amountTo;
    }
    public String getAmountTo() {
        return amountTo;
    }
    
}