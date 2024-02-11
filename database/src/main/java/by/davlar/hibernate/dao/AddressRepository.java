package by.davlar.hibernate.dao;

import by.davlar.hibernate.entity.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressRepository extends BaseRepository<Integer, Address> {
    private static final AddressRepository INSTANCE = new AddressRepository();

    public static AddressRepository getInstance() {
        return INSTANCE;
    }
}
