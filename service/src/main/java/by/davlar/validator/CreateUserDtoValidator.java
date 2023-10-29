package by.davlar.validator;

import by.davlar.dto.CreateUserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserDtoValidator implements Validator<CreateUserDto> {
    private static final CreateUserDtoValidator INSTANCE = new CreateUserDtoValidator();

    private static final String NAME_MASK = "^[A-Za-zА-Яа-я]+$";

    private static final String LOGIN_MASK_1 = "^\\w{5,12}$";

    private static final String LOGIN_MASK_2 = "^[A-Za-z].*$";

    private static final String PASSWORD_MASK = "^[\\w!?]{6,12}$";

    public static CreateUserDtoValidator getInstance() {
        return INSTANCE;
    }

    public ValidationResult isValid(CreateUserDto userDto) {
        var validationResult = new ValidationResult();

        validateName(userDto, validationResult);
        validateLogin(userDto, validationResult);
        validatePassword(userDto, validationResult);

        return validationResult;
    }

    public void validateName(CreateUserDto userDto, ValidationResult validationResult) {
        boolean isValid = true;
        String error = "";
        String value = userDto.getFirstName();

        if (value == null
            || value.isEmpty()) {
            error = "Имя не должно быть пустым";
            isValid = false;
        } else if (!value.matches(NAME_MASK)) {
            error = "Имя должно состоять только из букв";
            isValid = false;
        }

        if (!isValid) {
            validationResult.add(Error.of("name", error));
        }
    }

    public void validateLogin(CreateUserDto userDto, ValidationResult validationResult) {
        boolean isValid = true;
        String error = "";
        String value = userDto.getLogin();

        if (value == null
            || value.isEmpty()) {
            error = "Укажите логин";
            isValid = false;
        } else if (!value.matches(LOGIN_MASK_1)) {
            error = "Логин должен состоять из 5 - 12 латинских букв, цифр и знака '_'";
            isValid = false;
        } else if (!value.matches(LOGIN_MASK_2)) {
            error = "Логин должен начинаться с буквы";
            isValid = false;
        }

        if (!isValid) {
            validationResult.add(Error.of("login", error));
        }
    }

    public void validatePassword(CreateUserDto userDto, ValidationResult validationResult) {
        boolean isValid = true;
        String error = "";
        String value = userDto.getPassword();

        if (value == null
            || value.isEmpty()) {
            error = "Укажите пароль";
            isValid = false;
        } else if (!value.matches(PASSWORD_MASK)) {
            error = "Пароль должен состоять из 6 - 12 латинских букв, цифр и символов '_!?'";
            isValid = false;
        }

        if (!isValid) {
            validationResult.add(Error.of("password", error));
        }
    }
}
