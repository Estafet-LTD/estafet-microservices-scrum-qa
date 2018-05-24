package com.estafet.microservices.scrum.cucumber;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.util.List;

import com.estafet.microservices.scrum.lib.data.ServiceDatabases;
import com.estafet.microservices.scrum.lib.data.project.Project;
import com.estafet.microservices.scrum.lib.data.project.ProjectBurndown;
import com.estafet.microservices.scrum.lib.data.project.ProjectBurndownSprint;
import com.estafet.microservices.scrum.lib.data.sprint.Sprint;
import com.estafet.microservices.scrum.lib.data.story.Story;
import com.estafet.microservices.scrum.lib.data.story.StoryBuilder;
import com.estafet.microservices.scrum.lib.data.task.TaskBuilder;
import com.estafet.microservices.scrum.lib.selenium.pages.home.HomePage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.NewProjectPage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectBurndownPage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectListPage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectPage;
import com.estafet.microservices.scrum.lib.selenium.pages.sprint.SprintBurndownPage;
import com.estafet.microservices.scrum.lib.selenium.pages.sprint.SprintPage;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ProjectSteps {

	HomePage homePage;
	ProjectListPage projectListPage;
	ProjectPage projectPage;
	ProjectBurndownPage projectBurndownPage;
	SprintBurndownPage sprintBurndownPage;

	@Before("@project")
	public void before() {
		ServiceDatabases.clean();
		homePage = new HomePage();
	}

	@After("@project")
	public void after() {
		homePage.close();
	}

	@Given("^these projects have been created:$")
	public void these_projects_have_been_created(DataTable dataTable) {
		new ProjectDataTableBuilder().setDataTable(dataTable).build();
	}

	@When("^I navigate to the project list page$")
	public void i_navigate_to_the_project_list_page() throws Throwable {
		projectListPage = homePage.clickHereLink();
		assertTrue(projectListPage.isLoaded());
	}

	@Then("^the project list should contain:$")
	public void the_project_list_should_contain(DataTable dataTable) {
		List<List<String>> data = dataTable.raw();
		for (int i = 0; i < data.size(); i++) {
			assertThat(data.get(i).get(0), is(projectListPage.getProjects().get(i)));
		}
	}

	@When("^I /navigate to the project list page and click on the \"([^\"]*)\" project/$")
	public void i_navigate_to_the_project_list_page_and_click_on_the_project(String project) throws Throwable {
		projectListPage = homePage.clickHereLink();
		assertTrue(projectListPage.isLoaded());
		projectPage = projectListPage.clickProjectLink(project);
	}

	@Then("^I should /see to the project page for the \"([^\"]*)\" project/$")
	public void i_should_see_to_the_project_page_for_the_project(String project) throws Throwable {
		assertTrue(projectPage.isLoaded());
		assertThat(projectPage.getProjectTitle(), is(project));
	}

	@Given("^has a backlog consisting of the following completed stories for each sprint of \"([^\"]*)\" project:$")
	public void has_a_backlog_consisting_of_the_following_completed_stories_for_each_sprint_of_project(
			String projectTitle, DataTable dataTable) throws Throwable {
		List<List<String>> data = dataTable.raw();
		Project project = Project.getProjectByTitle(projectTitle);
		for (int i = 1; i < data.size(); i++) {
			String storyTitle = data.get(i).get(0);
			Integer storypoints = Integer.parseInt(data.get(i).get(1));
			Story story = new StoryBuilder()
							.setProjectId(project.getId())
							.setTitle(storyTitle)
							.setStorypoints(storypoints)
							.build();
			new TaskBuilder()
				.setStoryId(story.getId())
				.build();
		}
		String previousSprint = project.getActiveSprint().getName();
		Sprint sprintObject = null;
		for (int i = 1; i < data.size(); i++) {
			String sprint = data.get(i).get(2);
			String storyTitle = data.get(i).get(0);
			if (!sprint.equals(previousSprint)) {
				project.getSprint(previousSprint).complete();
			}
			sprintObject = project.getSprint(sprint);
			project.getStory(storyTitle).addToSprint(sprintObject.getId());
			previousSprint = sprint;
		}
		sprintObject.complete();
	}

	@Then("^the corresponding project burndown for \"([^\"]*)\" will match the following:$")
	public void the_corresponding_project_burndown_for_will_match_the_following(String project, DataTable dataTable)
			throws Throwable {
		List<List<String>> data = dataTable.raw();
		List<String> sprints = data.get(0);
		List<String> burndown = data.get(1);
		ProjectBurndown projectBurndown = Project.getProjectByTitle(project).getBurndown();
		for (int i = 0; i < sprints.size(); i++) {
			assertThat(projectBurndown.getSprints().get(i).getName(), is(sprints.get(i)));
			assertThat(projectBurndown.getSprints().get(i).getPointsTotal(), is(Integer.parseInt(burndown.get(i))));
		}

	}

	@When("^I click new project from the project list screen and submit the following values:$")
	public void i_click_new_project_from_the_project_list_screen_and_submit_the_following_values(DataTable dataTable) throws Throwable {
		projectListPage = homePage.clickHereLink();
		assertTrue(projectListPage.isLoaded());
		NewProjectPage newProjectPage = projectListPage.clickNewProjectLink();
		assertTrue(newProjectPage.isLoaded());
		List<List<String>> data = dataTable.raw();
		List<String> values = data.get(1);
		projectPage = newProjectPage.setProjectTitle(values.get(0))
									.setNoSprints(Integer.parseInt(values.get(1)))
									.setSprintLengthDays(Integer.parseInt(values.get(2)))
									.clickSubmitButton();
		Project.getProjectById(projectPage.getProjectId()).newProjectWait();
	}

	@Then("^I should be able to view the new project called \"([^\"]*)\" on the project page$")
	public void i_should_be_able_to_view_the_new_project_called_on_the_project_page(String project) throws Throwable {
		assertTrue(projectPage.isLoaded());
		assertThat(projectPage.getProjectTitle(), is(project));
	}

	@Then("^the projects list page should now display the following projects:$")
	public void the_projects_list_page_should_now_display_the_following_projects(DataTable dataTable) throws Throwable {
		projectListPage = projectPage.clickProjectsBreadCrumbLink();
		assertTrue(projectListPage.isLoaded());
		List<List<String>> data = dataTable.raw();
		for (int i=0; i<data.size(); i++) {
			String project = data.get(i).get(0);
			assertThat(projectListPage.getProjects().get(i), is(project));
		}
	}

	@Then("^on the \"([^\"]*)\" project page, for there should be a link to a sprint called \"([^\"]*)\"$")
	public void on_the_project_page_for_there_should_be_a_link_to_a_sprint_called(String project, String sprint) throws Throwable {
		assertThat(projectPage.getActiveSprintText(), is(sprint));
	}

	@Then("^the \"([^\"]*)\" link should navigate me to an \"([^\"]*)\" sprint called \"([^\"]*)\"$")
	public void the_link_should_navigate_me_to_an_sprint_called(String sprintLinkText, String status, String sprint) throws Throwable {
		assertTrue(projectPage.isLoaded());
		SprintPage sprintPage = projectPage.clickActiveSprintLink();
		assertTrue(sprintPage.isLoaded());
		assertThat(sprintPage.getStatus(), is(status));
		assertThat(sprintPage.getName(), is(sprint));
	}

	@Then("^on the \"([^\"]*)\" there should be a link for Project Burndown that shows me the project burndown$")
	public void on_the_there_should_be_a_link_for_Project_Burndown_that_shows_me_the_project_burndown(String project) throws Throwable {
	    projectBurndownPage = projectPage.clickProjectBurndownLink();
	    assertTrue(projectBurndownPage.isLoaded());
	}

	@Then("^the project burndown should consist of (\\d+) sprints, each totalling (\\d+) story point effort remaining$")
	public void the_project_burndown_should_consist_of_sprints_each_totalling_story_point_effort_remaining(int noSprints, int pointsTotal) throws Throwable {
		ProjectBurndown projectBurndown = Project.getProjectById(projectBurndownPage.getProjectId()).getBurndown();
		assertThat(projectBurndown.getSprints().size()-1, is(noSprints));	
		for (ProjectBurndownSprint sprint : projectBurndown.getSprints()) {
			assertThat(sprint.getPointsTotal(), is(pointsTotal));	
		}
	}

	@Then("^on \"([^\"]*)\" there should be a link for Sprint Burndown that shows me the sprint burndown$")
	public void on_there_should_be_a_link_for_Sprint_Burndown_that_shows_me_the_sprint_burndown(String project) throws Throwable {
		assertTrue(projectPage.isLoaded());
	    sprintBurndownPage = projectPage.clickSprintBurndownLink();
	    assertTrue(sprintBurndownPage.isLoaded());
	}

	@Then("^the sprint burndown should contain (\\d+) days$")
	public void the_sprint_burndown_should_contain_days(int noDays) throws Throwable {
	   assertThat(Sprint.getSprint(sprintBurndownPage.getSprintId()).getSprintBurndown().getSprintDays().size()-1, is(noDays));
	}	
	
}
