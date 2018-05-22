Feature: Sprint Board

  Background: Base Project with the following stories and tasks for the sprint board
    Given these projects have already been created for the sprint board:
      | title         | number of sprints | length of sprint |
      | My Project #3 |                 3 |                5 |
    And has the following stories and tasks:
    And the project burndown for "Sprint#1" is 86 points
      | story                | story points | tasks               | sprint   |
      | create the ui        |           13 | Task#1:5, Task#2:10 | Sprint#1 |
      | test the ui          |           40 | Task#3:5            | Sprint#1 |
      | back end development |            5 | Task#4:8, Task#5:13 |          |
      | database work        |            8 | Task#6:3            |          |
      | my work              |           20 | Task#7:12           |          |

  Scenario: Review Initial Sprint
    When on the sprint board "Sprint #1" page
    Then the following tasks are in the todo column:
      | task   | story         |
      | Task#1 | create the ui |
      | Task#2 | create the ui |
      | Task#3 | test the ui   |

  Scenario Outline: Claim Todo Tasks
    When on the sprint board "Sprint #1" page
    And <claim> click the claim for task <task>
    Then verify that its status is <status> on the "Sprint#1" page

    Examples: Claim Tasks
      | task   | claim | status      |
      | Task#1 | yes   | In Progress |
      | Task#2 | no    | Not Started |
      | Task#3 | yes   | In Progress |

  Scenario Outline: Complete Some Tasks
    When on the sprint board "Sprint #1" page
    And click the claim for task <task>
    And <complete> click complete for task <task>
    Then verify that its status is <status> on the "Sprint#1" page
    And the sprint burndown total for "Sprint#1" should be <sprint burndown>

    Examples: Complete Tasks
      | task   | complete | status      | sprint burndown |
      | Task#1 | yes      | Completed   |              15 |
      | Task#2 | no       | In Progress |              15 |
      | Task#3 | yes      | Completed   |              10 |

  Scenario Outline: Update Task Hours
    When on the sprint board "Sprint #1" page
    And click the claim for task <task>
    And click update hours link for task <task>
    And enter <update hours>
    Then verify that its status is <status> on the "Sprint#1" page
    And the sprint burndown total for "Sprint#1" should be <sprint burndown>

    Examples: Update Task Hours
      | task   | sprint burndown | status      | update hours |
      | Task#1 |              15 | Completed   |            0 |
      | Task#2 |               7 | In Progress |            2 |
      | Task#3 |               3 | In Progress |            1 |

  Scenario Outline: Complete All Tasks
    When on the sprint board "Sprint #1" page
    And click the claim for task <task>
    And <complete> click complete for task <task>
    Then the sprint burndown total for "Sprint#1" should be <sprint burndown>
    And the current active sprint should be "Sprint#2" as viewed from the "My Project #3" page
    And "Sprint#1" should be in the completed sprints on the "My Project #3" page
    And the project burndown total for "Sprint#2" should be 33 points

    Examples: Complete Tasks
      | task   | sprint burndown |
      | Task#1 |              15 |
      | Task#2 |               5 |
      | Task#3 |               0 |
