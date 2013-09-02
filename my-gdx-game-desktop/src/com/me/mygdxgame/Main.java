package com.me.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;




public class Main {
	
	static void permute( String str ){
	    int          length = str.length();
	    boolean[]    used = new boolean[ length ];
	    StringBuffer out = new StringBuffer();
	    char[]       in = str.toCharArray();

	    doPermute( in, out, used, length, 0 );
	}

	static void doPermute( char[] in, StringBuffer out,
	                boolean[] used, int length, int level ){
	    if( level == length ){
	        System.out.println( out.toString() );
	        return;
	    }

	
	    for( int i = 0; i < length; ++i ){
	        if( used[i] ) continue;

	        out.append( in[i] );
	        used[i] = true;
	        doPermute( in, out, used, length, level + 1 );
	        used[i] = false;
	        out.setLength( out.length() - 1 );
	    }
	}
	
	public static void main(String[] args) {
		
		permute( "123");
		/**
		 * To find permutation x of array A, where A has N elements:
0. if A has one element, return it
1. set p to ( x / (N-1)! ) mod N
2. the desired permutation will be A[p] followed by
   permutation ( x mod (N-1)! )
   of the elements remaining in A after position p is removed
   http://stackoverflow.com/questions/352203/generating-permutations-lazily/353248#353248**/
		 
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Drop";
	      cfg.useGL20 = true;
	      cfg.width = 800;
	      cfg.height = 480;
		new LwjglApplication(new MyGdxGame(), cfg);
	}
}
