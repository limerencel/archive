package dao;

import entity.User;
import utils.DataSourceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class userDao {
    public static void register(User user) {
        String sql = "INSERT INTO user (username, name, password, email, number, gender, registered_date, status, pic, role) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DataSourceUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getNumber());
            ps.setInt(6, user.getGender());
            ps.setObject(7, user.getRegisteredDate());
            ps.setInt(8, user.getStatus());
            ps.setString(9, user.getPic());
            ps.setString(10, user.getRole());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
