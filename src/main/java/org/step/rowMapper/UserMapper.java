package org.step.rowMapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class UserMapper implements RowMapper {
    private long id;

    private String username;

    private String password;

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        UserMapper user = new UserMapper();
        user.setId(resultSet.getLong(1));
        user.setUsername(resultSet.getString(2));
        user.setPassword(resultSet.getString(3));
        return user;
    }
}
