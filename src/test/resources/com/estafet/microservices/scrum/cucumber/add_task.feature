@task
Feature: Add Task

  Scenario Outline: Create a new task
    Given the following project has already been created for add task:
      | title         | number of sprints | length of sprint |
      | My Project #3 |                 3 |                5 |
    And "My Project #3" has the following stories:
      | title                | story points |
      | create the ui        |           13 |
      | back end development |            5 |
    When I click Add Task from the <story> page
    And enter a title of <title>
    And estimate of <initial hours>
    And click submit on the add task page
    Then I should be able to see the task <title> added to the <story> page
    And with initial hours <initial hours>
    And with remaining hours <remaining hours>
    And the status for the <story> should now be <story status>

    Examples: 
      | story                | story status | title      | initial hours | remaining hours |
      | create the ui        | Planning     | My Task #1 |            13 |              13 |
      | back end development | Planning     | My Task #2 |             5 |               5 |

  Scenario: Add Task to Closed Story
    Given the following project has already been created for add task:
      | title         | number of sprints | length of sprint |
      | My Project #3 |                 3 |                5 |
    And has a backlog consisting of the following completed stories:
      | story         | story points | sprint    |
      | create the ui |           13 | Sprint#1 |
    When I attempt to add a task "My Task #3" to story "create the ui"
    Then the "Whitelabel Error Page" will be displayed
    And the task "My Task #3" will not be created
