Feature: Delete an item

  Scenario: Ability to delete an item
    Given a "Apple MacBook Pro 18" item is created
    And is a "Intel Core i10" CPU model
    And has a price of "2050.99"
    When the request to add the item is made
    Then a 200 response code is returned
    And a "Apple MacBook Pro 18" is created
    When the created item is deleted
    Then a 200 response code is returned
    When the created item is retrieved
    Then a 404 response code is returned
