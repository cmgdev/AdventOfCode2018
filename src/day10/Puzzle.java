package day10;

import base.AbstractPuzzle;

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

      while ( cont ) {
         int minX = 0;
         int maxX = 0;
         int minY = 0;
         int maxY = 0;
         boolean checkContinue = true;
         for ( Point point : points ) {
            int x = point.getX();
            int y = point.getY();
            if ( x < minX ) {
               minX = x;
            }
            if ( x > maxX ) {
               maxX = x;
            }
            if ( y < minY ) {
               minY = y;
            }
            if ( y > maxY ) {
               maxY = y;
            }
         }

         int xRange = Math.abs( minX ) + Math.abs( maxX ) + 1;
         int yRange = Math.abs( minY ) + Math.abs( maxY ) + 1;
         int xOffset = Math.abs( minX );
         int yOffset = Math.abs( minY );

         if ( xRange < 250 && yRange < 250 ) {
            String[][] chart = new String[yRange][xRange];
            for ( Point point : points ) {
               int x = point.getX() + xOffset;
               int y = point.getY() + yOffset;
               chart[y][x] = "#";
            }

            for ( int i = 0; i < yRange; i++ ) {
               for ( int j = 0; j < xRange; j++ ) {
                  String s = chart[i][j];
                  if ( s != null ) {
                     System.out.print( chart[i][j] );
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
            System.out.print( "Continue? y/n " );
            String r = in.next();
            if ( !r.equalsIgnoreCase( "y" ) ) {
               cont = false;
            }
         }
         if ( cont ) {
            for ( Point point : points ) {
               point.increment();
            }
         }
      }

   }

}
