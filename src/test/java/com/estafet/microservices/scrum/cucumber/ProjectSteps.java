package com.estafet.microservices.scrum.cucumber;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import com.estafet.microservices.scrum.lib.data.db.ServiceDatabases;
import com.estafet.microservices.scrum.lib.data.project.Project;
import com.estafet.microservices.scrum.lib.data.project.ProjectBurndown;
import com.estafet.microservices.scrum.lib.data.project.ProjectBurndownSprint;
import com.estafet.microservices.scrum.lib.data.project.ProjectCompleteDataSetBuilder;
import com.estafet.microservices.scrum.lib.data.project.ProjectDataSetBuilder;
import com.estafet.microservices.scrum.lib.data.sprint.Sprint;
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
	public void before() throws Exception{
		ServiceDatabases.clean();
		homePage = new HomePage();
	}

	@After("@project")
	public void after() {
		Optional.ofNullable(homePage).ifPresent(pageToClose -> pageToClose.close());
	}

	@Given("^these projects have been created:$")
	public void these_projects_have_been_created(final DataTable dataTable) {
		new ProjectDataSetBuilder().setData(dataTable.raw()).build();
	}

	@When("^I navigate to the project list page$")
	public void i_navigate_to_the_project_list_page() throws Throwable {
		projectListPage = homePage.clickHereLink();
		assertTrue(projectListPage.isLoaded());
	}

	@Then("^the project list should contain:$")
	public void the_project_list_should_contain(final DataTable dataTable) {
		final List<List<String>> data = dataTable.raw();
		for (int i = 0; i < data.size(); i++) {
			assertThat(data.get(i).get(0), is(projectListPage.getProjects().get(i)));
		}
	}

	@When("^I /navigate to the project list page and click on the \"([^\"]*)\" project/$")
	public void i_navigate_to_the_project_list_page_and_click_on_the_project(final String project) throws Throwable {
		projectListPage = homePage.clickHereLink();
		assertTrue(projectListPage.isLoaded());
		projectPage = projectListPage.clickProjectLink(project);
	}

	@Then("^I should /see to the project page for the \"([^\"]*)\" project/$")
	public void i_should_see_to_the_project_page_for_the_project(final String project) throws Throwable {
		assertTrue(projectPage.isLoaded());
		assertThat(projectPage.getProjectTitle(), is(project));
	}

	@Given("^has a backlog consisting of the following completed stories for each sprint of \"([^\"]*)\" project:$")
	public void has_a_backlog_consisting_of_the_following_completed_stories_for_each_sprint_of_project(
			final String projectTitle, final DataTable dataTable) throws Throwable {
		new ProjectCompleteDataSetBuilder().setProjectTitle(projectTitle).setData(dataTable.raw()).build();
	}

	@Then("^the corresponding project burndown for \"([^\"]*)\" will match the following:$")
	public void the_corresponding_project_burndown_for_will_match_the_following(final String project, final DataTable dataTable)
			throws Throwable {
		final List<List<String>> data = dataTable.raw();
		final List<String> sprints = data.get(0);
		final List<String> burndown = data.get(1);
		final ProjectBurndown projectBurndown = Project.getProjectByTitle(project).getBurndown();
		for (int i = 0; i < sprints.size(); i++) {
			assertThat(projectBurndown.getSprints().get(i).getName(), is(sprints.get(i)));
			assertThat(projectBurndown.getSprints().get(i).getPointsTotal(), is(Integer.parseInt(burndown.get(i))));
		}

	}

	@When("^I click new project from the project list screen and submit the following values:$")
	public void i_click_new_project_from_the_project_list_screen_and_submit_the_following_values(final DataTable dataTable) throws Throwable {
		projectListPage = homePage.clickHereLink();
		assertTrue(projectListPage.isLoaded());
		final NewProjectPage newProjectPage = projectListPage.clickNewProjectLink();
		assertTrue(newProjectPage.isLoaded());
		final List<List<String>> data = dataTable.raw();
		final List<String> values = data.get(1);
		projectPage = newProjectPage.setProjectTitle(values.get(0))
									.setNoSprints(Integer.parseInt(values.get(1)))
									.setSprintLengthDays(Integer.parseInt(values.get(2)))
									.clickSubmitButton();
	}

	@Then("^I should be able to view the new project called \"([^\"]*)\" on the project page$")
	public void i_should_be_able_to_view_the_new_project_called_on_the_project_page(final String project) throws Throwable {
		assertTrue(projectPage.isLoaded());
		assertThat(projectPage.getProjectTitle(), is(project));
	}

	@Then("^the projects list page should now display the following projects:$")
	public void the_projects_list_page_should_now_display_the_following_projects(final DataTable dataTable) throws Throwable {
		projectListPage = projectPage.clickProjectsBreadCrumbLink();
		assertTrue(projectListPage.isLoaded());
		final List<List<String>> data = dataTable.raw();
		for (int i=0; i<data.size(); i++) {
			final String project = data.get(i).get(0);
			assertThat(projectListPage.getProjects().get(i), is(project));
		}
	}

	@Then("^on the \"([^\"]*)\" project page, for there should be a link to a sprint called \"([^\"]*)\"$")
	public void on_the_project_page_for_there_should_be_a_link_to_a_sprint_called(final String project, final String sprint) throws Throwable {
		assertTrue(projectPage.isLoaded());
		assertThat(projectPage.getActiveSprintText(), is(sprint));
	}

	@Then("^the \"([^\"]*)\" link should navigate me to an \"([^\"]*)\" sprint called \"([^\"]*)\"$")
	public void the_link_should_navigate_me_to_an_sprint_called(final String sprintLinkText, final String status, final String sprint) throws Throwable {
		assertTrue(projectPage.isLoaded());
		final SprintPage sprintPage = projectPage.clickActiveSprintLink();
		assertTrue(sprintPage.isLoaded());
		assertThat(sprintPage.getStatus(), is(status));
		assertThat(sprintPage.getName(), is(sprint));
	}

	@Then("^on the \"([^\"]*)\" there should be a link for Project Burndown that shows me the project burndown$")
	public void on_the_there_should_be_a_link_for_Project_Burndown_that_shows_me_the_project_burndown(final String project) throws Throwable {
	    projectBurndownPage = projectPage.clickProjectBurndownLink();
	    assertTrue(projectBurndownPage.isLoaded());
	}

	@Then("^the project burndown should consist of (\\d+) sprints, each totalling (\\d+) story point effort remaining$")
	public void the_project_burndown_should_consist_of_sprints_each_totalling_story_point_effort_remaining(final int noSprints, final int pointsTotal) throws Throwable {
		final ProjectBurndown projectBurndown = Project.getProjectById(projectBurndownPage.getProjectId()).getBurndown();
		assertThat(projectBurndown.getSprints().size()-1, is(noSprints));
		for (final ProjectBurndownSprint sprint : projectBurndown.getSprints()) {
			assertThat(sprint.getPointsTotal(), is(pointsTotal));
		}
	}

	@Then("^on \"([^\"]*)\" there should be a link for Sprint Burndown that shows me the sprint burndown$")
	public void on_there_should_be_a_link_for_Sprint_Burndown_that_shows_me_the_sprint_burndown(final String project) throws Throwable {
		assertTrue(projectPage.isLoaded());
	    sprintBurndownPage = projectPage.clickSprintBurndownLink();
	    assertTrue(sprintBurndownPage.isLoaded());
	}

	@Then("^the sprint burndown should contain (\\d+) days$")
	public void the_sprint_burndown_should_contain_days(final int noDays) throws Throwable {
	   assertThat(Sprint.getSprint(sprintBurndownPage.getSprintId()).getSprintBurndown().getSprintDays().size()-1, is(noDays));
	}

}
