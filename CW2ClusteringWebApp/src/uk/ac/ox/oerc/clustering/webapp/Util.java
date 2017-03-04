package uk.ac.ox.oerc.clustering.webapp;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class Util {

	private static final Color minColour = new Color(255, 135, 135);
	private static final Color midColour = Color.WHITE;
	private static final Color maxColour = new Color(135, 135, 255);

	public static List<Color> createSpectrum(int numGradients) {
		// some even/odd dividers
		int evenOddStep;
		float evenOddOffset;
		if ((numGradients & 1) == 1) {
			// odd
			evenOddStep = 3;
			evenOddOffset = (float) 1.0;
		} else {
			// even
			evenOddStep = 2;
			evenOddOffset = (float) 0.5;
		}
		// we are operating in a 3-point scale with (close to) white as midpoint, no alpha channel.
		// general algorithm is: generate colours from minimum to last before midpoint, 
		// add midpoint if uneven gradients, then from next after midpoint to max colour.
		List<Color> colours = new ArrayList<Color>(numGradients);
		int numSteps = (numGradients - evenOddStep) / 2;
		// finish fast logic - only one gradient needed (which is white) - done! 
		if (numSteps == -1) {
			colours.add(midColour);
			return colours;
		} 
		// part 1 - add gradients from min to last before midpoint
		colours.add(minColour);
		int redOffset   = Math.round((midColour.getRed()   - minColour.getRed())   / (numSteps + evenOddOffset));
		int greenOffset = Math.round((midColour.getGreen() - minColour.getGreen()) / (numSteps + evenOddOffset));
		int blueOffset  = Math.round((midColour.getBlue()  - minColour.getBlue())  / (numSteps + evenOddOffset));
		for (int i = 1; i<=numSteps; i++) {
			colours.add(new Color(
					minColour.getRed()   + i * redOffset, 
					minColour.getGreen() + i * greenOffset,
					minColour.getBlue()  + i * blueOffset));
		}
		// part 2 - add midColour, if odd gradients
		if ((numGradients & 1) == 1) {
			colours.add(midColour);
		}
		// part 3 - add next to midpoint to max
		redOffset   = Math.round((maxColour.getRed()   - midColour.getRed())   / (numSteps + evenOddOffset));
		greenOffset = Math.round((maxColour.getGreen() - midColour.getGreen()) / (numSteps + evenOddOffset));
		blueOffset  = Math.round((maxColour.getBlue()  - midColour.getBlue())  / (numSteps + evenOddOffset));
		for (int i = 1; i<=numSteps; i++) {
			colours.add(new Color(
					midColour.getRed()   + i * redOffset, 
					midColour.getGreen() + i * greenOffset,
					midColour.getBlue()  + i * blueOffset));
		}
		colours.add(maxColour);
	
		return colours;
	}
	
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
}
