
document.getElementById('search-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the form from submitting the traditional way
    const ticker = document.getElementById('stock-ticker').value.toUpperCase();

    const apiKey = window.FINNHUB_API_KEY;
    if (!apiKey) {
        alert('Finnhub API key is not configured.');
        return;
    }
    const profileUrl = `https://finnhub.io/api/v1/stock/profile2?symbol=${ticker}&token=${apiKey}`;
    const quoteUrl = `https://finnhub.io/api/v1/quote?symbol=${ticker}&token=${apiKey}`;

    // Create XMLHttpRequest object for company profile
    const profileRequest = new XMLHttpRequest();
    profileRequest.open('GET', profileUrl, true);
    
    profileRequest.onload = function() {
        if (profileRequest.status >= 200 && profileRequest.status < 300) {
            const profileData = JSON.parse(profileRequest.responseText);

            // Create XMLHttpRequest object for company quote
            const quoteRequest = new XMLHttpRequest();
            quoteRequest.open('GET', quoteUrl, true);
            quoteRequest.onload = function() {
                if (quoteRequest.status === 200) {
                    const quoteData = JSON.parse(quoteRequest.responseText);
                    displayStockInfo(ticker, profileData, quoteData);
                } else {
                    alert('Error fetching quote data');
                }
            };
            
            quoteRequest.onerror = function() {
                alert('Error connecting to Finnhub API for quote data');
            };
            quoteRequest.send();
        } 
        else {
            alert('Error fetching profile data');
        }
    };
    
    
    profileRequest.onerror = function() {
        alert('Error connecting to Finnhub API for profile data');
    };
    profileRequest.send();
});

function displayStockInfo(ticker, profileData, quoteData) {
    // Hide the search form
    document.getElementById('search-form').style.display = 'none';
    document.getElementById('search-area').style.display = 'none';
    
    // Show the stock details div if it was previously hidden
    const stockDetailsDiv = document.getElementById('stock-details');
    stockDetailsDiv.style.display = 'block';
    
    // Populate the stock details
    stockDetailsDiv.innerHTML = `
        <div class="stock-header">
            <h2>${ticker} - ${profileData.name}</h2>
            <p>${profileData.exchange}</p>
        </div>
        <div class="stock-summary">
            <h3>Summary</h3>
            <p>High Price: ${quoteData.h}</p>
            <p>Low Price: ${quoteData.l}</p>
            <p>Open Price: ${quoteData.o}</p>
            <p>Close Price: ${quoteData.pc}</p>
        </div>
        <div class="stock-company-info">
            <h3>Company Information</h3>
            <p>IPO Date: ${profileData.ipo}</p>
            <p>Market Cap: ${profileData.marketCapitalization}</p>
            <p>Website: <a href="${profileData.weburl}" target="_blank">${profileData.weburl}</a></p>
            <p>Phone: ${profileData.phone}</p>
        </div>
    `;
}
