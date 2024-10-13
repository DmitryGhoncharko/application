package by.ghoncharko.application.repository;


import by.ghoncharko.application.entity.BannedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannedUserRepository extends JpaRepository<BannedUser, Long> {
    Optional<BannedUser> findByUser_UserLogin(String userLogin);
}
