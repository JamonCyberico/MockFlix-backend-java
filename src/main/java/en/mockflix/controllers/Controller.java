package en.mockflix.controllers;

import en.mockflix.entities.Address;
import en.mockflix.entities.Contact;
import en.mockflix.entities.Role;
import en.mockflix.entities.User;
import en.mockflix.exceptions.UserNotFoundException;
import en.mockflix.repositories.AddressRepository;
import en.mockflix.repositories.ContactRepository;
import en.mockflix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;


    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }

    @GetMapping("/contacts")
    public List<Contact> getAllContacts(){
        return contactRepository.getAllContacts();
    }

    @GetMapping("/addresses")
    public List<Address> getAllAddresses(){
        return addressRepository.getAllAddresses();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long id) throws UserNotFoundException {
        try {
            User user = userRepository.getUserById(id);
            userRepository.deleteUserById(id);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            throw new UserNotFoundException("No user is found with id: " + id);
        }
    }

    // add user with username and password
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = RequestMethod.POST, allowCredentials = "true", maxAge = 3600, exposedHeaders = "*")
    @PostMapping("/register")
    public User addUser(String username, String password){
        // check if user already exists
        User user = userRepository.getUserByUsername(username);
        if (user != null) {
            return null;
        } else {
            return userRepository.register(username, password);
        }
    }

    // add role to user
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/add-role")
    public User addRole(String username, String role){
        return userRepository.addRole(username, role);
    }

    // create a contact
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/add-contact")
    public User addContact(String username, String email, String firstName, String lastName, String phoneNumber){
        return userRepository.addContact(username, email, firstName, lastName, phoneNumber);
    }

    // create an address
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/add-address")
    public User addAddress(String username, String country, String area, String city, String street, String number){
        return userRepository.addAddress(username, country, area, city, street, number);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password) throws UserNotFoundException {
        try {
            User user = userRepository.getUserByUsername(username);
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                throw new UserNotFoundException("No user is found with username: " + username);
            }
        } catch (Exception e) {
            throw new UserNotFoundException("No user is found with username: " + username);
        }
    }

}
