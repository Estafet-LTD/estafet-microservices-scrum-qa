package com.estafet.microservices.scrum.cucumber;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.estafet.microservices.scrum.lib.data.story.Story;
import com.estafet.microservices.scrum.lib.data.story.StoryBuilder;
import com.estafet.microservices.scrum.lib.data.task.TaskBuilder;

import cucumber.api.DataTable;

public class StoryDataTableBuilder {

	private DataTable dataTable;
	
	private Integer projectId;
	
	public StoryDataTableBuilder setProjectId(Integer projectId) {
		this.projectId = projectId;
		return this;
	}

	public StoryDataTableBuilder setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
		return this;
	}

	public List<Story> build() {
		List<Story> stories = new ArrayList<Story>();
		List<List<String>> data = dataTable.raw();
		Pattern p = Pattern.compile("(Task\\#\\d+)(\\s+)(\\[)(\\d+)(\\s+hours\\])");
		for (int i = 1; i < data.size(); i++) {
			String storyTitle = data.get(i).get(0);
			Integer storypoints = Integer.parseInt(data.get(i).get(1));
			Story story = new StoryBuilder()
							.setProjectId(projectId)
							.setTitle(storyTitle)
							.setStorypoints(storypoints)
							.build();
			for (String task : data.get(i).get(2).split(",")) {
				Matcher m = p.matcher(task.trim());
				if (m.find()) {
					new TaskBuilder()
						.setInitialHours(Integer.parseInt(m.group(4)))
						.setTitle(m.group(1))
						.setStoryId(story.getId())
						.build();		
				}
			}
			stories.add(story);
		}
		return stories;
	}
	
}
