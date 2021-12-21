#Author: camilo@generalsoftwareinc.com
#Keywords Summary : Driver UHs automated Testing Examples
#Feature: Add Driver.
#Scenario:
#------------------------------------------
#1 The "Add Driver" button is not shown if the user who is accessing the
# "Drivers List" view is the GoHeavy Admin, and he comes from the "Fleet Owners List" view.
#2.
#------------------------------------------
#Given: Some precondition step
#------------------------------------------
#1. The GoHeavy Admin / Fleet Owner must be logged in the system.
#2. The GoHeavy Admin / Fleet Owner is on the "Drivers List" view.
#------------------------------------------
#When: Some key actions
#Then: To observe outcomes or validation
#------------------------------------------
#1. Clicks on the "Add Driver" button.
#------------------------------------------
#And,But:
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: Given Any user is logged
#Sample Feature Definition Template

Feature: Add Driver 2.3
  As a: GoHeavy Admin / Fleet Owner
  I Want To: add a Driver
  So That: a new Driver is registered in the system.

  Background:
    Given Any "GoHeavy Admin / Fleet Owner" is logged
    And The user is in "Drivers List" view.

  Scenario: Add Driver 2.3 -- Add Driver

    When User clicks on "Add Driver" button.
    And The user inserts valid driver's data
    And User clicks on the "Add" button.
    Then System add a new driver in "On-boarding" status.
    And System displays message "A new Driver was successfully created."
    And System returns to the "Drivers List" view
