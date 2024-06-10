package by.davlar.spring.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
