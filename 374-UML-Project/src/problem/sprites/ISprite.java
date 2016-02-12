package problem.sprites;

import java.awt.Dimension;
import java.awt.Shape;

public interface ISprite {
	public void move(Dimension space);
	public Shape getShape();
}
