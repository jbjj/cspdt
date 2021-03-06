package com.fware.cspdt.cspm.editor.link;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.hyperlink.DefaultHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import com.fware.cspdt.cspm.editor.CspMEditor;
/**
 * Nesta classe serao configuradas as caracteristicas do browsing.
 * 
 *  Entre estas caracteristicas estao a delimitacao e sublinhamento da regiao que foi clicada.
 * 
 * @author Joabe Jesus
 * @author Victor Vilmarques
 * @author ALVARO, EVERALDA, FELIPE, JONATHAN, JUVENAL
 *
 */
public class CspMHyperlinkPresenter extends DefaultHyperlinkPresenter {
	
	Region range = null;
	CspMEditor editor;
	private static Cursor fCursor = null;
	

	public CspMHyperlinkPresenter(RGB color, CspMEditor editor) {
		super(color);
		this.editor = editor;
	}

	public void applyTextPresentation(TextPresentation textPresentation) {
		super.applyTextPresentation(textPresentation);
	}

	public void hideHyperlinks(){
		if (range != null )	{
			removeUnderlinedRange(range);
		}
		
		resetCursor();
		
		super.hideHyperlinks();
		
		range = null;
	}

	public void showHyperlinks(IHyperlink[] hyperlinks)	{
		if (hyperlinks.length == 1 && hyperlinks[0] != null)	{
			IRegion hyperlinkRegion = hyperlinks[0].getHyperlinkRegion();
			range = new Region(hyperlinkRegion.getOffset(), hyperlinkRegion.getLength());
			setUnderlinedRange(range);
		}
		
		activateCursor();
		
		super.showHyperlinks(hyperlinks);
	}
	
	void setUnderlinedRange(Region range) {
		if (range != null) {
			StyledText textWidget = editor.getViewer().getTextWidget();
			StyleRange styleRange = new StyleRange();
			styleRange.start = range.getOffset();
			styleRange.length = range.getLength();
			editor.setUnderLineRange(styleRange);
			try	{
				textWidget.redrawRange(range.getOffset(), range.getLength(), true);
			}
			catch (IllegalArgumentException e) {
				// Do nothing if the range being invalid causes an exception
			}
		}
	}
	
	void removeUnderlinedRange(Region range) {
		if (editor.getViewer() == null || range == null) {
			return;
		}
		StyledText textWidget = editor.getViewer().getTextWidget();
		editor.setUnderLineRange(null);
		try	{
			textWidget.redrawRange(range.getOffset(), range.getLength(), true);
		}
		catch (IllegalArgumentException e) {
			// Do nothing if the range being invalid causes an exception
		}
	}
	
	private void activateCursor() {
		if (editor == null) {
			return;
		}
		try	{
			StyledText text= editor.getViewer().getTextWidget();
			if (text == null || text.isDisposed())
				return;
			Display display= text.getDisplay();
			if (fCursor == null)
				fCursor= new Cursor(display, SWT.CURSOR_HAND);
			text.setCursor(fCursor);
		} catch (Throwable th) {
			//ignore
		}
	}

	private void resetCursor() {
		if (editor == null) {
			return;
		}
		try	{
			StyledText text= editor.getViewer().getTextWidget();
			if (text != null && !text.isDisposed()) {
				text.setCursor(null);
			}
			if (fCursor != null) {
				fCursor.dispose();
				fCursor= null;
			}
		} catch (Throwable th) {
			//ignore
		}
	}
	
	public void uninstall() {
		if (fCursor != null) {
			fCursor.dispose();
			fCursor= null;
		}
		
		super.uninstall();
	}

}
