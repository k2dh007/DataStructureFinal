package RBtree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RBtree {
	 public static void main(String args[]) {
		 
		    if (args.length == 0) {                   
		      System.err.println("Input Filename...");
		      System.exit(1);                         
		    }
		    int insertNum=0;
		    int deleteNum=0;
		    int missNum=0;
		    try {
		    	RBtree1 st = new RBtree1();
		    	File f = new File(args[0]);
		      BufferedReader in = new BufferedReader(new FileReader(f));
		      String s;
		      while ((s = in.readLine()) != null) {
		    	  int s_int = Integer.parseInt(s.trim());
		    	  if(s_int>0){
		    		  st.insert(s_int);
		    		  insertNum++;
		    	  }
		    	  else if(s_int<0){
		    		  String s_str = Integer.toString(-s_int);
		    		  if(st.remove(-s_int)==null) missNum++;
		    		  else deleteNum++;
		    	  }
		    	  else break;
		      }
		      in.close();
		      
			  	File fSearch = new File(args[1]);
			    BufferedReader inSearch = new BufferedReader(new FileReader(fSearch));
			    File outFile = new File("D:", "output01.txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
			    String sSearch;
			    RedBlackNode foundNum = null;
	  			RedBlackNode preNum = null;
	  			int preNum_int = 0;
	  			RedBlackNode sucNum = null;
	  			int sucNum_int = 0;
			    while ((sSearch = inSearch.readLine()) != null) {
			  	  int sSearch_int = Integer.parseInt(sSearch.trim());
			  	  if(sSearch_int!=0){
			  		  foundNum = st.search(sSearch_int);
			  		  if(foundNum!=null){
			  			  preNum = null;
			  			  preNum = st.treePredecessor(foundNum);
			  			  if (!st.isNil(preNum)) bw.write(preNum.key+" ");
			  			  else bw.write("NIL ");
			  			  bw.write(foundNum.key + " ");
			  			  sucNum = null;
			  			  sucNum = st.treeSuccessor(foundNum);
			  			  if (!st.isNil(sucNum)) {
			  				  bw.write(sucNum.key+" ");
			  				  bw.newLine();
			  			  }
			  			  else {
			  				  bw.write("NIL ");
			  				  bw.newLine();
			  			  }
			  		  }
			  		  else{
			  			preNum = null;
			  			preNum = st.numSmaller(sSearch_int);
			  			if (!st.isNil(preNum)) bw.write(preNum.key+" ");
			  			else bw.write("NIL ");
			  			bw.write("NIL ");
			  			sucNum = null;
			  			sucNum = st.numGreater(sSearch_int);
			  			if (!st.isNil(sucNum)) {
			  				bw.write(sucNum.key+" ");
			  				bw.newLine();
			  			}
			  			else {
			  				bw.write("NIL ");
			  				bw.newLine();
			  			}
			  		  }
			  	  }
			  	  else break;
			    }
			    inSearch.close();
			    bw.close();
			    
		    } catch (IOException e) {
		        System.err.println(e); 
		        System.exit(1);
		    }
		    }
	 }