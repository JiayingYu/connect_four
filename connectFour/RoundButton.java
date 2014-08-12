package connectFour;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class RoundButton extends JButton {
  private static final long serialVersionUID = 4374851841327447783L;
  final int row;
  final int col;

  RoundButton(int row, int col) {
    this.row = row;
    this.col = col;
    Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width, size.height);
    setPreferredSize(size);
    setContentAreaFilled(false);
    this.setBackground(java.awt.Color.WHITE);
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    if (getModel().isArmed()) {
      g.setColor(java.awt.Color.lightGray);
    } else {
      g.setColor(getBackground());
    }
    g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

  }

  protected void paintBorder(Graphics g) {
    g.setColor(getForeground());
    g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
  }

  Shape shape;

  public boolean contains(int x, int y) {
    if (shape == null || !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }
}


class ButtonListener1 implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		((RoundButton)e.getSource()).setBackground(Color.red);
	}
	
}

