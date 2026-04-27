@general
Feature: General feature to verify cart actions

  Background:
    Given user is logged in successfully
    And the shopping cart is empty

  Scenario Outline: Verify that the user can add multiple items to the cart and the total price does not exceed the max price
    When user searches for items using data from "search_items.json" at index <index>
    And user adds all found items to the cart
    Then user verifies that the cart total does not exceed the max price
    Examples:
      | index |
      | 0     |
      | 1     |
      | 2     |
      | 3     |


