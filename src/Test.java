import java.util.Scanner;
import java.util.Iterator;


public class Test {

    private static boolean state = true;
    private static int c = 0;
    private static Scanner scanTools = new Scanner(System.in);

    public static void main(String [] args) {
        WeightedGraph myGraph = new WeightedGraph();
        LinkedStack myStack = new LinkedStack();
        while(state) {
            boolean nodeCheck = true;
            boolean vertexCheck = true;
            showMenu();
            System.out.println("Please enter your choice : ");
            c = scanTools.nextInt();
            switch (c) {
                case 1:
                    System.out.println("Please enter the input for creating the graph Vertex1(Vertex2,Edge Weight) : ");
                    String input = scanTools.next().replaceAll(" ","").toUpperCase();
                    String vertexSecond = "";
                    String edgeWeight = "";
                    String vertexFirst = String.valueOf(input.charAt(0));
                    for (int i = 0; i<input.length(); i++) {
                        if (input.charAt(i) == '(') {
                            myStack.push('(');
                            if (i + 1 < input.length()) {
                                vertexSecond = String.valueOf(input.charAt(i + 1));
                            }
                        } if (isOperand(input.charAt(i)) && i + 1 < input.length() && isOperand(input.charAt(i+1))) {
                            edgeWeight += input.charAt(i);
                        } else if (isOperand(input.charAt(i))) {
                            edgeWeight += input.charAt(i);
                        } else if (input.charAt(i) == ')') {
                            myStack.pop();
                        }
                    }
                    if (myStack.size() > 0) {
                        throw new FullStackException();
                    }
                    if (vertexFirst.equals(vertexSecond)) {
                        System.out.println("Self loops are not allowed.");
                        break;
                    }
                    if (myGraph.getIndex(vertexFirst) == -1) {
                        nodeCheck = false;
                    }
                    if (myGraph.getIndex(vertexSecond) == -1) {
                        vertexCheck = false;
                    }
                    if (!nodeCheck) {
                        myGraph.addVertex(vertexFirst);
                    }
                    if (!vertexCheck) {
                        myGraph.addVertex(vertexSecond);
                    }
                        myGraph.addEdge(vertexFirst,vertexSecond,Double.parseDouble(edgeWeight));
                        System.out.println("Graph created successfully.");
                    break;
                case 2:
                    System.out.println("1.BFS");
                    System.out.println("2.DFS");
                    int choiceforPrint = scanTools.nextInt();
                    switch (choiceforPrint) {
                        case 1:
                            System.out.println("Please enter the start node :");
                            String bfs = scanTools.next().toUpperCase();
                            System.out.println("BFS Traversal :");
                            Iterator itrBFS = myGraph.iteratorBFS(bfs);
                            while(itrBFS.hasNext()) {
                                Object element = itrBFS.next();
                                System.out.print(element + " ");
                            }
                            break;
                        case 2:
                            System.out.println("Please enter the start node :");
                            String dfs = scanTools.next().toUpperCase();
                            System.out.println("DFS Traversal :");
                            Iterator itrDFS = myGraph.iteratorDFS(dfs);
                            while(itrDFS.hasNext()) {
                                Object element = itrDFS.next();
                                System.out.print(element + " ");
                            }
                            break;
                        default:
                            System.out.println("You must enter 1 or 2.");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Please enter start node :");
                    String startNode = scanTools.next().toUpperCase();
                    System.out.println("Please enter last node :");
                    String lastNode = scanTools.next().toUpperCase();
                    System.out.println("Shortest Path :");
                    Iterator itrSP = myGraph.iteratorShortestPath(startNode,lastNode);
                    while(itrSP.hasNext()) {
                        Object element = itrSP.next();
                        System.out.print(element + " ");
                    }
                    System.out.println();
                    System.out.println("Shortest path weight is : " + myGraph.shortestPathWeight(startNode,lastNode));
                    break;
                case 4:
                    System.out.println(myGraph.getWeightedMinimumSpanningTree().toString());
                    break;
                case 5:
                    System.out.println("Please enter the search key :");
                    String searchKey = scanTools.next().toUpperCase();
                    if (myGraph.getIndex(searchKey) == -1) {
                        System.out.println("No results found.");
                    } else {
                        System.out.println(searchKey + " found. Index of this vertex is : " + myGraph.getIndex(searchKey));
                    }
                    break;
                case 6:
                    System.out.println("Do you want to exit ? (Y/N)");
                    String exit = scanTools.next();
                    if(exit.equalsIgnoreCase("Y")) {
                        state = false;
                    }
                    break;
                default:
                    System.out.println("Please enter number between 1 and 6.");
                    break;
            }
        }
    }

    private static void showMenu() {
        System.out.println();
        System.out.println();
        System.out.println("1.Creating a graph by the user input");
        System.out.println("2.Printing the graph on the screen");
        System.out.println("3.Shortest Path");
        System.out.println("4.Minimum Spanning Tree");
        System.out.println("5.Search");
        System.out.println("6.Exit");
    }

    private static boolean isOperand(char c) {
        if(c >= '0' && c <= '9') return true;
        return false;
    }
}
