package it.unisa.control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.*;

/**
 * Servlet implementation class RegistrazioneServlet
 */
@WebServlet("/Registrazione")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDao dao = new UserDao();
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String dataNascita = request.getParameter("nascita");
		String username = request.getParameter("us");
		String pwd = request.getParameter("pw");

        String[] parti = dataNascita.split("-");
        dataNascita = parti[2] + "-" + parti[1] + "-" + parti[0];
		
		try {
			
			UserBean user = new UserBean();
			user.setNome(nome);
			user.setCognome(cognome);
			user.setEmail(email);
			user.setDataDiNascita(Date.valueOf(dataNascita));
			user.setUsername(username);
			// Crittografa la password prima di salvarla nel database
			String hashedPassword = hashPassword(pwd);
			user.setPassword(hashedPassword);
			user.setAmministratore(false);
			user.setCap(null);
			user.setIndirizzo(null);
			user.setCartaDiCredito(null);
			dao.doSave(user);
			
		}catch(SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
				
		response.sendRedirect(request.getContextPath() + "/Home.jsp");
	}
	
	// Metodo per crittografare la password utilizzando SHA-512
	private String hashPassword(String password) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hashString = new StringBuilder();
            for (byte b : hash) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
