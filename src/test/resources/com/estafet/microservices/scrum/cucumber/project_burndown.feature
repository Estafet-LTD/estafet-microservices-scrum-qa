Feature: Project Burndown

  Scenario: Base Project with the following stories and tasks for the active sprint
    Given these projects have already been created:
      | title         | number of sprints | length of sprint |
      | My Project #3 |                 3 |                5 |
    And has the a backlog consisting of the following stories and completed tasks for each sprint:
      | story                | story points | tasks               | sprint   |
      | create the ui        |           13 | Task#1:5, Task#2:10 | Sprint#1 |
      | test the ui          |           40 | Task#3:5            | Sprint#1 |
      | back end development |            5 | Task#4:8, Task#5:13 | Sprint#2 |
      | database work        |            8 | Task#6:3            | Sprint#3 |
      | my work              |           20 | Task#7:12           | Sprint#3 |
    Then the corresponding project burndown for "My Project #3" will match the following:
    	| Sprint #0 | Sprint #1 | Sprint #2 | Sprint #3 |
    	| 86				| 33				| 28				| 0					|
     
