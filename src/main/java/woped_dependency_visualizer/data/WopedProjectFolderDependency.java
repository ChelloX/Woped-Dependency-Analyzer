package woped_dependency_visualizer.data;

public class WopedProjectFolderDependency {
	private WopedProjectFolder dependency;
	private int importCount;
	
	public WopedProjectFolderDependency(WopedProjectFolder dependency) {
		this.dependency = dependency;
	}
	
	public WopedProjectFolder getDependency() {
		return dependency;
	}
	public void setDependency(WopedProjectFolder dependency) {
		this.dependency = dependency;
	}
	public int getCount() {
		return importCount;
	}
	public void setCount(int count) {
		this.importCount = count;
	}
	
	public String toString() {
		return dependency.getName() + " Count: " + getCount();
	}
}
