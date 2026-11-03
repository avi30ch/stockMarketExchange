import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

@WebServlet("/PortfolioServlet")
public class PortfolioServlet extends HttpServlet {
	public static final long serialVersionUID = 1L;
	String responsejson = "";
	 ArrayList<Integer> userIDs = new ArrayList<Integer>();
	 ArrayList<String> tickers = new ArrayList<String>();
	 ArrayList<Integer> quantities = new ArrayList<Integer>();
	 ArrayList<Double> prices = new ArrayList<Double>();
	 Gson gson = new Gson();
	 int id;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // This servlet would need to handle trade logic, like buying or selling stock.
        // The implementation depends on your database schema and trade logic.
		StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        
        JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
        String ticker = jsonObject.get("ticker").getAsString();
        int quantity = jsonObject.get("quantity").getAsInt();
        int userID = jsonObject.get("user-id").getAsInt();
        System.out.println(ticker + " " + quantity);
        tickers.add(ticker);
        quantities.add(quantity);
        userIDs.add(userID);
        int tradeID = -1;
        responsejson = generateJSON(userIDs, tickers, quantities, prices);
	    String username = request.getParameter("signup-username");
		response.setContentType("application/json");
		JDBCConnector temp = new JDBCConnector();
		tradeID = temp.portfolio(username, ticker, quantity);
		
       // response.getWriter().write(/* user's portfolio in JSON */);
    }
	private String generateJSON(ArrayList<Integer> u, ArrayList<String> t,  ArrayList<Integer> q, ArrayList<Double> p) {
		ArrayList<Portfolio> portfolios = new ArrayList<>();
		
		for (int i = 0; i < u.size(); i++) {
			portfolios.add(new Portfolio(id, u.get(i), t.get(i), q.get(i), p.get(i)));
		}
		return gson.toJson(portfolios);	
	}
}
