package controller;

import com.alibaba.fastjson.JSON;
import entity.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 拿到session
        HttpSession session = req.getSession();

        // 拿到user信息
        User loginUser = (User) session.getAttribute("loginUser");

        // 返回User JSON
        resp.getWriter().write(JSON.toJSONString(loginUser));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置编码和相应类型
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            resp.getWriter().write("{\"success\": false, \"message\": \"未登录\"}");
            return;
        }

        try {
            // 获取并更新信息
            loginUser.setName(req.getParameter("realName"));
            loginUser.setEmail(req.getParameter("email"));
            loginUser.setNumber(req.getParameter("phone"));
            loginUser.setGender(Integer.parseInt(req.getParameter("gender")));
            loginUser.setBio(req.getParameter("bio"));

            // 更新数据库
            boolean success = userService.updateUser(loginUser);

            if (success) {
                // 更新session里的user
                session.setAttribute("loginUser", loginUser);
            }
            resp.getWriter().write("{\"success\": " + success + "}");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
