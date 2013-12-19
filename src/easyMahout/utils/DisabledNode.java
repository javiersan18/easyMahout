package easyMahout.utils;

import javax.swing.tree.DefaultMutableTreeNode;

public class DisabledNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;

	protected boolean enabled;

	// private String name;

	private String pathFile;

	public DisabledNode() {
		this(null, true, true);
	}

	public DisabledNode(Object userObject) {
		this(userObject, true, true);
	}

	public DisabledNode(Object userObject, String path) {
		this(userObject, true, true);
		this.setPathFile(path);
	}

	public DisabledNode(Object userObject, boolean allowsChildren) {
		this(userObject, allowsChildren, true);
	}
	
	public DisabledNode(Object userObject, boolean allowsChildren, String path) {
		this(userObject, allowsChildren, true);
		this.setPathFile(path);
	}

	public DisabledNode(Object userObject, boolean allowsChildren,
			boolean enabled) {
		super(userObject, allowsChildren);
		this.enabled = enabled;
	}
	
	public DisabledNode(Object userObject, boolean allowsChildren,
			boolean enabled, String path) {
		super(userObject, allowsChildren);
		this.enabled = enabled;
		this.setPathFile(path);
	}

	public int getChildCount() {
		if (enabled) {
			return super.getChildCount();
		} else {
			return 0;
		}
	}

	public boolean isLeaf() {
		return (super.getChildCount() == 0);
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
}