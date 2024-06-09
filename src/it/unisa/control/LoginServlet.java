package it.unisa.control;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import it.unisa.model.*;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();

        try {
            UserBean user = new UserBean();
            user.setUsername(request.getParameter("un"));
            // Crittografa la password prima di passarla al metodo di recupero
            String encryptedPassword = encryptPassword(request.getParameter("pw"));
            user.setPassword(encryptedPassword);
            user = userDao.doRetrieve(request.getParameter("un"), encryptedPassword);

            String checkout = request.getParameter("checkout");

            if (user.isValid()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser", user);
                if (checkout != null)
                    response.sendRedirect(request.getContextPath() + "/account?page=Checkout.jsp");
                else
                    response.sendRedirect(request.getContextPath() + "/Home.jsp");
            } else
                response.sendRedirect(request.getContextPath() + "/Login.jsp?action=error"); // error page
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    // Metodo per crittografare la password utilizzando SHA-256
    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
