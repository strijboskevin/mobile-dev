package be.pxl.mdev.repository;

import be.pxl.mdev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by kevin on 02/10/2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
