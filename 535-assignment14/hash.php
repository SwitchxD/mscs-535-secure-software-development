<?php
// Generate a bcrypt hash of the password "mypassword123"
$hash = password_hash("mypassword123", PASSWORD_BCRYPT);

// Display the hash on the page so you can copy it
echo $hash;
?>