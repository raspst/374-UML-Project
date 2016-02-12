package problem.sprites;

import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

public class TripleCircle extends Sprite {

	public TripleCircle(double x, double y, double width, double height) {
		super(x, y, width * 3, height);
		shape = new Rectangle2D.Double(x, y, width * 3, height);
		for (int i = 0; i < 3; ++i) {
			add(new CircleSprite(x, y, width, height));
			x += width;
		}
	}

	@Override
	public void move(Dimension space) {
		Rectangle2D bounds = this.computeNewBoundsAfterMoving(space);
		Rectangle2D b = shape.getBounds2D();
		double xOff = bounds.getX() - b.getX();
		double yOff = bounds.getY() - b.getY();
		shape = new Ellipse2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		move(xOff,yOff);
	}

	@Override
	public void move(double dX, double dY) {
		Iterator<SpriteComponent> it = createIterator();
		while (it.hasNext()) {
			SpriteComponent c = it.next();
			Rectangle2D b = c.shape.getBounds2D();
			c.shape = new Ellipse2D.Double(b.getX() + dX, b.getY() + dY, b.getWidth(), b.getHeight());

		}
	}
}
