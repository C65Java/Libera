package by.my.auth.repository;

import by.my.auth.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
