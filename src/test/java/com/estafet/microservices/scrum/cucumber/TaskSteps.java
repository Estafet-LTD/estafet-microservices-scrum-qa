package com.estafet.microservices.scrum.cucumber;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.util.List;

import com.estafet.microservices.scrum.lib.data.db.ServiceDatabases;
import com.estafet.microservices.scrum.lib.data.project.Project;
import com.estafet.microservices.scrum.lib.data.project.ProjectCompleteDataSetBuilder;
import com.estafet.microservices.scrum.lib.data.project.ProjectDataSetBuilder;
import com.estafet.microservices.scrum.lib.data.story.StoryBuilder;
import com.estafet.microservices.scrum.lib.selenium.pages.home.HomePage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectListPage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectPage;
import com.estafet.microservices.scrum.lib.selenium.pages.story.StoryPage;
import com.estafet.microservices.scrum.lib.selenium.pages.task.AddTaskPage;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TaskSteps {

	HomePage homePage;
	ProjectPage projectPage;
	AddTaskPage addTaskPage;
	StoryPage storyPage;
	String task;

	@Before("@task")
	public void before() {
		ServiceDatabases.clean();
		homePage = new HomePage();
	}

	@After("@task")
	public void after() {
		homePage.close();
	}

	@Given("^the following project has already been created for add task:$")
	public void the_following_project_has_already_been_created_for_add_task(DataTable dataTable) throws Throwable {
		new ProjectDataSetBuilder().setData(dataTable.raw()).build().get(0);
	}

	@Given("^\"([^\"]*)\" has the following stories:$")
	public void has_the_following_stories(String project, DataTable dataTable) throws Throwable {
		List<List<String>> data = dataTable.raw();
		for (int i=1; i < data.size(); i++) {
			List<String> row = data.get(i);
			String title = row.get(0);
			Integer storyPoints = Integer.parseInt(row.get(1));
			new StoryBuilder()
				.setTitle(title)
				.setStorypoints(storyPoints)
				.setProjectId(Project.getProjectByTitle(project).getId())
				.build();
		}
		ProjectListPage projectListPage = homePage.clickHereLink();
		assertTrue(projectListPage.isLoaded());
		projectPage = projectListPage.clickProjectLink(project);
		assertTrue(projectPage.isLoaded());
	}

	@When("^I click Add Task from the create the ui page$")
	public void i_click_Add_Task_from_the_create_the_ui_page() throws Throwable {
		storyPage = projectPage.clickStoryLink("create the ui");
		assertTrue(storyPage.isLoaded());
		addTaskPage = storyPage.clickAddTaskLink();
		assertTrue(addTaskPage.isLoaded());
	}

	@When("^enter a title of My Task #(\\d+)$")
	public void enter_a_title_of_My_Task(int taskNo) throws Throwable {
		addTaskPage.setTitle("My Task #" + taskNo);
	}

	@When("^estimate of (\\d+)$")
	public void estimate_of(int estimate) throws Throwable {
		addTaskPage.setEstimate(estimate);
	}

	@When("^click submit on the add task page$")
	public void click_submit_on_the_add_task_page() throws Throwable {
		storyPage = addTaskPage.clickSubmitButton();
		assertTrue(storyPage.isLoaded());
	}

	@Then("^I should be able to see the task My Task #(\\d+) added to the create the ui page$")
	public void i_should_be_able_to_see_the_task_My_Task_added_to_the_create_the_ui_page(int arg1) throws Throwable {		
		task = "My Task #" + arg1;
		assertTrue(storyPage.getTasks().contains(task));
	}

	@Then("^with initial hours (\\d+)$")
	public void with_initial_hours(int arg1) throws Throwable {
		assertThat(storyPage.getTaskInitialHours(task), is(arg1));
	}

	@Then("^with remaining hours (\\d+)$")
	public void with_remaining_hours(int arg1) throws Throwable {
		assertThat(storyPage.getTaskRemainingHours(task), is(arg1));
	}

	@Then("^the status for the create the ui should now be Planning$")
	public void the_status_for_the_create_the_ui_should_now_be_Planning() throws Throwable {
		assertThat(storyPage.getStatus(), is("Planning"));
	}

	@When("^I click Add Task from the back end development page$")
	public void i_click_Add_Task_from_the_back_end_development_page() throws Throwable {
		storyPage = projectPage.clickStoryLink("back end development");
		assertTrue(storyPage.isLoaded());
		addTaskPage = storyPage.clickAddTaskLink();
		assertTrue(addTaskPage.isLoaded());
	}

	@Then("^I should be able to see the task My Task #(\\d+) added to the back end development page$")
	public void i_should_be_able_to_see_the_task_My_Task_added_to_the_back_end_development_page(int arg1)
			throws Throwable {
		task = "My Task #" + arg1;
		assertTrue(storyPage.getTasks().contains(task));
	}

	@Then("^the status for the back end development should now be Planning$")
	public void the_status_for_the_back_end_development_should_now_be_Planning() throws Throwable {
		assertThat(storyPage.getStatus(), is("Planning"));
	}

	@Given("^has a backlog consisting of the following completed stories:$")
	public void has_a_backlog_consisting_of_the_following_completed_stories(DataTable arg1) throws Throwable {
		new ProjectCompleteDataSetBuilder().setData(arg1.raw()).setProjectTitle("My Project #3").build();
		ProjectListPage projectListPage = homePage.clickHereLink();
		assertTrue(projectListPage.isLoaded());
		projectPage = projectListPage.clickProjectLink("My Project #3");
		assertTrue(projectPage.isLoaded());
	}

	@When("^I attempt to add a task \"([^\"]*)\" to story \"([^\"]*)\"$")
	public void i_attempt_to_add_a_task_to_story(String task, String story) throws Throwable {
		storyPage = projectPage.clickStoryLink(story);
		assertTrue(storyPage.isLoaded());
		addTaskPage = storyPage.clickAddTaskLink();
		assertTrue(addTaskPage.isLoaded());
		addTaskPage.setTitle(task);
		storyPage = addTaskPage.clickSubmitButton();
	}

	@Then("^the \"([^\"]*)\" will be displayed$")
	public void the_will_be_displayed(String arg1) throws Throwable {
		assertFalse(storyPage.isLoaded());
	}

	@Then("^the task \"([^\"]*)\" will not be created$")
	public void the_task_will_not_be_created(String arg1) throws Throwable {
		homePage = homePage.restart();
		projectPage = homePage.clickHereLink().clickProjectLink("My Project #3");
		assertTrue(projectPage.isLoaded());
		storyPage = projectPage.clickStoryLink("create the ui");
		assertThat(storyPage.getTasks().size(), is(1));
	}

}
