package jp.co.myself.myselfcommon.db.entity;

import jp.co.myself.myselfcommon.db.entity.BaseEntity;

public class SampleEntity extends BaseEntity {
	
	public static final String TABLE_NAME = "sample";
	
	private int id;
	private String name;
	public static final String ID = "id";
	public static final String NAME = "name";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
