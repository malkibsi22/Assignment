Feature: To Automate Sauce labs
  
  
  Scenario: API
  Given the user hit the api and capture firstname, lastname and zip
  
  Scenario: Add to Cart
  
  
Given the user launches "https://www.saucedemo.com"
Then the user enters "standard_user" as username
Then the user enters "secret_sauce" as password
And the users clicks on login button
Then the user Filter items based on Price "High to Low"
And the user Scroll to "Sauce Labs Onesie"
Then the user Click on Add to cart and verify items 
And the user Click the Checkout button
Then the user provides value from API
Then user Click the continue button
Then user Click the Finish button
And user Verifies the order success message.
