package by.davlar.jdbc.dao;

import by.davlar.jdbc.entity.Address;
import by.davlar.jdbc.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AddressDao extends AbstractDao<Integer, Address> {
    private static final AddressDao INSTANCE = new AddressDao();

    private static final String SAVE_SQL = """
            INSERT INTO pizzeria.address
            (user_id, city, region, street, apartment) 
            VALUES (?, ?, ?, ?, ?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM pizzeria.address
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, user_id, city, region, street, apartment
             FROM pizzeria.address
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT id, user_id, city, region, street, apartment
             FROM pizzeria.address
             where id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE pizzeria.address
            SET user_id     = ?,
                city        = ?,
                region      = ?,
                street      = ?,
                apartment   = ?
            WHERE id = ?
            """;

    @Override
    protected String getSaveSql() {
        return SAVE_SQL;
    }

    @Override
    protected String getDeleteSql() {
        return DELETE_SQL;
    }

    @Override
    protected String getFindAllSql() {
        return FIND_ALL_SQL;
    }

    @Override
    protected String getFindDyIdSql() {
        return FIND_BY_ID_SQL;
    }

    @Override
    protected String getUpdateSql() {
        return UPDATE_SQL;
    }


    @Override
    protected void setPrimaryKey(Address entity, ResultSet keys) throws SQLException {
        entity.setId(keys.getInt("id"));
    }

    @Override
    protected void setParametersSave(PreparedStatement statement, Address entity) throws SQLException {
        setNoPrimaryParameters(statement, entity);
    }

    @Override
    protected void setParametersUpdate(PreparedStatement statement, Address entity) throws SQLException {
        setNoPrimaryParameters(statement, entity);
        statement.setInt(6, entity.getId());
    }

    private void setNoPrimaryParameters(PreparedStatement statement, Address entity) throws SQLException {
        statement.setInt(1, entity.getUserId());
        statement.setString(2, entity.getCity());
        statement.setString(3, entity.getRegion());
        statement.setString(4, entity.getStreet());
        statement.setString(5, entity.getApartment());
    }

    @Override
    protected Address buildEntity(ResultSet resultSet) throws SQLException {
        return Address.builder()
                .id(resultSet.getInt("id"))
                .userId(resultSet.getInt("user_id"))
                .city(resultSet.getString("city"))
                .region(resultSet.getString("region"))
                .street(resultSet.getString("street"))
                .apartment(resultSet.getString("apartment"))
                .build();
    }

    public static AddressDao getInstance() {
        return INSTANCE;
    }

    private AddressDao() {
    }
}
