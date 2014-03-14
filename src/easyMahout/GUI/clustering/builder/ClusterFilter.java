package easyMahout.GUI.clustering.builder;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

final class ClusterFilter implements PathFilter {

  @Override
  public boolean accept(Path path) {
    String pathString = path.toString();
    return pathString.contains("/clusters-");
  }

}