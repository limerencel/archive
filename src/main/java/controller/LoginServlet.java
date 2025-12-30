package controller;

import DTO.LoginDTO;
import DTO.RegisterDTO;
import com.alibaba.fastjson.JSON;
import entity.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try(BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }
        String jsonString = jsonBuffer.toString();

        // 使用Fastjson一键转换成Java对象
        LoginDTO loginData = JSON.parseObject(jsonString, LoginDTO.class);

        String username = loginData.getUsername();
        String password = loginData.getPassword();

        HttpSession session = req.getSession();

        User user = userService.findUserByUsername(username);

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        if (user != null && password.equals(user.getPassword())) {
            // 登陆成功
            // 更新登录时间
            userService.updateLoginDate(user.getUsername(), now);
            session.setAttribute("loginUser", user);
            resp.getWriter().write("{\"success\": true, \"message\": \"login successfully\"}");
        } else {
            resp.getWriter().write("{\"success\": false, \"message\": \"username or password is wrong\"}");
        }
    }
}
