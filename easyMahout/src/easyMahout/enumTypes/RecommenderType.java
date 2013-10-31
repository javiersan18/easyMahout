package easyMahout.enumTypes;

public enum RecommenderType {
	
	USER_BASED(1,"User-based"), ITEM_BASED(2,"Item-based");
	
	private int type;
	private String name;
	
	private RecommenderType(int type, String name) {
		this.setType(type);
		this.setName(name);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
}
