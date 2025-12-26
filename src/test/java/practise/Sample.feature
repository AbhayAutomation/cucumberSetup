#
#@Reg
#Feature: Login Functionality for Task Manager
#
#  Background:
#    Given I am on the login page
#
#  @test
#  Scenario: Successful login with valid credentials
#    When I enter valid email "redv@mailinator.com" and password "Redv@123"
#    And I click on the login button
#    Then I should be redirected to the dashboard
#    And I should see the welcome message
#
#  Scenario: Login with invalid password
#    When I enter valid email and invalid password
#    And I click on the login button
#    Then I should see invalid email or password error
#
#  @test
#  Scenario Outline: Successful login with multiple valid credentials
#    And I enter valid emails "<user>" and password "<pass>"
#    And I click on the login button
#    Then I should be redirected to the dashboard
#    And I should see the welcome message
#
#    Examples:
#      | user  | pass  |
#      | test1 | pass1 |
#      | test2 | pass2 |
#
#  @test
#  Scenario: Successful login with valid credentials
#    When I enter valid email and password
#      | email | user1    |
#      | pass  | password |
#    And I click on the login button
#    Then I should be redirected to the dashboard
#    And I should see the welcome message
#
#