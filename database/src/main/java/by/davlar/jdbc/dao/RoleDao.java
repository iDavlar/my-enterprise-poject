package by.davlar.jdbc.dao;

import by.davlar.jdbc.entity.Pizza;
import by.davlar.jdbc.entity.Role;
import by.davlar.jdbc.exception.DaoException;
import by.davlar.jdbc.utils.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleDao extends AbstractDao<Integer, Role> {

    private static final RoleDao INSTANCE = new RoleDao();
    private static final Integer DEFAULT_ID = 2;
    private static final String SAVE_SQL = """
            INSERT INTO pizzeria.roles
            (name, isAdmin) 
            VALUES (?, ?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM pizzeria.roles
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, name, isAdmin
             FROM pizzeria.roles
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT id, name, isAdmin
             FROM pizzeria.roles
             where id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE pizzeria.roles
            SET name    = ?,
                isAdmin = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_NAME_SQL = """
            SELECT id, name, isAdmin
            FROM pizzeria.roles
            WHERE name = ?
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
    protected void setPrimaryKey(Role entity, ResultSet keys) throws SQLException {
        entity.setId(keys.getInt("id"));
    }

    @Override
    protected void setParametersSave(PreparedStatement statement, Role entity) throws SQLException {
        setNoPrimaryParameters(statement, entity);
    }

    @Override
    protected void setParametersUpdate(PreparedStatement statement, Role entity) throws SQLException {
        setNoPrimaryParameters(statement, entity);
        statement.setInt(3, entity.getId());
    }

    private void setNoPrimaryParameters(PreparedStatement statement, Role entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setBoolean(2, entity.getIsAdmin());
    }

    @Override
    protected Role buildEntity(ResultSet resultSet) throws SQLException {
        return Role.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .isAdmin(resultSet.getBoolean("isAdmin"))
                .build();
    }

    public static RoleDao getInstance() {
        return INSTANCE;
    }

    public Integer getDefaultId() {
        return DEFAULT_ID;
    }

    public Optional<Role> findByName(String name) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_NAME_SQL)) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            Role role = null;
            if (resultSet.next()) {
                role = buildEntity(resultSet);
            }
            return Optional.ofNullable(role);

        } catch (DaoException e) {
            throw e;
        } catch (Throwable e) {
            throw new DaoException(e);
        }
    }
}
