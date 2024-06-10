package by.davlar.spring.mapper;

public interface Mapper<T, F> {
    T mapFrom(F f);
}
