Feature: Validate Search functionality
  I want to use this template to test search feature

  @hafele
  Scenario: validate search result for when enter valid input
    Given user select accept cookies
    When user enter input "40051125" in search box
    Then add to cart button should be displayed for the searched item

  @hafele
  Scenario: validate search result for when enter valid input
    Given user select accept cookies
    When user enter input "testtt" in search box
    Then validate no results found message is displayed