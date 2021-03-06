package dkeep.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dkeep.logic.Map;

public class GameView extends JPanel
{
	private TreeMap<Character, BufferedImage> dungeon;
	private TreeMap<Character, BufferedImage> keep;
	private BufferedImage[][] graphics;
	private char[][] gameMap;
	private int width;
	private int height;
	private int level;
	private int imageSize;

	public GameView(Map gameMap, int level) 
	{
		width = 10;
		height = 10;
		this.level = level;
		this.gameMap = new char[height][width];
		dungeon = new TreeMap<Character, BufferedImage>();
		keep = new TreeMap<Character, BufferedImage>();
		imageSize = 32;

		try
		{
			loadImages();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}

		initGraphics(width,height);

		if(gameMap != null)
			updateMap(gameMap.layout());
	}

	public void loadImages() throws IOException
	{		
		dungeon.put('H', ImageIO.read(new File("res/hero.png")));
		dungeon.put('G', ImageIO.read(new File("res/guard.png")));
		dungeon.put('g', ImageIO.read(new File("res/sleep.png")));
		dungeon.put('I', ImageIO.read(new File("res/door_closed.png")));
		dungeon.put('S', ImageIO.read(new File("res/door_open.png")));
		dungeon.put('k', ImageIO.read(new File("res/lever.png")));
		dungeon.put('X', ImageIO.read(new File("res/parede.png")));
		dungeon.put(' ', ImageIO.read(new File("res/espaco.png")));

		keep.put('O', ImageIO.read(new File("res/ogre.png")));
		keep.put('*', ImageIO.read(new File("res/club.png")));
		keep.put('K', dungeon.get('H'));
		keep.put('A', dungeon.get('H'));
		keep.put('$', ImageIO.read(new File("res/dollar.png")));
		keep.put('k', ImageIO.read(new File("res/key.png")));
		keep.put('8', keep.get('O'));
		keep.put(' ', ImageIO.read(new File("res/espaco.png")));
		keep.put('X', dungeon.get('X'));
		keep.put('S', dungeon.get('S'));
		keep.put('I', dungeon.get('I'));
	}

	public void initGraphics(int width, int height)
	{
		graphics = new BufferedImage[height][width];

		for(int i = 0; i < height;i++)
			for(int j = 0;j<width;j++)
			{
				graphics[i][j] = dungeon.get(' ');
				gameMap[i][j] = ' ';
			}
	}

	public void changeLevel()
	{
		level++;
	}

	public BufferedImage[][] getMap()
	{
		return graphics;
	}

	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		int x = 0;
		int y = 0;

		for(int i = 0; i < graphics.length; i++)
		{
			for(int j = 0; j < graphics[i].length; j++)
			{
				g.drawImage(graphics[i][j], x, y, this);
				x += imageSize;
			}

			x = 0;
			y += imageSize;
		}
	}

	public void updatePos(int i, int j, char entity)
	{
		graphics[i][j] = level == 1 ? dungeon.get(entity) : keep.get(entity);
		gameMap[i][j] = entity;
	}

	public void updateMap(char[][] map)
	{		
		TreeMap<Character, BufferedImage> images = level == 1 ? dungeon : keep;

		for(int i = 0; i < map.length; i++)
			for(int j = 0; j < map[i].length; j++)
				graphics[i][j] = images.get(map[i][j]);
	}	

	public char[][] getGameMap()
	{
		return gameMap;
	}
}
