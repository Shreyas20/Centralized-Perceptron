package perceptron;

import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Perceptron {

	public Perceptron() {
	}

	public static void main(String[] args) throws FileNotFoundException {
		double startTime=System.currentTimeMillis();

		List<List<Double>> x=readFile("/home/shreyas/Downloads/peersim-1.0.5/Eggeye/eggEye.txt");
		int nRow=x.size();
		int nCol=x.get(0).size()-1;
		float RMSE;
		float SE;
		int nIter=3;
		float lr=(float) 0.1;
		float eps=(float) 0.0001;
		double[] w=new double[nCol+1];
//	    System.out.println("Weights before training perceptron");

	    for(int i=0;i<w.length;i++)
	    {
	        w[i] = (Math.random() * ((0.01 - (-0.01)) )) -0.01;
	       System.out.println("W"+i+": "+w[i]);
	    }
	    int iter=0;
    	double cnorm=0,onorm = 0,o2norm=0,c2norm=0,diffnorm=0;
	    do {
	    	RMSE=0;
	    	SE=0;
	    	System.out.println("\nNo of cycle:"+iter);
	    	for(int o=0;o<nCol+1;o++) {
	    		onorm=w[o]*w[o];
	    	}
	    	o2norm=Math.sqrt(onorm);
	    	for(int i=0;i<nRow;i++) {
	    		
	    		float error=calculateWt(w,x,i,nCol);
	    	
	    		w[0]=w[0]+2*lr*error;
	    				
	    		for(int j=1;j<=nCol;j++) {
	    			w[j]=w[j]+2*error*x.get(i).get(j-1)*lr;
	    	    	//System.out.println("W"+j+": "+w[j]);
	    		}

//	    	    for(int m=0;m<w.length;m++)
//	    	    {
//	    	        w[m] = (Math.random()*((1-0)));
//	    	        System.out.println("W"+m+": "+w[m]);
//	    	    }
	    	    
	    		SE=SE+error*error;

	    	} 
	    	RMSE=(float) (Math.sqrt(SE)/(nRow));
	    	iter++;
	    	for(int c=0;c<nCol+1;c++) {
	    		cnorm=w[c]*w[c];
	    	}
	    	c2norm=Math.sqrt(cnorm);
	    	
	    	diffnorm=Math.abs(c2norm-o2norm);
		   System.out.println(diffnorm);

	    }while(iter<nIter && diffnorm>eps);
	    System.out.println("\nNumber of iterations required to train perceptron: "+iter);
	    //System.out.println("\nRoot Mean square error: "+);

	    System.out.println("\nRoot Mean square error: "+RMSE);
//	    System.out.println("\nWeights after training perceptron");
//	    for(int l=0;l<w.length;l++) {
//	    	System.out.println("W"+l+": "+w[l]);
//	    	
//	    }
	    double timeDist = System.currentTimeMillis() - startTime;
	    //writeLoss(timeFile,timeDist);
	   System.out.println("Time for centralized algorithm "+timeDist);
	    
	    String filename =  "/home/shreyas/Downloads/peersim-1.0.5/Eggeye/"+"CentwtVec.txt";
		for(int f=0;f<w.length;f++)
		{
			writeWts(filename,w[f]);
		}
		

		// TODO Auto-generated method stub
	}
	public static List<List<Double>> readFile(String fileName) throws FileNotFoundException{
		File file= new File(fileName);

        // this gives you a 2-dimensional array of strings
        List<List<String>> lines = new ArrayList<>();
        List<List<Double>> d = new ArrayList<List<Double>>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);
            String header=inputStream.next();
            while(inputStream.hasNext()){
                String line= inputStream.next();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // the following code lets you iterate through the 2-dimensional array
        for(List<String> line: lines) {
        	List<Double> w= new ArrayList<>();
        	
            for (String value: line) {
            	Double x=Double.parseDouble(value);
            	w.add(x);
                                
            }
            d.add(w);
        }		
		return d;
	}
	public static int calculateWt(double[] w, List<List<Double>> x,int row,int nCol) {

//	    for(int i=0;i<w.length;i++)
//	    {
//	        System.out.println("W"+i+": "+w[i]);
//	    }
		double sum=w[0];
		int j;
		for(j=1;j<=nCol;j++) {
			sum=sum+w[j]*x.get(row).get(j-1);			
		}
		//System.out.println(sum);
		int output;
		if(sum>0) {
			output=1;
			}
		else {
			output=-1;
		}
		//System.out.println(x.get(row).get(nCol));
		//System.out.println(output);
		int error=(int) ((output-x.get(row).get(nCol)));
        //System.out.println(error);
        //System.out.println("\n");
		return error;	
	}
	public static void writeWts(String fName, double db)
	{
		BufferedWriter out = null;
		try
		{
			FileWriter fstream = new FileWriter(fName, true);
			out = new BufferedWriter(fstream);
			out.write(String.valueOf(db)); 
		    out.write("\n");
	    	}
		catch (IOException ioe) 
		{ioe.printStackTrace();}
		finally
		{
		if (out != null) 
	    {try {out.close();} catch (IOException e) {e.printStackTrace();}
	    }
		}
	}
}
