package routing.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import routing.model.User;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.lastModified IS NULL OR function('DATE', u.lastModified) < :today")
    List<User> findUneditedUsers(@Param("today") LocalDateTime today);

    @Query("SELECT u FROM User u WHERE u.lastNotification IS NULL OR function('DATE', u.lastNotification) < :today")
    List<User> findUnnotifiedUsers(@Param("today") LocalDateTime today);
}
