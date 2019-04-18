package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRequestDTO;
import codesquad.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class ApiUserController {

    private UserService userService;

    public ApiUserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> create(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        User user = userService.save(userRequestDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/users/" + user.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> login(@Valid @RequestBody UserRequestDTO UserR) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
