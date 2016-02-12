package problem.sprites;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class SpriteComponent extends AbstractSprite{
	private ArrayList<SpriteComponent> c;
	public SpriteComponent(double x, double y, double width, double height) {
		super(x, y, width, height);
		c = new ArrayList<SpriteComponent>();
		c.add(this);
	}
	public void add(SpriteComponent spriteComponent){
		//throw new UnsupportedOperationException();
	}
	public void remove(SpriteComponent spriteComponent){
		//throw new UnsupportedOperationException();
	}
	public SpriteComponent getChild(int i){
		return this;
		//throw new UnsupportedOperationException();
	}
	public Iterator<SpriteComponent> createIterator(){
		return c.iterator();
		//throw new UnsupportedOperationException();
	}
	public void move(double dX, double dY){
		Rectangle2D r = shape.getBounds2D();
		shape = new Rectangle2D.Double(r.getX()+dX,r.getY()+dY,r.getWidth(),r.getHeight());
	}
}
