package problem.sprites;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpriteFactory {
	// Constants used by all sprite objects
	public static final double DX = 5;
	public static final double DY = 5;
	public static final double WIDTH = 50;
	public static final double HEIGHT = 50;
	
	private static Random random = new Random();
	private static List<Class<? extends SpriteComponent>> sprites = new ArrayList<Class<? extends SpriteComponent>>();
	
	// Pre-populate sprites here
	static{
		sprites.add(TripleStack.class);
		sprites.add(TripleCircle.class);
		sprites.add(RectangleSprite.class);
		sprites.add(CircleSprite.class);
	}
	
	public static Point2D computeRandomLocation(Dimension space) {
		Random r = new Random();
		double x = r.nextInt(space.width);
		double y = r.nextInt(space.height);
		
		return new Point2D.Double(x, y);
	}
	
	
	public static SpriteComponent createRandomSprite(Dimension space) throws Exception {
		int index = random.nextInt(sprites.size());
		Class<? extends SpriteComponent> clazz = sprites.get(index);
		Constructor<? extends SpriteComponent> ctor = clazz.getDeclaredConstructor(double.class, double.class, double.class, double.class);
		
		Point2D aPoint = computeRandomLocation(space);
		return ctor.newInstance(aPoint.getX(), aPoint.getY(), WIDTH, HEIGHT);
	}
}
