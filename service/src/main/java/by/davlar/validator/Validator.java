package by.davlar.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
