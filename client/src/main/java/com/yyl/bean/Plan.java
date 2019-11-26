package com.yyl.bean;


class Plan{
	private String name;
	private long space;
	private int collaborators;
	private int private_repos;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSpace() {
		return space;
	}
	public void setSpace(long space) {
		this.space = space;
	}
	public int getCollaborators() {
		return collaborators;
	}
	public void setCollaborators(int collaborators) {
		this.collaborators = collaborators;
	}
	public int getPrivate_repos() {
		return private_repos;
	}
	public void setPrivate_repos(int private_repos) {
		this.private_repos = private_repos;
	}
}
