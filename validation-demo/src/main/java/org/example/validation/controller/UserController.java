package org.example.validation.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.example.validation.dto.User;
import org.example.validation.model.ErrorMessage;
import org.example.validation.validation.group.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

  //  方式1:使用一个额外的参数 BindingResult
  // 来接收校验错误信息，然后根据需要展示给调用者。但这种处理方式有点太冗余了，每个请求方法都需要加这么一个参数并重新写一遍错误返回的逻辑。
  //
  // BindingResult，该参数会捕获所有的字段校验错误信息，本文仅是将其中的第一个错误按照 ErrorMessage 格式返回了出来，没有任何错误信息则会返回 201 状态码
  // curl -X POST http://localhost:8081/v1/users \
  //        -H "Content-Type: application/json" \
  //        -d '{
  //        "name": "John Doe",
  //        "age": 30,
  //        "email": "john.doe@example.com",
  //        "phone": "13800138000",
  //        "address": {
  //        "province": "California",
  //        "city": "Los Angeles",
  //        "street": "123 Main Street"
  //        }
  //        }'
  @PostMapping
  public ResponseEntity<?> addUser(@RequestBody @Valid User user, BindingResult result) {
    if (result.hasErrors()) {
      List<ObjectError> allErrors = result.getAllErrors();
      if (!allErrors.isEmpty()) {
        ObjectError objectError = allErrors.get(0);
        String message = objectError.getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorMessage("validation_failed", message));
      }
    }

    // userService.addUser(user)
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  //  方式2:不加 BindingResult 参数的话，若有校验错误，Spring Boot 框架会抛出一个MethodArgumentNotValidException。
  //  所以简单一点的处理方式是：使用 @RestControllerAdvice 注解来将一个类标记为全局的异常处理类，
  //  针对 MethodArgumentNotValidException，只需要在这个异常处理类中进行统一捕获、统一处理就可以了。
  @PostMapping("/user")
  public ResponseEntity<?> addUser(@RequestBody @Valid User user) {
    // userService.addUser(user)
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // 演示：分组校验功能的使用
  // - @Validated 注解是 Spring 框架自带的，可以指定分组
  // - @Valid 注解是 jakarta.validation 包下的，没有分组这个功能。
  @PatchMapping("/user")
  public ResponseEntity<?> updateUser(@RequestBody @Validated(Update.class) User user) {
    // userService.addUser(user)
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}

// curl -X PATCH http://localhost:8081/v1/users/user \
//        -H "Content-Type: application/json" \
//        -d '{
//        "id": 1,
//        "name": "John Doe",
//        "age": 30,
//        "email": "john.doe@example.com",
//        "phone": "13800138000",
//        "address": {
//        "province": "California",
//        "city": "Los Angeles",
//        "street": "123 Main Street"
//        }
//        }'
