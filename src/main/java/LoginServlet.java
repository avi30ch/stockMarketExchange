import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

//... other imports ...
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
 public static final long serialVersionUID = 1L;
 Gson gson = new Gson();
 public LoginServlet() {
     super();
     // TODO Auto-generated constructor stub
 }

 protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
     String password = request.getParameter("signup-password");
     String username = request.getParameter("signup-username");
	 
	 PrintWriter pw = response.getWriter();
	
	response.setContentType("application/json");		
			
	
	if(username == null || password == null) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		String error = "User info is missing";
		pw.write(gson.toJson(error));
		pw.flush();
	}
	int userID = -1;
	JDBCConnector temp = new JDBCConnector();
	userID = temp.loginUser(username, password);

	if(userID == -1) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		String error = "Username does not exist";
		pw.write(gson.toJson(error));
		pw.flush();	
	}
	else if(userID == -2) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		String error = "Password is Incorrect";
		pw.write(gson.toJson(error));
		pw.flush();
	}
	else {
		response.setStatus(HttpServletResponse.SC_OK); // Change the status code to SC_OK (200)
        pw.write(gson.toJson(userID));
        // Redirect to the home page
        response.sendRedirect("portfolio.html");
	}
 }
 private String generateJSON(ArrayList<String> u, ArrayList<String> p) {
		ArrayList<ArrayList<String>> users = new ArrayList<>();
		
		for (int i = 0; i < u.size(); i++) {
			ArrayList<String> individual = new ArrayList<String>();
			individual.add(u.get(i));
			individual.add(p.get(i));
			users.add(individual);
		}
		return new Gson().toJson(users);	
	}
}