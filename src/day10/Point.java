package day10;

public class Point {

   private int x;
   private int y;
   private int vx;
   private int vy;

   public Point( int x, int y, int vx, int vy ) {
      this.x = x;
      this.y = y;
      this.vx = vx;
      this.vy = vy;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public int getVx() {
      return vx;
   }

   public int getVy() {
      return vy;
   }

   public void increment() {
      x += vx;
      y += vy;
   }
}
