Feature: Update an item

  Scenario: Ability to update an item
    Given an item named "Apple MacBook Pro 16"
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
