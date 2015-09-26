package com.shekhar.olaalarma.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShekharKG on 9/26/2015.
 */
public class Category {

  @SerializedName("id")
  String cabCategoryId;
  @SerializedName("display_name")
  String getCabCategoryName;
  @SerializedName("currency")
  String currencyUnit;
  @SerializedName("distance_units")
  String distanceUnit;
  @SerializedName("time_units")
  String timeUnits;
  @SerializedName("eta")
  String estimatedArrivalTime;
  @SerializedName("distance")
  String cabDistance;
  @SerializedName("image")
  String cabLogo;
  @SerializedName("fare_breakup")
  List<FareBreakup> fareBreakups = new ArrayList<>();

  public String getCabCategoryId() {
    return cabCategoryId;
  }

  public void setCabCategoryId(String cabCategoryId) {
    this.cabCategoryId = cabCategoryId;
  }

  public String getGetCabCategoryName() {
    return getCabCategoryName;
  }

  public void setGetCabCategoryName(String getCabCategoryName) {
    this.getCabCategoryName = getCabCategoryName;
  }

  public String getCurrencyUnit() {
    return currencyUnit;
  }

  public void setCurrencyUnit(String currencyUnit) {
    this.currencyUnit = currencyUnit;
  }

  public String getDistanceUnit() {
    return distanceUnit;
  }

  public void setDistanceUnit(String distanceUnit) {
    this.distanceUnit = distanceUnit;
  }

  public String getTimeUnits() {
    return timeUnits;
  }

  public void setTimeUnits(String timeUnits) {
    this.timeUnits = timeUnits;
  }

  public String getEstimatedArrivalTime() {
    return estimatedArrivalTime;
  }

  public void setEstimatedArrivalTime(String estimatedArrivalTime) {
    this.estimatedArrivalTime = estimatedArrivalTime;
  }

  public String getCabDistance() {
    return cabDistance;
  }

  public void setCabDistance(String cabDistance) {
    this.cabDistance = cabDistance;
  }

  public String getCabLogo() {
    return cabLogo;
  }

  public void setCabLogo(String cabLogo) {
    this.cabLogo = cabLogo;
  }

  public List<FareBreakup> getFareBreakups() {
    return fareBreakups;
  }

  public void setFareBreakups(List<FareBreakup> fareBreakups) {
    this.fareBreakups = fareBreakups;
  }
}
