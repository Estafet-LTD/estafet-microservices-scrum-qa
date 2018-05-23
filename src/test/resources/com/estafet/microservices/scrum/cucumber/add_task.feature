Feature: Add Task
      
  Scenario Outline: Create a new task
    Given the following project has already been created for add task:
      | title                | number of sprints | length of sprint |
      | My Project #3        |                 3 |                5 |
    And has the following stories:
    	| title                 | story points	|
      | create the ui					| 13						|
      | test the ui						| 40						|
      | back end development	| 5							|
      | database work					| 8							|   
    When I click Add Task from the story "test the ui" page 
    	And enter a title of <title> 
    	And initial hours of <initial hours>
    	And click submit on the add task page
    Then I should be able to see the task <title> added to the story "test the ui" page
	    And with initial hours <initial hours>
	    And with remaining hours <remaining hours>
	    And the status for the story "test the ui" should now be "Planning"
    Examples:
    	| title           | initial hours	| remaining hours		|
      | My Task #1			| 13						| 13								|
      | My Task #2			| 40						| 40								|
      | My Task #3			| 5							| 5									|
      | My Task #4			| 8							| 8									| 


