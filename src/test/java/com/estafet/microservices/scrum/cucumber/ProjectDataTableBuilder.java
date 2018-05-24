package com.estafet.microservices.scrum.cucumber;

import java.util.ArrayList;
import java.util.List;

import com.estafet.microservices.scrum.lib.data.project.Project;
import com.estafet.microservices.scrum.lib.data.project.ProjectBuilder;

import cucumber.api.DataTable;

public class ProjectDataTableBuilder {

	private DataTable dataTable;
	
	public ProjectDataTableBuilder setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
		return this;
	}

	public List<Project> build() {
		List<Project> projects = new ArrayList<Project>();
		List<List<String>> data = dataTable.raw();
		for (int i = 1; i < data.size(); i++) {
			List<String> row = data.get(i);
			Project project = new ProjectBuilder()
							.setTitle(row.get(0))
							.setNoSprints(Integer.parseInt(row.get(1)))
							.setSprintLengthDays(Integer.parseInt(row.get(2)))
							.build();
			projects.add(project);
		}
		return projects;
	}
	
}
