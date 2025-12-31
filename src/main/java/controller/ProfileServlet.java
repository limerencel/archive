package controller;

import com.alibaba.fastjson.JSON;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 拿到session
        HttpSession session = req.getSession();

        // 拿到user信息
        User loginUser = (User) session.getAttribute("loginUser");

        // 返回User JSON
        resp.getWriter().write(JSON.toJSONString(loginUser));
    }
}
