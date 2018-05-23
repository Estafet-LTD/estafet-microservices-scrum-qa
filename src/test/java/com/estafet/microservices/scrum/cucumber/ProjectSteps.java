package com.estafet.microservices.scrum.cucumber;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.util.List;

import com.estafet.microservices.scrum.lib.data.cleanser.DataCleanser;
import com.estafet.microservices.scrum.lib.data.project.Project;
import com.estafet.microservices.scrum.lib.data.project.ProjectBuilder;
import com.estafet.microservices.scrum.lib.data.project.ProjectBurndown;
import com.estafet.microservices.scrum.lib.data.sprint.Sprint;
import com.estafet.microservices.scrum.lib.data.story.Story;
import com.estafet.microservices.scrum.lib.data.story.StoryBuilder;
import com.estafet.microservices.scrum.lib.data.task.TaskBuilder;
import com.estafet.microservices.scrum.lib.selenium.pages.home.HomePage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectListPage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectPage;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ProjectSteps {

	HomePage homePage;
	ProjectListPage projectListPage;
	ProjectPage projectPage;

	@Before
	public void before() {
		DataCleanser.clean();
		homePage = new HomePage();
	}

	@After
	public void after() {
		homePage.close();
	}

	@Given("^these projects have been created:$")
	public void these_projects_have_been_created(DataTable dataTable) {
		List<List<String>> data = dataTable.raw();
		for (int i = 1; i < data.size(); i++) {
			List<String> row = data.get(i);
			new ProjectBuilder().setTitle(row.get(0)).setNoSprints(Integer.parseInt(row.get(1)))
					.setSprintLengthDays(Integer.parseInt(row.get(2))).build();
		}
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

}
