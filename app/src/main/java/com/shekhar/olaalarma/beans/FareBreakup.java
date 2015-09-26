package com.shekhar.olaalarma.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShekharKG on 9/26/2015.
 */
public class FareBreakup {

  @SerializedName("type")
  String type;
  @SerializedName("minimum_distance")
  String minimumDistance;
  @SerializedName("minimum_time")
  String minimumTime;
  @SerializedName("base_fare")
  String baseFare;
  @SerializedName("cost_per_distance")
  String costPerDistance;
  @SerializedName("waiting_cost_per_minute")
  String waitingCostPerMinute;
  @SerializedName("ride_cost_per_minute")
  String rideCostPerMinute;
  @SerializedName("surcharge")
  List<Surcharge> surcharges = new ArrayList<>();

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMinimumDistance() {
    return minimumDistance;
  }

  public void setMinimumDistance(String minimumDistance) {
    this.minimumDistance = minimumDistance;
  }

  public String getMinimumTime() {
    return minimumTime;
  }

  public void setMinimumTime(String minimumTime) {
    this.minimumTime = minimumTime;
  }

  public String getBaseFare() {
    return baseFare;
  }

  public void setBaseFare(String baseFare) {
    this.baseFare = baseFare;
  }

  public String getCostPerDistance() {
    return costPerDistance;
  }

  public void setCostPerDistance(String costPerDistance) {
    this.costPerDistance = costPerDistance;
  }

  public String getWaitingCostPerMinute() {
    return waitingCostPerMinute;
  }

  public void setWaitingCostPerMinute(String waitingCostPerMinute) {
    this.waitingCostPerMinute = waitingCostPerMinute;
  }

  public String getRideCostPerMinute() {
    return rideCostPerMinute;
  }

  public void setRideCostPerMinute(String rideCostPerMinute) {
    this.rideCostPerMinute = rideCostPerMinute;
  }

  public List<Surcharge> getSurcharges() {
    return surcharges;
  }

  public void setSurcharges(List<Surcharge> surcharges) {
    this.surcharges = surcharges;
  }
}
