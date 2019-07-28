package modélisation;
import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.view.mxInteractiveCanvas;
import com.mxgraph.view.mxCellState;
	public class SwingCanvas extends mxInteractiveCanvas
	{
		protected CellRendererPane rendererPane = new CellRendererPane();

		protected JLabel vertexRenderer = new JLabel();

		protected mxGraphComponent graphComponent;

		public SwingCanvas(mxGraphComponent graphComponent)
		{
			this.graphComponent = graphComponent;

			vertexRenderer.setBorder(BorderFactory
					.createBevelBorder(BevelBorder.RAISED));
			vertexRenderer.setHorizontalAlignment(JLabel.CENTER);
			vertexRenderer.setBackground(graphComponent.getBackground().green
					);
			vertexRenderer.setOpaque(true);
		}

		public void drawVertex(mxCellState state, String label)
		{
			vertexRenderer.setText(label);
			vertexRenderer.setIcon(new ImageIcon("personal32.png"));
			// TODO: Configure other properties...

			rendererPane.paintComponent(g, vertexRenderer, graphComponent,
					(int) state.getX() + translate.x, (int) state.getY()
							+ translate.y, (int) state.getWidth(), (int) state
							.getHeight(), true);
		}

	}