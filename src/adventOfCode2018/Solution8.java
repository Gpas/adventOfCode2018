package adventOfCode2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


public class Solution8 {

	public static void main(String[] args) {
		String fileName = "input/input8.txt";
		Scanner read;
		int metaDataSum = 0;
		Node rootNode = null;
		try {
			read = new Scanner (new File(fileName));
			read.useDelimiter(" ");
			int ids = 0;
			rootNode = new Node(ids++);
			rootNode.setChildNodes(read.nextInt());
			rootNode.setMetaDataCount(read.nextInt());
			Node currentParent = rootNode;
			Node node = rootNode;
			while(read.hasNext()) {
				if(node == null) {
					node = new Node(ids++);
					node.setChildNodes(read.nextInt());
					if(currentParent != null) {
						currentParent.addChild(node);
					} else {
						rootNode.addChild(node);
					}
					node.setMetaDataCount(read.nextInt());
				}
				if(node.getChildNodes() > 0) {
					currentParent = node;
					node = null; //Go One Down
					continue;
				} 
				if(node.getChildNodes() == 0) {
					if(node.getMetaDataCount() != 0) {
						node.getMetadata().add(read.nextInt());
						node.subtractMetaDataCount();
					} else {
						//Go one up
						metaDataSum += node.getMetadataSum();
						node = node.getParent();
						if(node != null) {
							currentParent = node.getParent();
						}
					}
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		metaDataSum += rootNode.getMetadataSum();
		System.out.println("Part 1: "+metaDataSum);
		
		System.out.println("Part 2: "+getRootNodeValue(rootNode));
	}
	
	private static Integer getRootNodeValue(Node rootNode) {
		int sum = 0;
		if(rootNode.getChildren().isEmpty()) {
			return rootNode.getMetadataSum();
		}
		for(Integer index : rootNode.getMetadata()) {
			try {
				sum += getRootNodeValue(rootNode.getChildren().get(index-1));
			} catch (IndexOutOfBoundsException e) {
				//Do nothing
			}
		}
		return sum;
	}

	private static class Node {
		int id;
		int childNodes;
		int metaDataCount;
		List<Integer> metadata = new ArrayList<>();
		
		List<Node> children = new ArrayList<>();
		Node parent = null;
		
		public void subtractMetaDataCount() {
			this.metaDataCount--;
		}
		
		public void subtractChildCount() {
			this.childNodes--;
		}
		
		public Node addChild(Node child) {
			 child.setParent(this);
			 this.children.add(child);
			 this.childNodes--;
			 return child;
		}
		
		
		
		public List<Node> getChildren() {
			return children;
		}



		public void setChildren(List<Node> children) {
			this.children = children;
		}



		public Node getParent() {
			return parent;
		}



		public void setParent(Node parent) {
			this.parent = parent;
		}



		public int getMetadataSum() {
			return metadata.stream().mapToInt(Integer::intValue).sum();
		}
		
		public Node(int id) {
			super();
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public List<Integer> getMetadata() {
			return metadata;
		}

		public void setMetadata(List<Integer> metadata) {
			this.metadata = metadata;
		}

		public int getChildNodes() {
			return childNodes;
		}

		public void setChildNodes(int childNodes) {
			this.childNodes = childNodes;
		}

		public int getMetaDataCount() {
			return metaDataCount;
		}

		public void setMetaDataCount(int metaDataCount) {
			this.metaDataCount = metaDataCount;
		}
		
		
		
		
	}
}


