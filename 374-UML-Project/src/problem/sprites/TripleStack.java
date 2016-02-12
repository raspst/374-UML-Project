package problem.sprites;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

public class TripleStack extends Sprite{
	public TripleStack(double x, double y, double width, double height) {
		super(x, y, width, height);
		this.shape= new Rectangle2D.Double(x, y-(height*3/4.0), width,height+ (height*3/4.0));
		add(new RectangleSprite(x, y, width, height));
		for(int i=0; i<2;++i){
		y-=height;
		height/=2.0;
		width/=2.0;
		y+=height;
		x+=width/2.0;
		add(new RectangleSprite(x, y, width, height));
		}
	}
	
	@Override
	public void move(Dimension space) {
		Rectangle2D bounds = this.computeNewBoundsAfterMoving(space);
		Rectangle2D b = shape.getBounds2D();
		double xOff = bounds.getX() - b.getX();
		double yOff = bounds.getY() - b.getY();
		shape = new Rectangle2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		move(xOff,yOff);
	}

	@Override
	public void move(double dX, double dY) {
		Iterator<SpriteComponent> it = createIterator();
		while (it.hasNext()) {
			SpriteComponent c = it.next();
			Rectangle2D b = c.shape.getBounds2D();
			c.shape = new Rectangle2D.Double(b.getX() + dX, b.getY() + dY, b.getWidth(), b.getHeight());
		}
	}
}
