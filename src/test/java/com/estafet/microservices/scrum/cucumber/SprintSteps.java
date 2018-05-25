package com.estafet.microservices.scrum.cucumber;

import static org.junit.Assert.*;

import static org.hamcrest.core.Is.*;

import com.estafet.microservices.scrum.lib.data.ServiceDatabases;
import com.estafet.microservices.scrum.lib.data.project.Project;
import com.estafet.microservices.scrum.lib.data.project.ProjectDataSetBuilder;
import com.estafet.microservices.scrum.lib.data.sprint.Sprint;
import com.estafet.microservices.scrum.lib.data.story.StoryDataSetBuilder;
import com.estafet.microservices.scrum.lib.selenium.pages.home.HomePage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectListPage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectPage;
import com.estafet.microservices.scrum.lib.selenium.pages.sprint.SprintPage;

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

	@Before("@sprint")
	public void before() {
		ServiceDatabases.clean();
		homePage = new HomePage();
	}

	@After("@sprint")
	public void after() {
		homePage.close();
	}

	@Given("^these projects have already been created for the sprint:$")
	public void these_projects_have_already_been_created_for_the_sprint(DataTable dataTable) throws Throwable {
		Project project = new ProjectDataSetBuilder().setData(dataTable.raw()).build().get(0);
		ProjectListPage projectListPage = homePage.clickHereLink();
		assertTrue(projectListPage.isLoaded());
		projectPage = projectListPage.clickProjectLink(project.getTitle());
		assertTrue(projectPage.isLoaded());
	}

	@Given("^has the following stories and tasks:$")
	public void has_the_following_stories_and_tasks(DataTable dataTable) throws Throwable {
		new StoryDataSetBuilder().setData(dataTable.raw()).setProjectId(projectPage.getProjectId()).build();
	}

	@When("^on \"([^\"]*)\" sprint page, click the Add to Sprint lick for the available story create the ui$")
	public void on_sprint_page_click_the_Add_to_Sprint_lick_for_the_available_story_create_the_ui(String sprint)
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
	public void will_have_a_status_of_for_create_the_ui(String status) throws Throwable {
	    assertThat(sprintPage.getSprintStoryStatus("create the ui"), is(status));
	}

	@Then("^will have a status of \"([^\"]*)\" for database work$")
	public void will_have_a_status_of_for_database_work(String status) throws Throwable {
		assertThat(sprintPage.getSprintStoryStatus("database work"), is(status));
	}

	@Then("^the sprint burndown total will be (\\d+)$")
	public void the_sprint_burndown_total_will_be(int hours) throws Throwable {
		assertThat(Sprint.getSprint(sprintPage.getSprintId()).getSprintBurndown().getSprintDays().get(0).getHoursTotal(), is(hours));
	}

	@When("^on \"([^\"]*)\" sprint page, click the Add to Sprint lick for the available story database work$")
	public void on_sprint_page_click_the_Add_to_Sprint_lick_for_the_available_story_database_work(String sprint)
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
	
}
