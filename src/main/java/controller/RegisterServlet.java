package controller;

import DTO.RegisterDTO;
import com.alibaba.fastjson.JSON;
import entity.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应请求和编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        // 从request输入流中读取JSON
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try(BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }
        String jsonString = jsonBuffer.toString();

        // 使用Fastjson一键转换成Java对象
        RegisterDTO registerDate = JSON.parseObject(jsonString, RegisterDTO.class);

        //TODO 用registerDate.emailCode和正确的验证码比较

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 创建实体类，准备入库
        User user = new User();
        // 手动搬数据
        user.setName(registerDate.getName());
        user.setEmail(registerDate.getEmail());
        user.setNumber(registerDate.getPhoneNumber());
        user.setPassword(registerDate.getPassword());
        user.setUsername(registerDate.getUsername());
        // 补充数据
        user.setRegisteredDate(now);
        user.setGender(0);
        user.setStatus(1);
        user.setRole("普通"); // 默认
        user.setPic("https://picsum.photos/100/100?random=10"); // 默认
        // 默认无bio

        // 直接使用对象
        System.out.println("name: " + registerDate.getUsername());

        // 实际业务逻辑
        // 调用注册方法
        userService.register(user);

        // map里放注册的信息
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true); // true是Boolean，通过给Map设置Object type前端更方便处理

        // 转成JSON再返回给前端
        resp.getWriter().write(JSON.toJSONString(resultMap));

    }
}
