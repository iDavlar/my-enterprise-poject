package by.davlar.jdbc.dao;

import by.davlar.jdbc.entity.Pizza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PizzaDao extends AbstractDao<Integer, Pizza> {
    private static final PizzaDao INSTANCE = new PizzaDao();

    private static final String SAVE_SQL = """
            INSERT INTO pizzeria.pizzas
            (name, cost) 
            VALUES (?, ?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM pizzeria.pizzas
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, name, cost
             FROM pizzeria.pizzas
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT id, name, cost
             FROM pizzeria.pizzas
             where id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE pizzeria.pizzas
            SET name    = ?,
                cost    = ?
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
    protected void setPrimaryKey(Pizza entity, ResultSet keys) throws SQLException {
        entity.setId(keys.getInt("id"));
    }

    @Override
    protected void setParametersSave(PreparedStatement statement, Pizza entity) throws SQLException {
        setNoPrimaryParameters(statement, entity);
    }

    @Override
    protected void setParametersUpdate(PreparedStatement statement, Pizza entity) throws SQLException {
        setNoPrimaryParameters(statement, entity);
        statement.setInt(3, entity.getId());
    }

    private void setNoPrimaryParameters(PreparedStatement statement, Pizza entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getCost());
    }

    @Override
    protected Pizza buildEntity(ResultSet resultSet) throws SQLException {
        return Pizza.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .cost(resultSet.getInt("cost"))
                .build();
    }

    public static PizzaDao getInstance() {
        return INSTANCE;
    }

    private PizzaDao() {
    }
}
