/**
 *
 * $Id$
 */
package edu.kit.kastel.scbs.pcm2java4joana.joana.validation;

import edu.kit.kastel.scbs.pcm2java4joana.joana.Lattice;
import edu.kit.kastel.scbs.pcm2java4joana.joana.SecurityLevel;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link edu.kit.kastel.scbs.pcm2java4joana.joana.EntryPoint}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface EntryPointValidator {
	boolean validate();

	boolean validateSecuritylevels(EList<SecurityLevel> value);
	boolean validateLattice(Lattice value);
}
