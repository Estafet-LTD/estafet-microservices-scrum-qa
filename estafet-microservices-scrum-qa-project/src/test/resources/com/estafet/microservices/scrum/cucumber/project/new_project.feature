Feature: New Project

  Background: Multiple projects
    Given these projects have already been created:
      | title                | number of sprints | length of sprint |
      | My Project #3        |                 3 |                5 |
      | The cucumber project |                 2 |                5 |
    When I click new project from the project list screen and submit the following values:
      | title                 | number of sprints | length of sprint |
      | A totally new project |                 4 |               10 |

  Scenario: Create a new project
    Then I should be able to view the new project called "A totally new project" on the project page
    And the projects list page should now display the following projects:
      | My Project #3         |
      | The cucumber project  |
      | A totally new project |

  Scenario: Review active sprint for new project
    Then on the "A totally new project" project page, for there should be a link to a sprint called "Sprint #1"
    And the "Sprint #1" link should navigate me to an "Active" sprint called "Sprint #1"

  Scenario: Review sprint burndown for new project
    Then on the "A totally new project" there should be a link for Project Burndown that shows me the project burndown
    And the project burndown should consist of 4 sprints, each totalling 0 story point effort remaining

  Scenario: Review project burndown for new project
    Then on "A totally new project" there should be a link for Sprint Burndown that shows me the sprint burndown
    And the sprint burndown should contain 10 days
