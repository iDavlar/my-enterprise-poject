package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressDao extends AbstractDao<Integer, Address> {
    private static final AddressDao INSTANCE = new AddressDao();

    public static AddressDao getInstance() {
        return INSTANCE;
    }
}
