Feature: Add an item

  Scenario: Verify an item can be added
    Given a "Apple MacBook Pro 16" item is created
    And is a "Intel Core i9" CPU model
    And has a price of "1849.99"
    When the request to add the item is made
    Then a 200 response code is returned
    And a "Apple MacBook Pro 16" is created

  Scenario Outline: Verify multiple items can be added
    Given a "<name>" item is created
    And is a "<model>" CPU model
    And has a price of "<price>"
    When the request to add the item is made
    Then a 200 response code is returned
    And a "<name>" is created
    Examples:
      | name                 | model          | price   |
      | Dell XPS 15          | Intel Core i7  | 1499.00 |
      | Apple MacBook Pro 18 | Intel Core i10 | 2050.99 |
