package com.example.alan.mapper;


import com.example.alan.model.User;

import java.util.List;

public interface UserMapper {

    List<User> list(int offset, int rows);

    long count();

    User getById(Long id);

    boolean existsByEmail(String email);

    List<User> searchByName(String name);

    void save(User user);

    void batchSave(List<User> users);

    void update(User user);

    void deleteById(Long id);
}
