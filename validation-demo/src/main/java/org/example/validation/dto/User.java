package org.example.validation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.validation.validation.CustomValidation;
import org.example.validation.validation.group.Add;
import org.example.validation.validation.group.Update;
import org.hibernate.validator.constraints.Range;

@Data
public class User {

  // 分组校验功能的使用
  // id 字段的分组是 Update.class，其它字段的分组是 Add.class，其余注解则未指定分组（表示均适用）。
  // 意思是要求：在新增时，name、age、email、phone 为必填字段；在更新时，id 为必填字段；
  // 而且不论新增还是更新，只要提供了对应的字段，就需要满足对应字段的校验规则。
  @NotNull(message = "id can not be null", groups = Update.class)
  private Long id;

  @NotNull(message = "name can not be null", groups = Add.class)
  @Size(min = 2, max = 20, message = "name length should be in the range [2, 20]")
  private String name;

  @NotNull(message = "age can not be null")
  @Range(min = 18, max = 100, message = "age should be in the range [18, 100]")
  private Integer age;

  @NotNull(message = "email can not be empty")
  @Email(message = "invalid email")
  private String email;

  @NotNull(message = "phone can not be null")
  @Pattern(regexp = "^1[3-9][0-9]{9}$", message = "phone number invalid")
  private String phone;

  // 若想对 address 字段应用校验规则，则需要额外在该字段上加一个 @Valid 注解
  @Valid
  @NotNull(message = "address can not be null")
  private Address address;

  @CustomValidation(message = "testFiled invalid")
  private String testField;
}
