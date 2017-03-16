

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class RedBlackTree {
	
	public static RedBlackTree[] rbt=new RedBlackTree[1000];	 
	
		public int count=0;

	    private final int RED = 0;
	    private final int BLACK = 1;

	    private class Node {

	        int key = -1, color = BLACK;
	        int index;
	        Node left = nil, right = nil, parent = nil;
	        
	        Node(int key) {
	            this.key = key;
	        } 
	    }

	    private final Node nil = new Node(-1); 
	    private Node root = nil;
	    static Node temp;

	    public void printTree(Node node) {
	    	count++;
	        if (node == nil) {
	            return;
	        }
	        printTree(node.left);
	        System.out.print(((node.color==RED)?"Color: Red ":"Color: Black ")+"Key: "+node.key+" Index: "+node.index+"\n");
	        //" Parent: "+node.parent.key+
	        printTree(node.right);
	    }
	    
	    public Node find_Tail_node(Node node) {
	    	count++;
	    	if (node == nil) {
	            return nil;
	        }
	        find_Tail_node(node.left);
	        temp=node;
	        find_Tail_node(node.right);
	        return temp;
	    }
	    
	    private Node findNode(Node findNode, Node node) {
	    	count++;
	        if (root == nil) {
	            return null;
	        }

	        if (findNode.key < node.key) {
	            if (node.left != nil) {
	                return findNode(findNode, node.left);
	            }
	        } else if (findNode.key > node.key) {
	            if (node.right != nil) {
	                return findNode(findNode, node.right);
	            }
	        } else if (findNode.key == node.key) {
	            return node;
	        }
	        return null;
	    }
	    
	    
	    
	    private Node tree_predecessor(Node node1){
	    	Node temp = nil;
	    	Node temp2=findNode(node1,root);
	    	
	    	if(temp2!=null)
	    	{
	    		if(temp2.left != nil)
	    		{
	    			return treeMaximum(temp2.left);
	    		}
	    		temp = temp2.parent;
	    		while(temp!= nil && temp2==temp.left)
	    		{	count++;
	    			temp2 = temp;
	    			temp=temp.parent;
	    		}
	    	}
	    	return temp;
	    }
	    
	    private Node tree_successor(Node node1){
	    	Node temp = nil;
	    	
	    	if(node1!= null)
	    	{
	    		if(node1.right != nil)
	    		{
	    			return treeMinimum(node1.right);
	    		}
	    		temp = node1.parent;
	    		while(temp!= nil && node1==temp.right)
	    		{
	    			count++;
	    			node1 = temp;
	    			temp=temp.parent;
	    		}
	    	}
	    	return temp;
	    }
	    
	    private Node find_predecessor(Node node1) {
	        Node temp = root;
	        if (root == nil) {
	            return nil;
	        } else {
	            node1.color = RED;
	            while (true) {
	            	count++;
	                if (node1.key < temp.key) {
	                    if (temp.left == nil) {
	                        temp.left = node1;
	                        node1.parent = temp;
	                        break;
	                    } else {
	                        temp = temp.left;
	                    }
	                } else if (node1.key >= temp.key) {
	                    if (temp.right == nil) {
	                        temp.right = node1;
	                        node1.parent = temp;
	                        break;
	                    } else {
	                        temp = temp.right;
	                    }
	                }
	            }
	            return tree_predecessor(node1);
	        }
	    }
	    
	    private void insert(Node node) {
	        Node temp = root;
	        if (root == nil) {
	            root = node;
	            node.color = BLACK;
	            node.parent = nil;
	        } else {
	            node.color = RED;
	            while (true) {
	            	count++;
	                if (node.key < temp.key) {
	                    if (temp.left == nil) {
	                        temp.left = node;
	                        node.parent = temp;
	                        break;
	                    } else {
	                        temp = temp.left;
	                    }
	                } else if (node.key >= temp.key) {
	                    if (temp.right == nil) {
	                        temp.right = node;
	                        node.parent = temp;
	                        break;
	                    } else {
	                        temp = temp.right;
	                    }
	                }
	            }
	            fixTree(node);
	        }
	    }

	    //Takes as argument the newly inserted node
	    private void fixTree(Node node) {
	        while (node.parent.color == RED) {
	        	count++;
	            Node uncle = nil;
	            if (node.parent == node.parent.parent.left) {
	                uncle = node.parent.parent.right;

	                if (uncle != nil && uncle.color == RED) {
	                    node.parent.color = BLACK;
	                    uncle.color = BLACK;
	                    node.parent.parent.color = RED;
	                    node = node.parent.parent;
	                    continue;
	                } 
	                if (node == node.parent.right) {
	                    //Double rotation needed
	                    node = node.parent;
	                    rotateLeft(node);
	                } 
	                node.parent.color = BLACK;
	                node.parent.parent.color = RED;
	                //if the "else if" code hasn't executed, this
	                //is a case where we only need a single rotation 
	                rotateRight(node.parent.parent);
	            } else {
	                uncle = node.parent.parent.left;
	                 if (uncle != nil && uncle.color == RED) {
	                    node.parent.color = BLACK;
	                    uncle.color = BLACK;
	                    node.parent.parent.color = RED;
	                    node = node.parent.parent;
	                    continue;
	                }
	                if (node == node.parent.left) {
	                    //Double rotation needed
	                    node = node.parent;
	                    rotateRight(node);
	                }
	                node.parent.color = BLACK;
	                node.parent.parent.color = RED;
	                //if the "else if" code hasn't executed, this
	                //is a case where we only need a single rotation
	                rotateLeft(node.parent.parent);
	            }
	        }
	        root.color = BLACK;
	    }

	    void rotateLeft(Node node) {
	        if (node.parent != nil) {
	            if (node == node.parent.left) {
	                node.parent.left = node.right;
	            } else {
	                node.parent.right = node.right;
	            }
	            node.right.parent = node.parent;
	            node.parent = node.right;
	            if (node.right.left != nil) {
	                node.right.left.parent = node;
	            }
	            node.right = node.right.left;
	            node.parent.left = node;
	        } else {//Need to rotate root
	            Node right = root.right;
	            root.right = right.left;
	            right.left.parent = root;
	            root.parent = right;
	            right.left = root;
	            right.parent = nil;
	            root = right;
	        }
	    }

	    void rotateRight(Node node) {
	        if (node.parent != nil) {
	            if (node == node.parent.left) {
	                node.parent.left = node.left;
	            } else {
	                node.parent.right = node.left;
	            }

	            node.left.parent = node.parent;
	            node.parent = node.left;
	            if (node.left.right != nil) {
	                node.left.right.parent = node;
	            }
	            node.left = node.left.right;
	            node.parent.right = node;
	        } else {//Need to rotate root
	            Node left = root.left;
	            root.left = root.left.right;
	            left.right.parent = root;
	            root.parent = left;
	            left.right = root;
	            left.parent = nil;
	            root = left;
	        }
	    }

	   //Deletes whole tree
	    void deleteTree(){
	        root = nil;
	    }
	    
	    //Deletion Code .
	    
	    //This operation doesn't care about the new Node's connections
	    //with previous node's left and right. The caller has to take care
	    //of that.
	    void transplant(Node target, Node with){ 
	          if(target.parent == nil){
	              root = with;
	          }else if(target == target.parent.left){
	              target.parent.left = with;
	          }else
	              target.parent.right = with;
	          with.parent = target.parent;
	    }
	    
	    boolean delete(Node z){
	        if((z = findNode(z, root))==null)return false;
	        Node x;
	        Node y = z; // temporary reference y
	        int y_original_color = y.color;
	        
	        if(z.left == nil){
	            x = z.right;  
	            transplant(z, z.right);  
	        }else if(z.right == nil){
	            x = z.left;
	            transplant(z, z.left); 
	        }else{
	            y = treeMinimum(z.right);
	            y_original_color = y.color;
	            x = y.right;
	            if(y.parent == z)
	                x.parent = y;
	            else{
	                transplant(y, y.right);
	                y.right = z.right;
	                y.right.parent = y;
	            }
	            transplant(z, y);
	            y.left = z.left;
	            y.left.parent = y;
	            y.color = z.color; 
	        }
	        if(y_original_color==BLACK)
	            deleteFixup(x);  
	        return true;
	    }
	    
	    void deleteFixup(Node x){
	        while(x!=root && x.color == BLACK){ 
	        	count++;
	            if(x == x.parent.left){
	                Node w = x.parent.right;
	                if(w.color == RED){
	                    w.color = BLACK;
	                    x.parent.color = RED;
	                    rotateLeft(x.parent);
	                    w = x.parent.right;
	                }
	                if(w.left.color == BLACK && w.right.color == BLACK){
	                    w.color = RED;
	                    x = x.parent;
	                    continue;
	                }
	                else if(w.right.color == BLACK){
	                    w.left.color = BLACK;
	                    w.color = RED;
	                    rotateRight(w);
	                    w = x.parent.right;
	                }
	                if(w.right.color == RED){
	                    w.color = x.parent.color;
	                    x.parent.color = BLACK;
	                    w.right.color = BLACK;
	                    rotateLeft(x.parent);
	                    x = root;
	                }
	            }else{
	                Node w = x.parent.left;
	                if(w.color == RED){
	                    w.color = BLACK;
	                    x.parent.color = RED;
	                    rotateRight(x.parent);
	                    w = x.parent.left;
	                }
	                if(w.right.color == BLACK && w.left.color == BLACK){
	                    w.color = RED;
	                    x = x.parent;
	                    continue;
	                }
	                else if(w.left.color == BLACK){
	                    w.right.color = BLACK;
	                    w.color = RED;
	                    rotateLeft(w);
	                    w = x.parent.left;
	                }
	                if(w.left.color == RED){
	                    w.color = x.parent.color;
	                    x.parent.color = BLACK;
	                    w.left.color = BLACK;
	                    rotateRight(x.parent);
	                    x = root;
	                }
	            }
	        }
	        x.color = BLACK; 
	    }
	    
	    Node treeMinimum(Node subTreeRoot)
	    {
	    	if(subTreeRoot != null)
	    	{
	    		while(subTreeRoot.left!=nil)
	    		{
	    			count++;
	    			subTreeRoot = subTreeRoot.left;
	    		}
	    	}
	        return subTreeRoot;
	    }
	    
	    Node treeMaximum(Node subTreeRoot)
	    {
	    	if(subTreeRoot != null)
	    	{
	    		while(subTreeRoot.right!=nil)
	    		{
	    			count++;
	    			subTreeRoot = subTreeRoot.right;
	    		}
	    	}
	        return subTreeRoot;
	    }
	    
	  /*  public int generateRandom(int min, int max)
	    {
	    	Random rand = new Random();
	    	int range=max-min+1;
	        int randomNum = rand.nextInt(range)+1;
	        return randomNum;
	    }
	    */
	   /* public int[] fillArray(int n)
	    {
	    	int a[]=new int[n];
	    	for(int i=0;i<n;i++)
	    	{
	    		count++;
	    		a[i]=generateRandom(1,n);
	    	}
	    	return a;
	    }
	    */
	    public int laisCompute(RedBlackTree rbtree, int n, boolean flag)
	    {
	    	
	    	//int range=max-min+1;
	        
	        int a[]=new int[n];
	        int p[] = new int[n];
	        int b[]=new int[n];
	        int counter = 0;
	        Node node_1, node_2;
	        Node pred_1, pred_2;
	        Node succ1;
	        
	        int i;
	    	int m,t;
	        
	        Random rand = new Random();
	    	for(i=0;i<n;i++)
	    	{
	    		count++;
	    		a[i]=rand.nextInt(n)+1;
	    	}
	    	
	    	if(flag==true){
	        System.out.println("Input");
	        for( i=0;i<n;i++)
	        {
	        	count++;
	        	System.out.print(a[i]+" ");
	        }
	    	}
	    	
	        for(i=0;i<n;i++)
	        {
	        	count++;
	        	node_1 = rbtree.new Node(a[i]);
	        	node_1.index=i;
	            rbtree.insert(node_1);
	            pred_1 = rbtree.tree_predecessor(node_1);
	        	if(pred_1!=rbtree.nil)
	            {
	            	p[i]=pred_1.index;
	            }
	            else
	            {
	            	p[i]=i;
	            }
	            node_2 = rbtree.new Node(a[i]+2);
	            pred_2 = rbtree.find_predecessor(node_2);
	            rbtree.delete(node_2);
	            succ1 = rbtree.tree_successor(pred_2);
	            if(succ1!=rbtree.nil)
	            {
	            	rbtree.delete(succ1);
	            }
	        }

	        m=rbtree.find_Tail_node(rbtree.root).index;
	        
	        for(i=n-1;i>=m+1;i--)
	        {
	        	count++;
	        	if((a[m]-2) < a[i] && a[i]<=a[m])
	        	{
	        		//System.out.println(a[i]);
	        			b[counter++]=a[i];
	        	}
	        }
	        //System.out.println(a[m]);
	        b[counter++]=a[m];
	        
	        t=m;
	        while(p[t]!=t)
	        {
	        	count++;
	        	for (i = t-1; i >= p[t]+1; i--) 
	        	{
	        		count++;
	    			if(a[p[t]]-2<a[i] && a[i]<=a[p[i]])
	    			{
	    				//System.out.println(a[i]);
	    				b[counter++]=a[i];
	    			}
	    		}
	        	//System.out.println(a[p[t]]);
	        	b[counter++]=a[p[t]];
	        	t=p[t];
	     
	        }
	        if(flag==true){
	        System.out.println("\n");
	        System.out.println("Output");
	        
	        for(int idx=counter-1;idx>=0;idx--){
	    	count++;
	    	System.out.print(b[idx]+" ");
	        }
	    }
      
	    System.out.println("\n");
	    
	    if(flag==true)
	    {
	    System.out.println("Table 2: p values");

	    for(int idx=0;idx<n;idx++)
	    {
	    	count++;
	    	System.out.print(p[idx]+" ");
	    }
	    }
	    
	    System.out.println("\n");
	    
		System.out.println("Count:"+count);
		
	    
	    return counter;
	    }
	    

	    
		public static void main(String[] args) throws FileNotFoundException 
		{
			int average=0,constant=100;
			int x=0;
			
	        RedBlackTree rbtree = new RedBlackTree();
	        RedBlackTree rbtree1 = new RedBlackTree();
	        //RedBlackTree[] rbtree2= new RedBlackTree[1000];
	        System.out.println("First sequence for n=20");
	        rbtree.laisCompute(rbtree, 20, true);
	        System.out.println("Second sequence for n=20");
	        rbtree.laisCompute(rbtree1, 20, true);
	        
	      /*  File file=new File("info.txt");
	        PrintWriter pw=new PrintWriter(file);
	        
	        	for(int i=0;i<1000;i++)
	        	{
	        		rbt[i]=new RedBlackTree();
	        		x=rbt[i].laisCompute(rbt[i], 1000, false);
	        		average=rbt[i].count/constant;
	        		pw.println(""+average+"-"+x+"\n");
	        		System.out.println("Average"+average);
	        	}
	        	pw.close();
	        */	
	        }
		}

	       /* System.out.println("Third Sequence for n=1000");
	        
	        for(int i=0;i<1000;i++)
	        {
	        	rbtree2[i].laisCompute(rbtree2[i], 1000, false);
	        	
	        }
	        */
	        
	        
	        
	        
	        
	    /*    int n=999;
	        //int n2=1000;
	        
	        //int a[]={7,15,2,14,14,6,8,11,17,15,14,13};
	        
	        int a[]=rbtree.fillArray(n);
	        System.out.println("Input");
	        for(int i=0;i<n;i++)
	        {
	        	System.out.print(a[i]+" ");
	        }
	        int p[] = new int[n];
	        int i;
			int m,t;
	        

	        Node node_1, node_2;
	        Node pred_1, pred_2;
	        Node succ1;

	        for(i=0;i<n;i++)
	        {
	        	node_1 = rbtree.new Node(a[i]);
	        	node_1.index=i;
	            rbtree.insert(node_1);
	            pred_1 = rbtree.tree_predecessor(node_1);
	        	if(pred_1!=rbtree.nil)
	            {
	            	p[i]=pred_1.index;
	            }
	            else
	            {
	            	p[i]=i;
	            }
	            node_2 = rbtree.new Node(a[i]+2);
	            pred_2 = rbtree.find_predecessor(node_2);
	            rbtree.delete(node_2);
	            succ1 = rbtree.tree_successor(pred_2);
	            if(succ1!=rbtree.nil)
	            {
	            	rbtree.delete(succ1);
	            }
	        }

	        m=rbtree.find_Tail_node(rbtree.root).index;
	        
	        int b[]=new int[20];
	        int counter = 0;
	        
	        
	        for(i=n-1;i>=m+1;i--)
	        {
	        	count++;
	        	if((a[m]-2) < a[i] && a[i]<=a[m])
	        	{
	        		System.out.println(a[i]);
	        			//b[counter++]=a[i];
	        	}
	        }
	        System.out.println(a[m]);
	        //b[counter++]=a[m];
	        
	        t=m;
	        while(p[t]!=t)
	        {
	        	count++;
	        	for (i = t-1; i >= p[t]+1; i--) 
	        	{
					if(a[p[t]]-2<a[i] && a[i]<=a[p[t]])
					{
						System.out.println(a[i]);
						//b[counter++]=a[i];
					}
				}
	        	System.out.println(a[p[t]]);
	        	//b[counter++]=a[p[t]];
	        	t=p[t];
	     
	        }
	        
	        System.out.println("\n");
	        System.out.println("Output");
		
	        for(int idx=counter-1;idx>=0;idx--){
			count++;
			System.out.print(b[idx]+" ");
		}
		
		System.out.println("\n");
		System.out.println("Table 2: p values");
		
		for(int idx=0;idx<n;idx++)
		{
			count++;
			System.out.print(p[idx]+" ");
		}
		System.out.println("\n");
		System.out.println("Count:"+count);
		}
*/

	
	

