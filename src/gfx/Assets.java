package gfx;

import java.awt.image.BufferedImage;

public class Assets {
	public static BufferedImage
	table,
	people,
	food,
	fork,
	namTrietGia,
	namtrietgia_nofork,
	philovang,
	philoxanh,
	philodo;
	
	public static void init(){
		table = ImageLoader.loadImage("/banan.png");
		people = ImageLoader.loadImage("/nguoi.png");
		food = ImageLoader.loadImage("/food.png");
		fork = ImageLoader.loadImage("/fork.png");
		namTrietGia = ImageLoader.loadImage("/namtrietgia.png");
		namtrietgia_nofork = ImageLoader.loadImage("/namtrietgia_nofork.png");
		philovang = ImageLoader.loadImage("/vang.png");
		philodo = ImageLoader.loadImage("/xanh.png");
		philoxanh = ImageLoader.loadImage("/do.png");
	}
}
