package by.davlar.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {

    private static final String NAME_MASK = "^[A-Za-zА-Яа-я]+$";

    private static final String LOGIN_MASK_1 = "^\\w{5,12}$";

    private static final String LOGIN_MASK_2 = "^[A-Za-z].*$";

    private static final String PASSWORD_MASK = "^[\\w!?]{6,12}$";

    @NotNull(message = "Укажите имя")
    @NotEmpty(message = "Укажите имя")
    @Pattern(regexp = NAME_MASK, message = "Имя должно состоять только из букв")
    String firstName;
    @NotNull(message = "Укажите фамилию")
    @NotEmpty(message = "Укажите фамилию")
    String lastName;
    @NotNull(message = "Укажите дату рождения")
    @NotEmpty(message = "Укажите дату рождения")
    String birthday;
    @NotNull(message = "Укажите логин")
    @NotEmpty(message = "Укажите логин")
    @Pattern(regexp = LOGIN_MASK_1, message = "Логин должен состоять из 5 - 12 латинских букв, цифр и знака '_'")
    @Pattern(regexp = LOGIN_MASK_2, message = "Логин должен начинаться с буквы")
    String login;
    @NotNull(message = "Укажите пароль")
    @NotEmpty(message = "Укажите пароль")
    @Pattern(regexp = PASSWORD_MASK, message = "Пароль должен состоять из 6 - 12 латинских букв, цифр и символов '_!?'")
    String password;
    String telephone;
    @NotNull
    @NotEmpty
    String role;
}
