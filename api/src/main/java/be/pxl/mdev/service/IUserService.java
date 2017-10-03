package be.pxl.mdev.service;

import be.pxl.mdev.entity.User;

import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */
public interface IUserService {

    public User find(String username);
    public List<User> all();
}
