package com.estafet.microservices.scrum.cucumber;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import com.estafet.microservices.scrum.lib.data.db.ServiceDatabases;
import com.estafet.microservices.scrum.lib.data.project.ProjectDataSetBuilder;
import com.estafet.microservices.scrum.lib.selenium.pages.home.HomePage;
import com.estafet.microservices.scrum.lib.selenium.pages.project.ProjectPage;
import com.estafet.microservices.scrum.lib.selenium.pages.story.AddStoryPage;
import com.estafet.microservices.scrum.lib.selenium.pages.story.StoryPage;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StorySteps {

	HomePage homePage;
	ProjectPage projectPage;
	AddStoryPage addStoryPage;
	StoryPage storyPage;

	@Before("@story")
	public void before() {
		ServiceDatabases.clean();
		homePage = new HomePage();
	}

	@After("@story")
	public void after() {
	    Optional.ofNullable(homePage).ifPresent(pageToClose -> pageToClose.close());
	}

	@Given("^the following project has already been created for add story:$")
	public void the_following_project_has_already_been_created_for_add_story(final DataTable dataTable) throws Throwable {
		new ProjectDataSetBuilder().setData(dataTable.raw()).build().get(0);
	}

	@When("^I click Add Story from the project \"([^\"]*)\" page$")
	public void i_click_Add_Story_from_the_project_page(final String project) throws Throwable {
		projectPage = homePage.clickHereLink().clickProjectLink(project);
		assertTrue(projectPage.isLoaded());
		addStoryPage = projectPage.clickAddStoryLink();
		assertTrue(addStoryPage.isLoaded());
	}

	@When("^enter a title of \"([^\"]*)\"$")
	public void enter_a_title_of(final String arg1) throws Throwable {
	   addStoryPage.setTitle(arg1);
	}

	@When("^(\\d+) story points$")
	public void story_points(final int arg1) throws Throwable {
	    addStoryPage.setStoryPointsField(arg1);
	}

	@When("^submit the story$")
	public void submit_the_story() throws Throwable {
		projectPage = addStoryPage.clickSubmitButton();
	}

	@Then("^I should be able to click on the link for story \"([^\"]*)\" navigate to the story page$")
	public void i_should_be_able_to_click_on_the_link_for_story_navigate_to_the_story_page(final String arg1) throws Throwable {
		storyPage = projectPage.clickStoryLink(arg1);
		assertTrue(storyPage.isLoaded());
	}

	@Then("^which should have a status of \"([^\"]*)\"$")
	public void which_should_have_a_status_of(final String arg1) throws Throwable {
	    assertThat(storyPage.getStatus(), is(arg1));
	}

	@Then("^a title of \"([^\"]*)\"$")
	public void a_title_of(final String arg1) throws Throwable {
		assertThat(storyPage.getName(), is(arg1));
	}

	@Then("^story points of (\\d+)$")
	public void story_points_of(final int arg1) throws Throwable {
		assertThat(storyPage.getPoints(), is(arg1));
	}

}
