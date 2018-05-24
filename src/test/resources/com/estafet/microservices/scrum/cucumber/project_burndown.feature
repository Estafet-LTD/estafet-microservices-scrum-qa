@project
Feature: Project Burndown

  Scenario: Complete multiple sprints and review project burndown
    Given these projects have been created:
      | title         | number of sprints | length of sprint |
      | My Project #3 |                 3 |                5 |
    And has a backlog consisting of the following completed stories for each sprint of "My Project #3" project:
      | story                | story points | sprint   |
      | create the ui        |           13 | Sprint#1 |
      | test the ui          |           40 | Sprint#1 |
      | back end development |            5 | Sprint#2 |
      | database work        |            8 | Sprint#3 |
      | my work              |           20 | Sprint#3 |
    Then the corresponding project burndown for "My Project #3" will match the following:
      | Sprint #0 | Sprint #1 | Sprint #2 | Sprint #3 | Sprint #4 |
      |        86 |        33 |        28 |         0 |         0 |
