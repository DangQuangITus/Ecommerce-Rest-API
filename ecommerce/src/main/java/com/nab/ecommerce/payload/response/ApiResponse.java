package com.nab.ecommerce.payload.response;

import com.nab.ecommerce.common.BaseEntity;

public class ApiResponse extends BaseEntity {

  private Boolean success;
  private String message;

  public ApiResponse(Boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
