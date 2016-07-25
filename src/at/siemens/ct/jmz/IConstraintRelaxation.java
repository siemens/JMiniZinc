package at.siemens.ct.jmz;

import at.siemens.ct.jmz.elements.constraints.Constraint;

/**
 * An implementation of this interface can be used to replace certain {@link Constraint}s by relaxed versions in a
 * {@link ModelBuilder}.
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public interface IConstraintRelaxation {

  /**
   * Determines if the given constraint shall be replaced by a relaxed version.
   * 
   * @param constraint
   * @return {@code true} iff the given constraint shall be replaced by a relaxed version.
   */
  boolean isRelaxed(Constraint constraint);

  /**
   * Retrieves the relaxed version of a given constraint.
   * 
   * @param constraint
   * @return the relaxed version of {@code constraint}, if available, or {@code constraint} itself if it is not to be
   *         relaxed.
   */
  Constraint getRelaxed(Constraint constraint);

}
