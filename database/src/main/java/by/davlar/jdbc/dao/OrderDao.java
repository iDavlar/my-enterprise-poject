package by.davlar.jdbc.dao;

import by.davlar.jdbc.entity.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderDao extends AbstractDao<Integer, Order> {
    private static final OrderDao INSTANCE = new OrderDao();

    private static final String SAVE_SQL = """
            INSERT INTO pizzeria.orders
            (user_id, address_id, data) 
            VALUES (?, ?, ?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM pizzeria.orders
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, user_id, address_id, data
             FROM pizzeria.orders
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT id, user_id, address_id, data
             FROM pizzeria.orders
             where id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE pizzeria.orders
            SET user_id     = ?,
                address_id  = ?,
                data        = ?
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
    protected void setPrimaryKey(Order entity, ResultSet keys) throws SQLException {
        entity.setId(keys.getInt("id"));
    }

    @Override
    protected void setParametersSave(PreparedStatement statement, Order entity) throws SQLException {
        setNoPrimaryParameters(statement, entity);
    }

    @Override
    protected void setParametersUpdate(PreparedStatement statement, Order entity) throws SQLException {
        setNoPrimaryParameters(statement, entity);
        statement.setInt(4, entity.getId());
    }

    private void setNoPrimaryParameters(PreparedStatement statement, Order entity) throws SQLException {
        statement.setInt(1, entity.getUserId());
        statement.setInt(2, entity.getAddressId());
        statement.setTimestamp(3, entity.getDate());
    }

    @Override
    protected Order buildEntity(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .id(resultSet.getInt("id"))
                .userId(resultSet.getInt("user_id"))
                .addressId(resultSet.getInt("address_id"))
                .date(resultSet.getTimestamp("data"))
                .build();
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    private OrderDao() {
    }
}
