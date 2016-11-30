package com.test.cmoa.orm;

import java.util.ArrayList;
import java.util.List;

/*
 * 1. 创建一个 Handler 方法. 使 menu.jsp 中的 url 的 data-options 的 url 修改为新建的 Handler 方法对应的 URL
 * <ul id="tt" class="easyui-tree" data-options="url:'${ctp}/test/tree_data1.json',method:'get',animate:true"></ul>
 * 2. 在 Handler 方法中根据登录用的权限-父权限的对应关系来构建 Navigation 的集合
 * 3. Handler 方法返回改集合. 
 * 4. 在 Handler 方法的方法签名前添加 @ResponseBody 注解. 
 * 
 */
public class Navigation {

	@Override
	public String toString() {
		return "Navigation [id=" + id + ", text=" + text + ", state=" + state + ", url=" + url + ", children="
				+ children + "]";
	}

	private Long id;
	private String text;
	private String state;
	private String url;

	public Navigation(Long id, String text) {
		this.id = id;
		this.text = text;
	}

	private List<Navigation> children = new ArrayList<Navigation>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Navigation> getChildren() {
		return children;
	}

	public void setChildren(List<Navigation> children) {
		this.children = children;
	}
}
