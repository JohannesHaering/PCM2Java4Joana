/**
 *
 * $Id$
 */
package edu.kit.kastel.scbs.pcm2java4joana.joanasimplifiedresult.validation;

import edu.kit.kastel.scbs.pcm2java4joana.joanasimplifiedresult.TraceState;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link edu.kit.kastel.scbs.pcm2java4joana.joanasimplifiedresult.Trace}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TraceValidator {
	boolean validate();

	boolean validateTracestate(EList<TraceState> value);
}