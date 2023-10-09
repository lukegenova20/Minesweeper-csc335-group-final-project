package view;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/** 
 * @author Taoseef Aziz
 * @author Luke Genova
 * @author Matteus McKinley Wilson
 * @author Amimul Ehsan Zoha
 * 
 * FILE: ExplosionAnimation.java
 * 
 * ASSIGNMENT: Project 6
 * 
 * COURSE: CSc 335; Section 001; Spring 2022
 * 
 * PURPOSE: This class is used to create an animation object that once played 
 * by the view, it plays an animation of an explosion on of the tiles of 
 * the game. This animation will play only when the user clicks on a mine which
 * results in a game over. 
 * 
 * CONSTRUCTORS:
 * 	ExplosionAnimation(ImageView, Duration, int, int, int, int, int, int)
 * 
 * FIELDS:
 * 	private imageView : ImageView
 * 	private count : int
 * 	private columns : int
 * 	private offsetX : int
 * 	private offsetY : int
 * 	private width : int
 * 	private height : int
 * 	private lastIndex : int
 * 
 * METHODS:
 *  protected void interpolate(double k)
 *	
 * USAGE: 
 * 	Run using Minesweeper.java, never directly run apart from testing purposes.
 *
 */
public class ExplosionAnimation extends Transition {
	private final ImageView imageView;
    private final int count;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    private int lastIndex;

    /**
     * ExplosionAnimation Constructor.
     * 
     * @param imageView The image view object that views a specific spot of the sprite sheet.
     * @param duration The duration of the animation.
     * @param count The number of sprites in the sprite sheet.
     * @param columns The number of sprite columns the sprite sheet has.
     * @param offsetX An integer that represents that x position offset from the origin point.
     * @param offsetY An integer that represents that y position offset from the origin point.
     * @param width An integer that represents the width of the sprite sheet.
     * @param height An integer that represents the height of the sprite sheet.
     */
    public ExplosionAnimation(
            ImageView imageView, 
            Duration duration, 
            int count,   int columns,
            int offsetX, int offsetY,
            int width,   int height) {
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    /**
     * While a Transition is running, this method is called in every frame. 
     * The parameter defines the current position with the animation. At the start, 
     * the fraction will be 0.0 and at the end it will be 1.0. How the parameter increases, 
     * depends on the interpolator, e.g. if the interpolator is Interpolator.LINEAR, the
     * fraction will increase linear. This method must not be called by the user directly.
     * 
     * @param k The relative position.
     */
    @Override
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}
