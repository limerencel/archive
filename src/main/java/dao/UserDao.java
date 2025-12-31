package dao;

import entity.User;
import utils.DataSourceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserDao {
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

    public static User findUserByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try (Connection conn = DataSourceUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String number = rs.getString("number");
                Integer gender = rs.getInt("gender");
                LocalDateTime registeredDate = rs.getObject("registered_date", LocalDateTime.class);
                LocalDateTime lastLoginDate = rs.getObject("last_login_date", LocalDateTime.class);
                Integer status = rs.getInt("status");
                String bio = rs.getString("bio");
                String pic = rs.getString("pic");
                String role = rs.getString("bio");

                return new User(id, username,name,password,email,number,gender,registeredDate,lastLoginDate,status,bio,pic,role);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void updateLoginDate(String username, LocalDateTime loginTime) {
        String sql = "UPDATE user SET last_login_date = ? WHERE username = ?";

        try (Connection conn = DataSourceUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, loginTime);
            ps.setString(2, username);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateUser(User loginUser) {
        String sql = "UPDATE user SET name=?, email=?, number=?, gender=?, bio=? WHERE id=?";
        try (Connection conn = DataSourceUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loginUser.getName());
            ps.setString(2, loginUser.getEmail());
            ps.setString(3, loginUser.getNumber());
            ps.setInt(4, loginUser.getGender());
            ps.setString(5, loginUser.getBio());
            ps.setInt(6, loginUser.getId());

            int rows = ps.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
