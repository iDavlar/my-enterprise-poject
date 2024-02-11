package by.davlar.hibernate.utils;

import by.davlar.hibernate.dao.*;
import by.davlar.hibernate.entity.*;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Month;

@UtilityClass
public class TestDataImporter {

    private final RoleRepository ROLE_DAO = RoleRepository.getInstance();
    private final UserRepository USER_DAO = UserRepository.getInstance();
    private final AddressRepository ADDRESS_DAO = AddressRepository.getInstance();
    private final PizzaRepository PIZZA_DAO = PizzaRepository.getInstance();
    private final OrderRepository ORDER_DAO = OrderRepository.getInstance();
    private final OrderEntryRepository ORDER_ENTRY_DAO = OrderEntryRepository.getInstance();

    public void importData(SessionFactory sessionFactory) {

        @Cleanup var session = sessionFactory.openSession();

        // Roles
        Role admin = saveRole("ADMIN", true, session);
        Role commonUser = saveRole("USER", false, session);

        // Users
        User daniil = saveUser(
                "Даниил",
                "Ардюков",
                Date.valueOf("1997-11-28"),
                "Davlar",
                "123456",
                "88005553535",
                admin,
                session
        );

        User egor = saveUser(
                "Егор",
                "Носов",
                Date.valueOf("1990-01-30"),
                "EgorNos",
                "123456",
                "454647",
                commonUser,
                session
        );

        User gleb = saveUser(
                "Глеб",
                "Дмитриев",
                Date.valueOf("2002-07-15"),
                "GlebDmi",
                "123456",
                "+79023456789",
                commonUser,
                session
        );

        User anna = saveUser(
                "Анна",
                "Петрова",
                Date.valueOf("1980-01-10"),
                "PetrovAn",
                "123456",
                "332244",
                commonUser,
                session
        );

        User maria = saveUser(
                "Мария",
                "Павлова",
                Date.valueOf("1997-03-20"),
                "MashPaw",
                "123456",
                "89021234567",
                commonUser,
                session
        );

        // Addresses
        Address daniilAddress1 = saveAddress(
                "Актау",
                "",
                "3 - 9",
                "15",
                daniil,
                session
        );

        Address daniilAddress2 = saveAddress(
                "Братск",
                "Центральный",
                "Советская 10",
                "45",
                daniil,
                session
        );

        Address egorAddress1 = saveAddress(
                "Москва",
                "Центр",
                "Центральная 4",
                "15",
                egor,
                session
        );

        Address egorAddress2 = saveAddress(
                "Москва",
                "Центр",
                "Декабристов 7",
                "12",
                egor,
                session
        );

        Address glebAddress1 = saveAddress(
                "Санкт-Петербург",
                "",
                "Красноармейская 98",
                "15",
                gleb,
                session
        );

        Address annaAddress1 = saveAddress(
                "Санкт-Петербург",
                "",
                "Советская 5",
                "",
                anna,
                session
        );

        Address mariaAddress1 = saveAddress(
                "Екатеринбург",
                "",
                "Приморская 4",
                "88",
                maria,
                session
        );

        // Pizzas
        Pizza pizza4cheese = savePizza(
                "4 сыра",
                500,
                session
        );

        Pizza pizzaPepperoni = savePizza(
                "Пиперони",
                600,
                session
        );

        Pizza pizzaMushrooms = savePizza(
                "С грибами",
                425,
                session
        );

        Pizza pizzaHam = savePizza(
                "С ветчиной",
                715,
                session
        );

        Pizza pizzaHawaiian = savePizza(
                "Гавайская",
                1000,
                session
        );

        // Orders
        Order daniilOrder1 = saveOrder(
                daniil,
                daniilAddress1,
                LocalDateTime.of(2023, Month.OCTOBER, 1, 0, 0),
                session
        );

        Order daniilOrder2 = saveOrder(
                daniil,
                daniilAddress1,
                LocalDateTime.of(2023, Month.OCTOBER, 15, 0, 0),
                session
        );

        Order egorOrder1 = saveOrder(
                egor,
                egorAddress1,
                LocalDateTime.of(2023, Month.OCTOBER, 2, 0, 0),
                session
        );

        Order egorOrder2 = saveOrder(
                egor,
                egorAddress1,
                LocalDateTime.of(2023, Month.OCTOBER, 11, 0, 0),
                session
        );

        Order egorOrder3 = saveOrder(
                egor,
                egorAddress2,
                LocalDateTime.of(2023, Month.OCTOBER, 17, 0, 0),
                session
        );

        Order glebOrder1 = saveOrder(
                gleb,
                glebAddress1,
                LocalDateTime.of(2023, Month.OCTOBER, 1, 0, 0),
                session
        );

        Order glebOrder2 = saveOrder(
                gleb,
                glebAddress1,
                LocalDateTime.of(2023, Month.OCTOBER, 14, 0, 0),
                session
        );

        Order annaOrder1 = saveOrder(
                anna,
                annaAddress1,
                LocalDateTime.of(2023, Month.OCTOBER, 7, 0, 0),
                session
        );

        Order mariaOrder1 = saveOrder(
                maria,
                mariaAddress1,
                LocalDateTime.of(2023, Month.OCTOBER, 7, 0, 0),
                session
        );

        // Order`s entities

        // Daniil`s
        saveOrderEntry(daniilOrder1, pizza4cheese, 1, session);
        saveOrderEntry(daniilOrder1, pizzaHam, 1, session);

        saveOrderEntry(daniilOrder2, pizzaHawaiian, 1, session);

        // Egor`s
        saveOrderEntry(egorOrder1, pizzaMushrooms, 3, session);

        saveOrderEntry(egorOrder2, pizza4cheese, 1, session);
        saveOrderEntry(egorOrder2, pizzaPepperoni, 1, session);
        saveOrderEntry(egorOrder2, pizzaMushrooms, 1, session);
        saveOrderEntry(egorOrder2, pizzaHam, 1, session);
        saveOrderEntry(egorOrder2, pizzaHawaiian, 1, session);

        saveOrderEntry(egorOrder3, pizza4cheese, 3, session);

        // Gleb`s
        saveOrderEntry(glebOrder1, pizzaMushrooms, 2, session);

        saveOrderEntry(glebOrder2, pizzaPepperoni, 1, session);

        // Anna`s
        saveOrderEntry(annaOrder1, pizzaHawaiian, 1, session);

        // Maria`s
        saveOrderEntry(mariaOrder1, pizza4cheese, 4, session);
    }

    private Role saveRole(String name,
                          Boolean isAdmin,
                          Session session) {
        Role user = Role.builder()
                .name(name)
                .isAdmin(isAdmin)
                .build();

        return ROLE_DAO.save(user, session);
    }

    private User saveUser(String firstName,
                          String lastName,
                          Date birthday,
                          String login,
                          String password,
                          String telephone,
                          Role role,
                          Session session) {
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthday(birthday)
                .login(login)
                .password(password)
                .telephone(telephone)
                .role(role)
                .build();

        return USER_DAO.save(user, session);
    }

    private Address saveAddress(String city,
                                String region,
                                String street,
                                String apartment,
                                User user,
                                Session session) {
        Address address = Address.builder()
                .city(city)
                .region(region)
                .street(street)
                .apartment(apartment)
                .user(user)
                .build();

        return ADDRESS_DAO.save(address, session);
    }

    private Pizza savePizza(String name,
                            Integer cost,
                            Session session) {
        Pizza pizza = Pizza.builder()
                .name(name)
                .cost(cost)
                .build();

        return PIZZA_DAO.save(pizza, session);
    }

    private Order saveOrder(User user,
                            Address address,
                            LocalDateTime date,
                            Session session) {
        Order order = Order.builder()
                .user(user)
                .address(address)
                .date(date)
                .build();

        return ORDER_DAO.save(order, session);
    }

    private OrderEntry saveOrderEntry(Order order,
                                      Pizza pizza,
                                      Integer amount,
                                      Session session) {
        OrderEntry orderEntry = OrderEntry.builder()
                .order(order)
                .pizza(pizza)
                .amount(amount)
                .build();

        return ORDER_ENTRY_DAO.save(orderEntry, session);
    }
}
