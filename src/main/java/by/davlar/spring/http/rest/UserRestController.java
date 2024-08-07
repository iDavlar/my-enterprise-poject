package by.davlar.spring.http.rest;

import by.davlar.spring.dto.UserCreateEditDto;
import by.davlar.spring.dto.UserReadDto;
import by.davlar.spring.dto.filter.UserFilter;
import by.davlar.spring.http.utils.UrlPath;
import by.davlar.spring.service.RoleService;
import by.davlar.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/v2/")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping(UrlPath.ALL_USERS)
    public List<UserReadDto> findAll(UserFilter filter,
                                     Sort sort) {

        return userService.findAll(filter, sort);
    }

    @GetMapping(UrlPath.USER_ID)
    public UserReadDto findById(@PathVariable("id") Integer id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/users/{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Integer id) {
        return userService.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }
    @PostMapping(UrlPath.REGISTRATION)
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto registration(@RequestBody @Validated UserCreateEditDto userCreateEditDto) {
        return userService.create(userCreateEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @PutMapping(UrlPath.USER_ID)
    public UserReadDto update(@PathVariable("id") Integer id,
                              @RequestBody @Validated UserCreateEditDto userCreateEditDto) {

        return userService.update(id, userCreateEditDto);
    }

    @DeleteMapping(UrlPath.USER_ID)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return userService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
