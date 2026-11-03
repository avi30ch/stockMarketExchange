// Function to handle the login AJAX request
function loginUser(event) {
    event.preventDefault();
    var username = document.getElementById('login-username').value;
    var password = document.getElementById('login-password').value;

    var xhttp = new XMLHttpRequest();
    xhttp.open('GET', '/LoginServlet', true);
    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.onload = function() {
        if (xhr.status === 200) {
            alert('Login successful!');
            var response = JSON.parse(xhttp.responseText);
			localStorage.setItem('userId', response.userId);
        } else {
            alert('Login failed!');
        }
    };

    xhttp.send();
}

// Function to handle the signup AJAX request
function signupUser(event) {
    event.preventDefault();
    var email = document.getElementById('signup-email').value;
    var username = document.getElementById('signup-username').value;
    var password = document.getElementById('signup-password').value;
    var confirmPassword = document.getElementById('signup-confirm-password').value;

    if(password !== confirmPassword) {
        alert("Passwords do not match!");
        return;
    }

    var xhttp = new XMLHttpRequest();
    xhttp.open('POST', '/RegisterServlet', true);
    xhttp.setRequestHeader('Content-Type', 'application/json');

    var data = JSON.stringify({
        email: email,
        username: username,
        password: password
    });

    xhttp.onload = function() {
        if (xhttp.status === 200) {
            alert('Signup successful!');
            console.log(xhttp.responseText);
        } else {
            alert('Signup failed!');
        }
    };

    xhttp.send(data);
}


// Attach event listeners to the form submit events
