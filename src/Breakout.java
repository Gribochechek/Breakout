
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/**
	 * Width of a brick. По моему тут должно быть
	 * NBRICKS_PER_ROW+1
	 */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	private static final int DELAY = 10;
	private GOval ball;
	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();

	private GRect paddle;

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		this.setSize(WIDTH, HEIGHT);
		addMouseListeners();
		for (int i = 0; i < NTURNS; i++) {
			setup();
			playGame();
			removeAll();
		}
	}

	private void playGame() {
		while (ball != null) {
			moveBall();
			checkForCollisions();
			pause(DELAY);
			if (ball.getY() >= getHeight()) {
				ball = null;
			}
		}
	}

	private void setup() {
		createBricks();
		createPaddle();
		drawBall();
		waitForClick();
		ballSpeed();
	}

	/**
	 * метод реализации столкновений с
	 * объектами. Пока нет реализации ракетки
	 * (paddle) - будет выдавать ошибку
	 */
	private void checkForCollisions() {
		GObject collObj = getCollidingObject();
		if (collObj == paddle) {
			vy = -vy;
			if (vx > 0 && (ball.getX() + BALL_RADIUS) < (paddle.getX() + PADDLE_WIDTH / 2)) {
				vx = -vx;
			}
			if (vx < 0 && (ball.getX() + BALL_RADIUS) > (paddle.getX() + PADDLE_WIDTH / 2)) {
				vx = -vx;
			}

		} else if (collObj != null) {
			remove(collObj);
			vy = -vy;
		}

	}

	/**
	 * метод возвращает объект, с которым
	 * столкнулся мяч
	 */
	private GObject getCollidingObject() {
		GObject obj = getElementAt(ball.getX(), ball.getY());

		if (obj == null) {
			obj = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		}
		if (obj == null) {
			obj = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		}
		if (obj == null) {
			obj = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		}

		return obj;
	}

	private void drawBall() {
		ball = new GOval(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS, BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}

	private void ballSpeed() {
		vy = 3.0;
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
	}

	private void moveBall() {
		ball.move(vx, vy);
		if (ball.getX() + BALL_RADIUS * 2 >= getWidth() || ball.getX() <= 0) {
			vx = -vx;
			// || ball.getY() + BALL_RADIUS * 2 >= getHeight()
		} else if (ball.getY() <= 0) {
			vy = -vy;
		}
	}

	private void createBricks() {

		for (int i = 0; i < NBRICK_ROWS; i++) {
			for (int j = 0; j < NBRICKS_PER_ROW; j++) {
				int x = j * (BRICK_WIDTH + BRICK_SEP) + BRICK_SEP / 2;
				int y = i * (BRICK_HEIGHT + BRICK_SEP) + BRICK_Y_OFFSET;
				GRect rect = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				rect.setFilled(true);
				if (i == 0 | i == 1) {
					rect.setFillColor(Color.RED);
					rect.setColor(Color.RED);
				}
				if (i == 2 | i == 3) {
					rect.setFillColor(Color.ORANGE);
					rect.setColor(Color.ORANGE);
				}
				if (i == 4 | i == 5) {
					rect.setFillColor(Color.YELLOW);
					rect.setColor(Color.YELLOW);
				}
				if (i == 6 | i == 7) {
					rect.setFillColor(Color.GREEN);
					rect.setColor(Color.GREEN);
				}
				if (i == 8 | i == 9) {
					rect.setFillColor(Color.CYAN);
					rect.setColor(Color.CYAN);
				}
				add(rect);
			}
		}
	}

	private void createPaddle() {

		paddle = new GRect((WIDTH - PADDLE_WIDTH) / 2, HEIGHT - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setFillColor(Color.BLACK);
		add(paddle);
	}

	public void mouseMoved(MouseEvent e) {

		if (e.getX() >= PADDLE_WIDTH / 2 & e.getX() < WIDTH - PADDLE_WIDTH / 2) {
			paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, HEIGHT - PADDLE_Y_OFFSET);
		}
	}

}
