package org.example.validation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

// 测试嵌套对象的校验
@Data
public class Address {

  @NotNull(message = "province can not be null")
  @Size(min = 2, max = 100, message = "province length should be in the range [10, 100]")
  private String province;

  @NotNull(message = "city can not be null")
  @Size(min = 2, max = 100, message = "city length should be in the range [10, 100]")
  private String city;

  @NotNull(message = "street can not be null")
  @Size(min = 10, max = 1000, message = "street length should be in the range [10, 1000]")
  private String street;
}
