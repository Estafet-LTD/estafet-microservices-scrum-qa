Feature: Add Story

  Background: Base Project
    Given the following project has already been created for add story:
      | title         | number of sprints | length of sprint |
      | My Project #3 |                 3 |                5 |

  Scenario Outline: Create a new story and verify backlog
    When I click Add Story from the project "My Project #3" page
    And enter a title of <title>
    And story points of <story points>
    And click submit on the add story page
    Then I should be able to see the story <title> added to the project "My Project #3" on the project page
    And with story points <story points>
    And the initial story points total of the project burndown for "My Project #3" is <project burndown>

    Examples: 
      | title                | story points | project burndown |
      | create the ui        |           13 |               13 |
      | test the ui          |           40 |               53 |
      | back end development |            5 |               58 |
      | database work        |            8 |               64 |

  Scenario: Create a new story and navigate to story page
    When I click Add Story from the project "My Project #3" page
    And enter a title of "My Story #1"
    And 3 story points
    Then I should be able to click on the link for story "My Story #1" navigate to the story page
    And which should have a status of "Not Started"
    And a title of "My Story #1"
    And story points of 3
