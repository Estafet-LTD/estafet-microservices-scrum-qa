package com.estafet.microservices.scrum.cucumber.project;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class NewProjectSteps {

	@Given("^these projects have already been created:$")
	public void these_projects_have_already_been_created(DataTable arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		throw new PendingException();
	}

	@When("^I click new project from the project list screen and submit the following values:$")
	public void i_click_new_project_from_the_project_list_screen_and_submit_the_following_values(DataTable arg1)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		throw new PendingException();
	}

	@Then("^I should be able to view the new project called \"([^\"]*)\" on the project page$")
	public void i_should_be_able_to_view_the_new_project_called_on_the_project_page(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^the projects list page should now display the following projects:$")
	public void the_projects_list_page_should_now_display_the_following_projects(DataTable arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		// E,K,V must be a scalar (String, Integer, Date, enum etc)
		throw new PendingException();
	}

	@Then("^on the \"([^\"]*)\" project page, for there should be a link to a sprint called \"([^\"]*)\"$")
	public void on_the_project_page_for_there_should_be_a_link_to_a_sprint_called(String arg1, String arg2)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^the \"([^\"]*)\" link should navigate me to an \"([^\"]*)\" sprint called \"([^\"]*)\"$")
	public void the_link_should_navigate_me_to_an_sprint_called(String arg1, String arg2, String arg3)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^on the \"([^\"]*)\" there should be a link for Project Burndown that shows me the project burndown$")
	public void on_the_there_should_be_a_link_for_Project_Burndown_that_shows_me_the_project_burndown(String arg1)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^the project burndown should consist of (\\d+) sprints, each totalling (\\d+) story point effort remaining$")
	public void the_project_burndown_should_consist_of_sprints_each_totalling_story_point_effort_remaining(int arg1,
			int arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^on \"([^\"]*)\" there should be a link for Sprint Burndown that shows me the sprint burndown$")
	public void on_there_should_be_a_link_for_Sprint_Burndown_that_shows_me_the_sprint_burndown(String arg1)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^the sprint burndown should contain (\\d+) days$")
	public void the_sprint_burndown_should_contain_days(int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

}
