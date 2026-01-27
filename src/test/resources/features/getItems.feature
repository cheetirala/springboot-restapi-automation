Feature: Get an item
  Scenario: Ability to return an item
    Given an item named "Dell XPS 15"
    And is a "Intel Core i7" CPU model
    And has a price of "1499.00"
    When the request to add the item is made
    Then a 200 response code is returned
    And a "Dell XPS 15" is created
    When the created item is retrieved
    Then a 200 response code is returned
    And the retrieved item name is "Dell XPS 15"

    Scenario: Ability to return specific items
      Given an item named "Dell XPS 15"
      And is a "Intel Core i7 " CPU model
      And has a price of "1499.00"
      When the request to add the item is made
      Then a 200 response code is returned
      And a "Dell XPS 15" is created
      Given an item named "Apple MacBook Pro 16"
      And is a "Intel Core i9" CPU model
      And has a price of "1849.99"
      When the request to add the item is made
      Then a 200 response code is returned
      And a "Apple MacBook Pro 16" is created
      When the created items are retrieved
      Then a 200 response code is returned
      And the retrieved items are as expected

  Scenario: Invalid id returns 404 for retrieve request
    When I retrieve an item with invalid id "invalid-id"
    Then a 404 response code is returned
