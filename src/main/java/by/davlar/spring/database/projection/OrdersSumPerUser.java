package by.davlar.spring.database.projection;


import by.davlar.spring.database.entity.User;

public record OrdersSumPerUser(
        User user,
        Long sum
) {

}
