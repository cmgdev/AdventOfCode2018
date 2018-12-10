package day10;

import base.AbstractPuzzle;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Puzzle extends AbstractPuzzle {

   public static final boolean IS_TEST = false;

   public Puzzle() {
      super( IS_TEST );
   }

   public static void main( String[] args ) {
      Puzzle puzzle = new Puzzle();
      List<String> input = puzzle.readFile( 10 );
      List<Point> points = new ArrayList<>();

      for ( String line : input ) {
         int a = line.indexOf( "<" ) + 1;
         int b = line.indexOf( ",", a );

         int x = Integer.parseInt( line.substring( a, b ).trim() );

         a = b + 1;
         b = line.indexOf( ">", a );
         int y = Integer.parseInt( line.substring( a, b ).trim() );

         a = line.indexOf( "<", b ) + 1;
         b = line.indexOf( ",", a );
         int vx = Integer.parseInt( line.substring( a, b ).trim() );

         a = b + 1;
         b = line.indexOf( ">", a );
         int vy = Integer.parseInt( line.substring( a, b ).trim() );

         Point point = new Point( x, y, vx, vy );
         points.add( point );
      }

      Scanner in = new Scanner( System.in );
      boolean cont = true;
      int seconds = 0;

      while ( cont ) {
         int minX = points.stream().mapToInt( Point::getX ).min().getAsInt();
         int maxX = points.stream().mapToInt( Point::getX ).max().getAsInt();
         int minY = points.stream().mapToInt( Point::getY ).min().getAsInt();
         int maxY = points.stream().mapToInt( Point::getY ).max().getAsInt();
         boolean checkContinue = true;

         int xRange = Math.abs( minX ) + Math.abs( maxX ) + 1;
         int yRange = Math.abs( minY ) + Math.abs( maxY ) + 1;
         int xOffset = Math.abs( minX );
         int yOffset = Math.abs( minY );

         if ( xRange < 250 || yRange < 250 ) {
            String[][] chart = new String[yRange][xRange];
            for ( Point point : points ) {
               int x = point.getX() + xOffset;
               int y = point.getY() + yOffset;
               chart[y][x] = "#";
            }

            for ( int i = minY; i <= maxY; i++ ) {
               for ( int j = minX; j <= maxX; j++ ) {
                  String s = chart[i + yOffset][j + xOffset];
                  if ( s != null ) {
                     System.out.print( chart[i + yOffset][j + xOffset] );
                  }
                  else {
                     System.out.print( "." );
                  }
               }
               System.out.println();
            }
         }

         else {
            System.out.println( "xRange is " + xRange + " and yRange is " + yRange + ". Resetting mins and maxes." );
            checkContinue = false;
         }
         if( checkContinue) {
            System.out.print( "(" + seconds + ") Continue? y/n " );
            String r = in.next();
            if ( !r.equalsIgnoreCase( "y" ) ) {
               cont = false;
            }
         }
         if ( cont ) {
            for ( Point point : points ) {
               point.increment();
            }
            seconds++;
         }
      }

   }

}
