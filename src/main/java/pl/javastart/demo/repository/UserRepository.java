package pl.javastart.demo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.javastart.demo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByName(String name);

//    List<User> findAllByName(String name, Sort sort);

    //Nie dziala na native query
    //@Query(value = "SELECT u FROM User u", nativeQuery = true)
    //List<User> finaAllJpql(Sort sort);

//    @Query("SELECT u FROM User u")
//    List<User> finaAllJpql(Sort sort, Pageable pageable);

//    List<User> findFirst4OrderByName();
}
