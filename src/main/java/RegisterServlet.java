import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
 public static final long serialVersionUID = 1L;
 String responseJson ="";
 double bal;
 int id;
 Gson gson = new Gson();
 boolean success = false;
 ArrayList<String> usernames = new ArrayList<String>();
 ArrayList<String> passwords = new ArrayList<String>();
 ArrayList<String> emails = new ArrayList<String>();

 private String generateJSON(ArrayList<String> u, ArrayList<String> p,  ArrayList<String> e) {
		ArrayList<User> users = new ArrayList<>();
		
		for (int i = 0; i < u.size(); i++) {
			users.add(new User(id, u.get(i), p.get(i), e.get(i), bal));
		}
		return gson.toJson(users);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String email = request.getParameter("signup-email");
	     String password = request.getParameter("signup-password");
	     String username = request.getParameter("signup-username");
		 usernames.add(username);
		 passwords.add(password);
		 emails.add(email);
		 
		 PrintWriter pw = response.getWriter();
		
		
		response.setContentType("application/json");		
		BufferedReader reader = request.getReader();
		
		StringBuilder json = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
		    json.append(line);
		}
				
		
		if(username == null || passwords == null || emails == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "User info is missing";
			pw.write(gson.toJson(error));
			pw.flush();
		}
		int userID = -1;
		JDBCConnector temp = new JDBCConnector();
		userID = temp.registerUser(username, password, email);

		if(userID == -1) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "Username is Taken";
			pw.write(gson.toJson(error));
			pw.flush();	
		}
		else if(userID == -2) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String error = "Email is already registered";
			pw.write(gson.toJson(error));
			pw.flush();
		}
		else {
			response.setStatus(HttpServletResponse.SC_OK); // Change the status code to SC_OK (200)
            pw.write(gson.toJson(userID));

            // Redirect to the home page
            response.sendRedirect("portfolio.html");
		}
		pw.print(responseJson);
	}
	
}
