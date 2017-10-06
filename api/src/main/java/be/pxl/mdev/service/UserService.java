package be.pxl.mdev.service;

import be.pxl.mdev.entity.User;
import be.pxl.mdev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by kevin on 02/10/2017.
 */

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository repo;

    @Override
    public User find(String username) {
        return repo.findOne(username);
    }

    @Override
    public List<User> all() {
        return repo.findAll();
    }

    @Override
    public void persist(User user) {
        repo.save(user);
    }

    @Override
    public void update(User user) {
        repo.save(user);
    }
}
