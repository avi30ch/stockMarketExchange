# JoesStocks

## Configuration

Enter your database username and password as the `DB_USER` and `DB_PASSWORD`
environment variables for the Java server. For example, in a shell:

```sh
export DB_USER="your_database_username"
export DB_PASSWORD="your_database_password"
```

Do not put real credentials in source code or commit them to Git. `DB_URL` is
optional and defaults to `jdbc:mysql://localhost:3306/login`.

For local stock-search testing, copy `src/main/webapp/api-config.example.js` to
`src/main/webapp/api-config.js` and set `window.FINNHUB_API_KEY` there. The
local config file is ignored by Git. A key used in browser code is visible to
site visitors; use a restricted key or serve market-data requests through a
backend if the provider supports it.
