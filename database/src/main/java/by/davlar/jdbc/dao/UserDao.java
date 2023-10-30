package by.davlar.jdbc.dao;

import by.davlar.jdbc.entity.Role;
import by.davlar.jdbc.entity.User;
import by.davlar.jdbc.exception.DaoException;
import by.davlar.jdbc.utils.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class UserDao
        extends AbstractDao<Integer, User> {
    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO pizzeria.users
            (first_name, last_name, birthday, login, password, telephone, role) 
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM pizzeria.users
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, first_name, last_name, birthday, login, password, telephone, role
             FROM pizzeria.users
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT id, first_name, last_name, birthday, login, password, telephone, role
             FROM pizzeria.users
             where id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE pizzeria.users
            SET first_name  = ?,
                last_name   = ?,
                birthday    = ?,
                login       = ?,
                password    = ?,
                telephone   = ?,
                role        = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_LOGIN_PASSWORD_SQL = """
            SELECT id, first_name, last_name, birthday, login, password, telephone, role
             FROM pizzeria.users
             where login = ? and password = ?
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
    protected void setPrimaryKey(User entity, ResultSet keys) throws SQLException {
        entity.setId(keys.getInt("id"));
    }

    @Override
    protected void setParametersSave(PreparedStatement statement, User entity) throws SQLException {
        setNoPrimaryParameters(statement, entity);
    }

    @Override
    protected void setParametersUpdate(PreparedStatement statement, User entity) throws SQLException {
        setNoPrimaryParameters(statement, entity);
        statement.setInt(8, entity.getId());
    }

    private void setNoPrimaryParameters(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getLastName());
        statement.setDate(3, entity.getBirthday());
        statement.setString(4, entity.getLogin());
        statement.setString(5, entity.getPassword());
        statement.setString(6, entity.getTelephone());
        statement.setInt(7, entity.getRoleId());
    }

    @Override
    protected User buildEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name").trim())
                .lastName(resultSet.getString("last_name").trim())
                .birthday(resultSet.getDate("birthday"))
                .login(resultSet.getString("login").trim())
                .password(resultSet.getString("password").trim())
                .telephone(resultSet.getString("telephone").trim())
                .roleId(resultSet.getInt("role"))
                .build();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    private UserDao() {
    }

    public Optional<User> findByLoginPassword(String login, String password) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_LOGIN_PASSWORD_SQL)) {

            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = buildEntity(resultSet);
            }
            return Optional.ofNullable(user);

        } catch (DaoException e) {
            throw e;
        } catch (Throwable e) {
            throw new DaoException(e);
        }
    }
}
