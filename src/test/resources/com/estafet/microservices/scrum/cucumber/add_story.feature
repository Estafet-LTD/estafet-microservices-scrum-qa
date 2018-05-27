@story
Feature: Add Story

  Scenario: Create a new story and navigate to story page
    Given the following project has already been created for add story:
      | title         | number of sprints | length of sprint |
      | My Project #3 |                 3 |                5 |
    When I click Add Story from the project "My Project #3" page
    And enter a title of "My Story #1"
    And 3 story points
    And submit the story
    Then I should be able to click on the link for story "My Story #1" navigate to the story page
    And which should have a status of "Not Started"
    And a title of "My Story #1"
    And story points of 3
