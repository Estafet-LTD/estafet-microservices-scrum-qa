Feature: Project Selection

  Background: Multiple projects
    Given these projects have been created:
      | title                      | number of sprints | length of sprint |
      | Microservices Demo Project |                 8 |               10 |
      | My Project #3              |                 3 |                5 |
      | A Secret Project           |                 4 |               10 |
      | The dashboard project      |                 6 |               10 |
      | The cucumber project       |                 2 |                5 |

  Scenario: Navigate to the project lists where multiple projects exist
    When I navigate to the project list page
    Then the project list should contain:
      | Microservices Demo Project |
      | My Project #3              |
      | A Secret Project           |
      | The dashboard project      |
      | The cucumber project       |

  Scenario: Select a project from a list of multiple projects
    When I /navigate to the project list page and click on the "My Project #3" project/
    Then I should /see to the project page for the "My Project #3" project/
