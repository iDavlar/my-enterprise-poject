package by.davlar.jdbc.dao;

import by.davlar.jdbc.entity.OrderEntry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderEntryDao extends AbstractDao<Integer, OrderEntry> {
    private static final OrderEntryDao INSTANCE = new OrderEntryDao();

    private static final String SAVE_SQL = """
            INSERT INTO pizzeria.order_entries
            (order_id, pizza_id, amount) 
            VALUES (?, ?, ?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM pizzeria.pizzas
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, order_id, pizza_id, amount
             FROM pizzeria.pizzas
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT id, order_id, pizza_id, amount
             FROM pizzeria.pizzas
             where id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE pizzeria.pizzas
            SET order_id    = ?,
                pizza_id    = ?,
                amount      = ?
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
    protected void setPrimaryKey(OrderEntry entity, ResultSet keys) throws SQLException {
        entity.setId(keys.getInt("id"));
    }

    @Override
    protected void setParametersSave(PreparedStatement statement, OrderEntry entity) throws SQLException {
        setNoPrimaryParameters(statement, entity);
    }

    @Override
    protected void setParametersUpdate(PreparedStatement statement, OrderEntry entity) throws SQLException {
        setNoPrimaryParameters(statement, entity);
        statement.setInt(4, entity.getId());
    }

    private void setNoPrimaryParameters(PreparedStatement statement, OrderEntry entity) throws SQLException {
        statement.setInt(1, entity.getOrderId());
        statement.setInt(2, entity.getPizzaId());
        statement.setInt(3, entity.getAmount());
    }

    @Override
    protected OrderEntry buildEntity(ResultSet resultSet) throws SQLException {
        return OrderEntry.builder()
                .id(resultSet.getInt("id"))
                .orderId(resultSet.getInt("order_id"))
                .pizzaId(resultSet.getInt("pizza_id"))
                .amount(resultSet.getInt("amount"))
                .build();
    }

    public static OrderEntryDao getInstance() {
        return INSTANCE;
    }

    private OrderEntryDao() {
    }
}
