package com.fware.cspdt.cspm.editor.hover;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.swt.graphics.Point;
/**
 * Esta classe define a estrategia de acao para quando o mouse passa sobre o texto no codigo CSPM.
 * 
 * @author Joabe Jesus
 * @author Victor Vilmarques
 * @author ALVARO, EVERALDA, FELIPE, JONATHAN, JUVENAL
 *
 */
public class CspMTextHover implements ITextHover {

	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		Point selection = textViewer.getSelectedRange();
		if (selection.x <= offset && offset < selection.x + selection.y) {
			return new Region(selection.x, selection.y);
		}
		return new Region(offset, 0);
	}

	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (hoverRegion != null) {
			if (hoverRegion.getLength() > -1) {
				try {
					return textViewer.getDocument().get(hoverRegion.getOffset(), hoverRegion.getLength());
				} catch (BadLocationException x) {
				}
			} else {
				return "It works! :)";
			}
		}
		return "It works! :P";// CspEditorMessages.getString("CspTextHover.emptySelection");
	}
}