package com.estafet.microservices.scrum.cucumber;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.estafet.microservices.scrum.lib.commons.wait.WaitUntil;
import com.estafet.microservices.scrum.lib.data.db.ServiceDatabases;
import com.estafet.microservices.scrum.lib.data.project.Project;
import com.estafet.microservices.scrum.lib.data.project.ProjectDataSetBuilder;
import com.estafet.microservices.scrum.lib.data.sprint.Sprint;
import com.estafet.microservices.scrum.lib.data.story.StoryDataSetBuilder;
import com.estafet.microservices.scrum.lib.selenium.pages.home.HomePage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectListPage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectPage;
import com.estafet.microservices.scrum.lib.selenium.pages.sprint.SprintBoardPage;
import com.estafet.microservices.scrum.lib.selenium.pages.sprint.SprintBoardPageToDoTask;
import com.estafet.microservices.scrum.lib.selenium.pages.sprint.SprintPage;
import com.estafet.microservices.scrum.lib.selenium.pages.task.UpdateTaskHoursPage;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SprintSteps {

	HomePage homePage;
	ProjectPage projectPage;
	SprintPage sprintPage;
	SprintBoardPage sprintBoardPage;
	UpdateTaskHoursPage updateTaskHoursPage;

	@Before("@sprint")
	public void before() {
		ServiceDatabases.clean();
		homePage = new HomePage();
	}

	@After("@sprint")
	public void after() {
	    Optional.ofNullable(homePage).ifPresent(pageToClose -> pageToClose.close());
	}

	@Given("^these projects have already been created for the sprint:$")
	public void these_projects_have_already_been_created_for_the_sprint(final DataTable dataTable) throws Throwable {
		final Project project = new ProjectDataSetBuilder().setData(dataTable.raw()).build().get(0);
		final ProjectListPage projectListPage = homePage.clickHereLink();
		assertTrue(projectListPage.isLoaded());
		projectPage = projectListPage.clickProjectLink(project.getTitle());
		assertTrue(projectPage.isLoaded());
	}

	@Given("^has the following stories and tasks:$")
	public void has_the_following_stories_and_tasks(final DataTable dataTable) throws Throwable {
		new StoryDataSetBuilder().setData(dataTable.raw()).setProjectId(projectPage.getProjectId()).build();
	}

	@When("^on \"([^\"]*)\" sprint page, click the Add to Sprint lick for the available story create the ui$")
	public void on_sprint_page_click_the_Add_to_Sprint_lick_for_the_available_story_create_the_ui(final String sprint)
			throws Throwable {
		sprintPage = projectPage.clickActiveSprintLink();
		assertTrue(sprintPage.isLoaded());
		assertThat(sprintPage.getName(), is(sprint));
	}

	@Then("^story create the ui will move into the sprint stories section$")
	public void story_create_the_ui_will_move_into_the_sprint_stories_section() throws Throwable {
		assertTrue(sprintPage.isAvailableStory("create the ui"));
		assertFalse(sprintPage.isSprintStory("create the ui"));
		sprintPage = sprintPage.clickAddToSprint("create the ui");
		assertTrue(sprintPage.isLoaded());
		assertFalse(sprintPage.isAvailableStory("create the ui"));
		assertTrue(sprintPage.isSprintStory("create the ui"));
	}

	@Then("^will have a status of \"([^\"]*)\" for create the ui$")
	public void will_have_a_status_of_for_create_the_ui(final String status) throws Throwable {
	    assertThat(sprintPage.getSprintStoryStatus("create the ui"), is(status));
	}

	@Then("^will have a status of \"([^\"]*)\" for database work$")
	public void will_have_a_status_of_for_database_work(final String status) throws Throwable {
		assertThat(sprintPage.getSprintStoryStatus("database work"), is(status));
	}

	@Then("^the sprint burndown total will be (\\d+)$")
	public void the_sprint_burndown_total_will_be(final int hours) throws Throwable {
		assertThat(Sprint.getSprint(sprintPage.getSprintId()).getSprintBurndown().getSprintDays().get(0).getHoursTotal(), is(hours));
	}

	@When("^on \"([^\"]*)\" sprint page, click the Add to Sprint lick for the available story database work$")
	public void on_sprint_page_click_the_Add_to_Sprint_lick_for_the_available_story_database_work(final String sprint)
			throws Throwable {
		sprintPage = projectPage.clickActiveSprintLink();
		assertTrue(sprintPage.isLoaded());
		assertThat(sprintPage.getName(), is(sprint));
	}

	@Then("^story database work will move into the sprint stories section$")
	public void story_database_work_will_move_into_the_sprint_stories_section() throws Throwable {
		assertTrue(sprintPage.isAvailableStory("database work"));
		assertFalse(sprintPage.isSprintStory("database work"));
		sprintPage = sprintPage.clickAddToSprint("database work");
		assertTrue(sprintPage.isLoaded());
		assertFalse(sprintPage.isAvailableStory("database work"));
		assertTrue(sprintPage.isSprintStory("database work"));
	}

	@Given("^these projects have already been created for the sprint board:$")
	public void these_projects_have_already_been_created_for_the_sprint_board(final DataTable dataTable) throws Throwable {
	    final Project project = new ProjectDataSetBuilder().setData(dataTable.raw()).build().get(0);
	    projectPage = homePage.clickHereLink().clickProjectLink(project.getTitle());
	    assertTrue(projectPage.isLoaded());
	}

	@Given("^the project burndown for \"([^\"]*)\" is (\\d+) points$")
	public void the_project_burndown_for_is_points(final String project, final int points) throws Throwable {
	    assertThat(Project.getProjectByTitle(project).getBurndown().getSprints().get(0).getPointsTotal(), is(points));
	}

	@Given("^add the following stories to \"([^\"]*)\":$")
	public void add_the_following_stories_to(final String sprint, final DataTable dataTable) throws Throwable {
		sprintPage = projectPage.clickActiveSprintLink();
		assertTrue(sprintPage.isLoaded());
		for (final List<String> row : dataTable.raw()) {
			sprintPage = sprintPage.clickAddToSprint(row.get(0));
			assertTrue(sprintPage.isLoaded());
		}
	}

	@When("^on the sprint board \"([^\"]*)\" page$")
	public void on_the_sprint_board_page(final String sprint) throws Throwable {
		sprintBoardPage = sprintPage.clickSprintBoardLink();
		assertTrue(sprintBoardPage.isLoaded());
	}

	@Then("^the following tasks are in the todo column:$")
	public void the_following_tasks_are_in_the_todo_column(final DataTable dataTable) throws Throwable {
		final List<List<String>> data = dataTable.raw();
		final List<SprintBoardPageToDoTask> todos = sprintBoardPage.getTodoTasks();
		final Iterator<SprintBoardPageToDoTask> iter = todos.iterator();
		for (int i=1; i < data.size(); i++) {
			final List<String> row = data.get(i);
			final String task = row.get(0);
			final String story = row.get(1);
			final SprintBoardPageToDoTask todo = iter.next();
			assertThat(todo.getTaskTitle(), is(task));
			assertThat(todo.getStoryTitle(), is(story));
		}
	}

	@When("^click the claim button for task Task#(\\d+)$")
	public void click_the_claim_button_for_task_Task(final int arg1) throws Throwable {
		sprintBoardPage = sprintBoardPage.getTodoTask("Task#" + arg1).claim();
		assertTrue(sprintBoardPage.isLoaded());
		assertNull(sprintBoardPage.getTodoTask("Task#" + arg1));
	}

	@Then("^verify that Task#(\\d+) is in the In Progress column$")
	public void verify_that_Task_is_in_the_In_Progress_column(final int arg1) throws Throwable {
		assertNotNull(sprintBoardPage.getInProgressTask("Task#" + arg1));
	}

	@When("^click the complete button for task Task#(\\d+)$")
	public void click_the_complete_button_for_task_Task(final int arg1) throws Throwable {
		sprintBoardPage = sprintBoardPage.getInProgressTask("Task#" + arg1).complete();
	}

	@Then("^verify that Task#(\\d+) is in the Completed column$")
	public void verify_that_Task_is_in_the_Completed_column(final int arg1) throws Throwable {
		assertNotNull(sprintBoardPage.getCompleted("Task#" + arg1));
	}

	@When("^click the claim button for the following tasks:$")
	public void click_the_claim_button_for_the_following_tasks(final DataTable dataTable) throws Throwable {
	    final List<List<String>> data = dataTable.raw();
	    for (final List<String> row : data) {
	    	sprintBoardPage = sprintBoardPage.getTodoTask(row.get(0)).claim();
	    }
	}

	@When("^click the complete button for the following tasks:$")
	public void click_the_complete_button_for_the_following_tasks(final DataTable dataTable) throws Throwable {
		final List<List<String>> data = dataTable.raw();
	    for (final List<String> row : data) {
	    	sprintBoardPage = sprintBoardPage.getInProgressTask(row.get(0)).complete();
	    }
	}

	@Then("^verify that the following tasks are in the completed column$")
	public void verify_that_the_following_tasks_are_in_the_completed_column(final DataTable dataTable) throws Throwable {
		final List<List<String>> data = dataTable.raw();
	    for (final List<String> row : data) {
	    	assertNotNull(sprintBoardPage.getCompleted(row.get(0)));
	    }
	}

	@When("^the current active sprint should be \"([^\"]*)\" as viewed from the \"([^\"]*)\" page$")
	public void the_current_active_sprint_should_be_as_viewed_from_the_page(final String sprint, final String project) throws Throwable {
		waitForTwoSprints();
	    projectPage = sprintBoardPage.clickProjectBreadCrumbLink();
	    assertTrue(projectPage.isLoaded());
	    assertThat(projectPage.getProjectTitle(), is(project));
	    assertThat(projectPage.getActiveSprintText(), is(sprint));
	}

	private void waitForTwoSprints() {
		new WaitUntil() {
			@Override
            public boolean success() {
				return Project.getProjectById(sprintBoardPage.getProjectId()).getSprints().size() == 2;
			}
		}.start();
	}

	@When("^\"([^\"]*)\" should be in the completed sprints on the \"([^\"]*)\" page$")
	public void should_be_in_the_completed_sprints_on_the_page(final String sprint, final String project) throws Throwable {
	    assertThat(projectPage.getCompletedSprints().get(0), is("Sprint " + sprint));
	}

	@Then("^the project burndown total for Sprint# (\\d+) should be (\\d+) points$")
	public void the_project_burndown_total_for_Sprint_should_be_points(final int sprint, final int points) throws Throwable {
	    assertThat(Project.getProjectById(projectPage.getProjectId()).getBurndown().getSprints().get(sprint).getPointsTotal(), is(points));
	}

	@Then("^on the \"([^\"]*)\" page, the \"([^\"]*)\" and \"([^\"]*)\" have statuses of \"([^\"]*)\"$")
	public void on_the_page_the_and_have_statuses_of(final String project, final String story1, final String story2, final String status) throws Throwable {
	    assertThat(projectPage.getStoryStatus(story1), is(status));
	    assertThat(projectPage.getStoryStatus(story2), is(status));
	}

	@When("^click update hours link for task Task#(\\d+)$")
	public void click_update_hours_link_for_task_Task(final int arg1) throws Throwable {
		updateTaskHoursPage = sprintBoardPage.getInProgressTask("Task#" + arg1).clickHoursLink();
		assertTrue(updateTaskHoursPage.isLoaded());
	}

	@When("^enter (\\d+)$")
	public void enter(final int arg1) throws Throwable {
	 	sprintBoardPage = updateTaskHoursPage.setRemainingHours(arg1).clickSubmitButton();
	 	assertTrue(sprintBoardPage.isLoaded());
	}


}
