document.addEventListener('DOMContentLoaded', function() {
    const searchForm = document.getElementById('searching');
    searchForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the form from submitting traditionally
        const ticker = document.querySelector('input[name="ticker"]').value.toUpperCase(); // Ensure this is correct
        if (!ticker) {
            alert('Please enter a stock symbol.');
            return;
        }
        fetchStockDetails(ticker);
    });

    const quantityForm = document.getElementById('signup-form'); // Ensure this is the correct ID
    quantityForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const ticker = document.querySelector('input[name="ticker"]').value.toUpperCase();
        const quantity = document.getElementById('quantity').value;
        sendData(ticker, quantity); // Pass ticker and quantity to sendData function
    });
    document.addEventListener('DOMContentLoaded', function() {
    const buyButton = document.getElementById('buy-button');
    buyButton.addEventListener('click', function(event) {
        event.preventDefault();  // Prevent default form submission
        sendData();  // Call your function to handle data sending
    });
});
});




function fetchStockDetails(ticker, quantity) {
    const apiKey = window.FINNHUB_API_KEY;
    if (!apiKey) {
        alert('Finnhub API key is not configured.');
        return;
    }
    const profileUrl = `https://finnhub.io/api/v1/stock/profile2?symbol=${ticker}&token=${apiKey}`;
    const quoteUrl = `https://finnhub.io/api/v1/quote?symbol=${ticker}&token=${apiKey}`;
	
    
    fetch(profileUrl).then(response => response.json())
        .then(profileData => {
            if(profileData.ticker) {
                fetch(quoteUrl).then(response => response.json())
                    .then(quoteData => {
                        updateStockInformation(profileData, quoteData, ticker);
                    }).catch(error => console.error('Error fetching quote data:', error));
            } else {
                alert('No data found for the entered symbol.');
            }
            
        }).catch(error => console.error('Error fetching profile data:', error));
       const quantityForm = document.querySelector('.quantity-form');
    quantityForm.style.display = 'block';
}
// ...

function updateStockInformation(profileData, quoteData, ticker) {
    // Assuming you have a container element in your HTML with the ID 'stock-details'
    
    const stockDetailsDiv = document.getElementById('stock-details');
    let changeColor = quoteData.d >= 0 ? 'green' : 'red';
    let arrowIcon = quoteData.d >= 0 ? 'fa-caret-up' : 'fa-caret-down';
    document.querySelector('.search-area').style.display = 'none';
    // Update the stock profile section
    const stockProfileHtml = `
        <div class="stock-profile">
            <h2>${profileData.ticker}</h2>
            <h3>${profileData.name}</h3>
            <p>${profileData.exchange}</p>

        </div>
    `;
    // Update the stock price section
    const stockPriceHtml = `
        <div class="stock-price-info">
            <h2 class="price">${quoteData.c.toFixed(2)}</h2>
            <p class="change ${changeColor}"><i class="fas ${arrowIcon}"></i> ${quoteData.d.toFixed(2)} (${quoteData.dp.toFixed(2)}%)</p>
            <p class="timestamp">${new Date().toLocaleString()}</p>
        </div>
    `;

    
    // Append both sections to the container
    stockDetailsDiv.innerHTML = stockProfileHtml + stockPriceHtml; // Combine HTML strings and then set innerHTML once
    const stockSummaryHtml = `
        <h3>Market is Open</h3>
        <h4>Summary</h4>
        <p>High Price: ${quoteData.h}</p>
        <p>Low Price: ${quoteData.l}</p>
        <p>Open Price: ${quoteData.o}</p>
        <p>Close Price: ${quoteData.pc}</p>
        <h4>Company Information</h4>
    `;

    // Construct the company information HTML
    const companyInfoHtml = `
        
        <p>IPO Date: ${profileData.ipo}</p>
        <p>Market Cap: ${profileData.marketCapitalization}</p>
        <p>Website: <a href="${profileData.weburl}" target="_blank">${profileData.weburl}</a></p>
        <p>Phone: ${profileData.phone}</p>
    `;

    // Select the divs and set the innerHTML
	
    document.getElementById('stock-summary').innerHTML = stockSummaryHtml;
    document.getElementById('company-info').innerHTML = companyInfoHtml;
    //document.write(ticker);
    const quantity = document.getElementById('quantity').value; // Retrieve the quantity from the input field
    const userId = localStorage.getItem('userId'); // Retrieve the userId from localStorage

    const tradeData = {
        ticker: ticker,
        quantity: quantity,
        userId: userId // Add the userId to the tradeData object
    };
}
// Correctly attach event listeners
function sendData() {
    const userId = localStorage.getItem('userId');
    const ticker = document.getElementById('ticker-input').value; // Corrected to the actual input ID for ticker
    const quantity = document.getElementById('quantity').value;

    const data = JSON.stringify({
        userId: userId,
        ticker: ticker,
        quantity: quantity
    });

    var xhttp = new XMLHttpRequest();
    xhttp.open('POST', '/TradeServlet', true);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.onload = function() {
        if (xhttp.status === 200) {
            alert('Trade successful!');
        } else {
            alert('Trade failed! ' + xhttp.responseText);
        }
    };

    xhttp.send(data);
}
