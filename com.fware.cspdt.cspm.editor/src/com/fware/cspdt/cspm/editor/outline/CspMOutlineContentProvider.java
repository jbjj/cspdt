package com.fware.cspdt.cspm.editor.outline;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.fware.cspdt.cspm.core.model.CspMModel;
import com.fware.cspdt.cspm.editor.CspMEditor;

import lmf.formula.csp.node.Start;
/**
 * Esta classe e reponsavel por montar a estrutura da arvore que sera exibido na tela.
 * 
 * @author Joabe Jesus
 * @author Victor Vilmarques
 * @author ALVARO, EVERALDA, FELIPE, JONATHAN, JUVENAL
 *
 */
public class CspMOutlineContentProvider extends TreeNodeContentProvider {
	
	protected final static String SEGMENTS = "__csp_segments";

	private CspMEditor editor; 

	protected IPositionUpdater positionUpdater;
	
	protected TreeNode rootNode;

	private IDocument document;

	public CspMOutlineContentProvider(CspMEditor editor) {
		this.editor = editor;
		positionUpdater = new DefaultPositionUpdater(SEGMENTS);
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (oldInput != null) {
			document = editor.getDocumentProvider().getDocument(oldInput);
			if (document != null) {
				try {
					document.removePositionCategory(SEGMENTS);
				} catch (BadPositionCategoryException x) {
				}
				document.removePositionUpdater(positionUpdater);
			}
		}
		
		if (newInput != null) {
			document = editor.getDocumentProvider().getDocument(newInput);
			if (document != null) {
				document.addPositionCategory(SEGMENTS);
				document.addPositionUpdater(positionUpdater);
				
				update(document, (TreeViewer) viewer);
			}
		}
	}

	/**
	 * Atualiza os conteudos presentes na arvore do codigo.
	 */
	private void update(IDocument document, TreeViewer treeViewer) {
		//String text = document.get();
		rootNode = null;

//		IEditorInput editorIn = editor.getEditorInput();		
//		IFile file = ((IFileEditorInput) editorIn).getFile();
		
		IFile file = (IFile) editor.getEditorInput().getAdapter(IFile.class);

		CspMModel model = editor.parse();
		if (model != null && model.ast != null) {
			Start start = model.ast;
			
			CspMAstTreeNodeGenerator outlineGen = new CspMAstTreeNodeGenerator(document, false);
			start.apply(outlineGen);
			
			rootNode = outlineGen.getRootNode();
		} else {
			rootNode = new TreeNode("Problema na cria��o do outline!");
		}
	}

	/*
	 * @see IContentProvider#dispose
	 */
	public void dispose() {
		if (rootNode != null) {
			rootNode.setChildren(null);
			rootNode = null;
		}
	}

	/*
	 * @see IContentProvider#isDeleted(Object)
	 */
	public boolean isDeleted(Object element) {
		return false;
	}

	/*
	 * @see IStructuredContentProvider#getElements(Object)
	 */
	public Object[] getElements(Object element) {
		if (element instanceof TreeNode)
			return ((TreeNode) element).getChildren();
		return new TreeNode[] { rootNode };
	}

	/*
	 * @see ITreeContentProvider#hasChildren(Object)
	 */
	public boolean hasChildren(Object element) {
		if (element instanceof TreeNode)
			return ((TreeNode) element).hasChildren();
		return false;
	}

	/*
	 * @see ITreeContentProvider#getParent(Object)
	 */
	public Object getParent(Object element) {
		if (element instanceof TreeNode)
			return ((TreeNode) element).getParent();
		return null;
	}

	/*
	 * @see ITreeContentProvider#getChildren(Object)
	 */
	public Object[] getChildren(Object element) {
		if (element instanceof TreeNode)
			return ((TreeNode) element).getChildren();
		return new Object[0];
	}
}