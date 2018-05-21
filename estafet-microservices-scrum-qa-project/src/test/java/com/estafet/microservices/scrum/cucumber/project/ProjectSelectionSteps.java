package com.estafet.microservices.scrum.cucumber.project;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.util.List;

import com.estafet.microservices.scrum.lib.data.cleanser.DataCleanser;
import com.estafet.microservices.scrum.lib.data.project.ProjectBuilder;
import com.estafet.microservices.scrum.lib.selenium.pages.HomePage;
import com.estafet.microservices.scrum.lib.selenium.pages.ProjectPage;
import com.estafet.microservices.scrum.lib.selenium.pages.ProjectListPage;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ProjectSelectionSteps {

	HomePage homePage;
	ProjectListPage projectListPage;
	ProjectPage projectPage;
	
	@Given("^these projects have been created:$")
	public void these_projects_have_been_created(DataTable dataTable) {
		DataCleanser.clean();
		List<List<String>> data = dataTable.raw();
		for (int i=1; i<data.size(); i++) {
			List<String> row = data.get(i);
			new ProjectBuilder()
				.setTitle(row.get(0))
				.setNoSprints(Integer.parseInt(row.get(1)))
				.setSprintLengthDays(Integer.parseInt(row.get(2)))
				.build();
		}
		homePage = new HomePage();
	}
	
	@When("^I navigate to the project list page$")
	public void i_navigate_to_the_project_list_page() throws Throwable {
		projectListPage = homePage.clickHereLink();
		assertTrue(projectListPage.isLoaded());
	}

	@Then("^the project list should contain:$")
	public void the_project_list_should_contain(DataTable dataTable) {
		List<List<String>> data = dataTable.raw();
		for (int i=0; i<data.size(); i++) {
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

}
