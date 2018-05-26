@sprint
Feature: Sprint Board

  Background: Base Project with the following stories and tasks for the sprint board
    Given these projects have already been created for the sprint board:
      | title         | number of sprints | length of sprint |
      | My Project #3 |                 3 |                5 |
    And has the following stories and tasks:
      | story                | story points | tasks                               |
      | create the ui        |           13 | Task#1 [5 hours], Task#2 [10 hours] |
      | test the ui          |           40 | Task#3 [5 hours]                    |
      | back end development |            5 | Task#4 [8 hours], Task#5 [13 hours] |
      | database work        |            8 | Task#6 [3 hours]                    |
      | my work              |           20 | Task#7 [12 hours]                   |
    And the project burndown for "My Project #3" is 86 points
    And add the following stories to "Sprint#1":
      | create the ui |
      | test the ui   |

  Scenario: Review Initial Sprint
    When on the sprint board "Sprint #1" page
    Then the following tasks are in the todo column:
      | task   | story         |
      | Task#1 | create the ui |
      | Task#2 | create the ui |
      | Task#3 | test the ui   |

  Scenario Outline: Claim Todo Tasks
    When on the sprint board "Sprint #1" page
    And click the claim button for task <task>
    Then verify that <task> is in the In Progress column

    Examples: Claim Tasks
      | task   |
      | Task#1 |
      | Task#3 |

  Scenario: Complete Some Tasks
    When on the sprint board "Sprint #1" page
    And click the claim button for the following tasks:
      | Task#1 |
      | Task#3 |
    And click the complete button for the following tasks:
      | Task#1 |
      | Task#3 |
    Then verify that the following tasks are in the completed column
      | Task#1 |
      | Task#3 |

  Scenario: Complete All Tasks
    When on the sprint board "Sprint #1" page
    And click the claim button for the following tasks:
      | Task#1 |
      | Task#2 |
      | Task#3 |
    And click the complete button for the following tasks:
      | Task#1 |
      | Task#2 |
      | Task#3 |
    Then the current active sprint should be "Sprint #2" as viewed from the "My Project #3" page
    And "#1" should be in the completed sprints on the "My Project #3" page
    And the project burndown total for Sprint# 1 should be 33 points
    And on the "My Project #3" page, the "create the ui" and "test the ui" have statuses of "Completed"

  Scenario Outline: Update Task Hours
    When on the sprint board "Sprint #1" page
    And click the claim button for task <task>
    And click update hours link for task <task>
    And enter <update hours>
    Then verify that <task> is in the <column> column

    Examples: Update Task Hours
      | task   | sprint burndown | column      | update hours |
      | Task#1 |              15 | Completed   |            0 |
      | Task#2 |               7 | In Progress |            2 |
      | Task#3 |               3 | In Progress |            1 |
