
public class Portfolio{
	int id;
	int trade;
	String t;
	int n;
	double p;
//Portfolio (trade_id, user_id, ticker, numStock, price)
	public Portfolio(int trade_id, int user_id, String ticker, int numStock, Double price) {
		id = user_id;
		trade = trade_id;
		t = ticker;
		n = numStock;
		p = price;
	}
	
	int getTradeID() {
		return trade;
	}
	int getUserID() {
		return id;
	}
	String getTicker() {
		return t;
	}
	int getQuantity() {
		return n;
	}
	double getPrice() {
		return p;
	}
}
