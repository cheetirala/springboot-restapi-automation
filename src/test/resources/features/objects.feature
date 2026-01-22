Feature: Add, Get, Update or Delete an item

  Scenario: Verify an item can be added
    Given a "Apple MacBook Pro 16" item is created
    And is a "Intel Core i9" CPU model
    And has a price of "1849.99"
    When the request to add the item is made
    Then a 200 response code is returned
    And a "Apple MacBook Pro 16" is created

  Scenario: Ability to return an item
    Given a "Dell XPS 15" item is created
    And is a "Intel Core i7" CPU model
    And has a price of "1499.00"
    When the request to add the item is made
    Then a 200 response code is returned
    And a "Dell XPS 15" is created
    When the created item is retrieved
    Then a 200 response code is returned
    And the retrieved item name is "Dell XPS 15"

  Scenario: Ability to update an item
    Given a "Apple MacBook Pro 16" item is created
    And is a "Intel Core i9" CPU model
    And has a price of "1849.99"
    When the request to add the item is made
    Then a 200 response code is returned
    And a "Apple MacBook Pro 16" is created
    When the user updated item name to "Apple MacBook Pro 17"
    And the user updated cpu model to "Intel Core i10"
    And the user updated price to "1849.99"
    And the request to update the item is made
    Then a 200 response code is returned
    And the item is updated with name "Apple MacBook Pro 17"

  Scenario: Ability to delete an item
    Given a "Apple MacBook Pro 18" item is created
    And is a "Intel Core i1" CPU model
    And has a price of "2050.99"
    When the request to add the item is made
    Then a 200 response code is returned
    And a "Apple MacBook Pro 18" is created
    When the created item is deleted
    Then a 200 response code is returned
    When the created item is retrieved
    Then a 404 response code is returned
