Feature: Project Burndown

  Background: Base Project with the following stories and tasks for the active sprint
    Given these projects have already been created:
      | title                | number of sprints | length of sprint |
      | My Project #3        |                 3 |                5 |
    And has the following stories and tasks:
    	| story                 | story points	| tasks								| sprint		|
      | create the ui					| 13						| Task#1:5, Task#2:10 | Sprint#1	|
      | test the ui						| 40						| Task#3:5						| Sprint#1	|
      | back end development	| 5							| Task#4:8, Task#5:13 |						|
      | database work					| 8							| Task#6:3						|						|
      | my work								| 20						| Task#7:12						|						|
