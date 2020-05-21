package pl.javastart.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.javastart.demo.model.User;
import pl.javastart.demo.repository.UserRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @ResponseBody
//    @GetMapping("/user")
//    void home(@RequestParam String sortBy) {
////        Optional<User> user = userRepository.findByName("Marcin");
////        user.ifPresent(System.out::println);
//
////        Sort by = Sort.by(sortBy).ascending().and(Sort.by("lastName").descending());
////        userRepository.findAll(by);
////        userRepository.findAllByName("name", by);
//
//        PageRequest of = PageRequest.of(0, 20);
//        userRepository.findAll(of);
//    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    User getOne(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElse(new User());
    }

    @GetMapping("/users")
    List<User> findAll(@RequestParam(required = false) String sortBy) {
        if (sortBy != null) {
            Sort by = Sort.by(sortBy);
            return userRepository.findAll(by);
        }
        return userRepository.findAll();
    }

    @PostMapping("/users")
    ResponseEntity<User> saveUser(@RequestBody User user) {
        User userFromDatabase = userRepository.save(user);
        URI uri = UriComponentsBuilder
                .fromPath("/users/{id}")
                .buildAndExpand(userFromDatabase.getId())
                .toUri();
        return ResponseEntity
                .created(uri)
                .build();
    }

    @PutMapping("/users/{id}")
    void update(@PathVariable Long id, @RequestBody User user) {
        User userToSave = userRepository.findById(id)
                .map(userFromDatabase -> updateUser(userFromDatabase, user))
                .orElse(user);
        userRepository.save(userToSave);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    private User updateUser(User userFromDatabase, User userFromForm) {
        userFromDatabase.setFirstName(userFromForm.getFirstName());
        userFromDatabase.setLastName(userFromForm.getLastName());
        return userFromDatabase;
    }
}
