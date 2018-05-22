Feature: Sprint

  Background: Base Project with the follow stories and tasks
    Given these projects have already been created for the sprint:
      | title         | number of sprints | length of sprint |
      | My Project #3 |                 3 |                5 |
    And has the following stories and tasks:
      | story                | story points | tasks               |
      | create the ui        |           13 | Task#1:5, Task#2:10 |
      | test the ui          |           40 | Task#3:5            |
      | back end development |            5 | Task#4:8, Task#5:13 |
      | database work        |            8 | Task#6:3            |

  Scenario Outline: Add Story to Sprint
    When on "Sprint #1" sprint page, click the Add to Sprint lick for the available story <story>
    Then story <story> will move into the sprint stories section
    And will have a status of "In Progress"
    And the sprint burndown total will be <sprint burndown>

    Examples: 
      | story         | sprint burndown |
      | create the ui |              15 |
      | database work |              18 |
