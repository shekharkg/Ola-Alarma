package com.shekhar.olaalarma.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShekharKG on 9/26/2015.
 */
public class Categories {

  @SerializedName("categories")
  List<Category> categories = new ArrayList<>();

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }
}
