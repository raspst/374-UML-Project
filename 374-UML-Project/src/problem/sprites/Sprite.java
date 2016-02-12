package problem.sprites;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;

public class Sprite extends SpriteComponent{
	public Sprite(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	private ArrayList<SpriteComponent> spriteComponents = new ArrayList<SpriteComponent>();
	public void add(SpriteComponent spriteComponent){
		spriteComponents.add(spriteComponent);
	}
	public void remove(SpriteComponent spriteComponent){
		spriteComponents.remove(spriteComponent);
	}
	public SpriteComponent getChild(int i){
		return spriteComponents.get(i);
	}
	@Override
	public Iterator<SpriteComponent> createIterator() {
		return spriteComponents.iterator();
	}
	@Override
	public void move(Dimension space) {
		
	}

}
