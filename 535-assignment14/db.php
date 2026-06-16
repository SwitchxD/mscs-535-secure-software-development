<?php
// Connect to the MySQL database using PDO
$pdo = new PDO("mysql:host=localhost;dbname=myapp", "root", "");

// Prepare a query to find the user by username
// The ? is a placeholder to prevent SQL injection
$stmt = $pdo->prepare("SELECT * FROM users WHERE username = ?");

// Execute the query, passing the username from the form
$stmt->execute([$_POST['username']]);

// Fetch the matching user row
$user = $stmt->fetch();

// Check if the user exists AND the password matches the stored hash
if ($user && password_verify($_POST['password'], $user['password'])) {
    echo "Login successful!";
} else {
    echo "Invalid credentials.";
}
?>