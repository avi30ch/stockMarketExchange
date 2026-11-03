import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.*;

@WebServlet("/TradeServlet")
public class TradeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder json = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        Gson gson = new Gson();
        TradeRequest tradeRequest = gson.fromJson(json.toString(), TradeRequest.class);

        if (tradeRequest == null || tradeRequest.ticker == null || tradeRequest.quantity <= 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid trade data supplied.\"}");
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"User not logged in.\"}");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        try {
            if (processTrade(tradeRequest.ticker, tradeRequest.quantity, userId)) {
                response.getWriter().write("{\"success\":\"Trade processed successfully.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Failed to process trade.\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Database error.\"}");
        }
    }

    private boolean processTrade(String ticker, int quantity, int userId) throws SQLException {
        // Database connection logic here
        return true;  // Simplified for illustration
    }

    static class TradeRequest {
        String ticker;
        int quantity;
    }
}
