import java.util.Iterator;

public class Graph<T> implements GraphADT<T>
{
    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices;
    protected boolean[][] adjMatrix;
    protected T[] vertices;

    public Graph()
    {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[])(new Object[DEFAULT_CAPACITY]);
    }

    public String toString()
    {
        if (numVertices == 0)
            return "Graph is empty";

        String result = new String("");

        result += "Adjacency Matrix\n";
        result += "----------------\n";
        result += "Index\t";

        for (int i = 0; i < numVertices; i++)
        {
            result += "" + i;
            if (i < 10)
                result += " ";
        }
        result += "\n----------------";
        result += "\n\n";

        for (int i = 0; i < numVertices; i++)
        {
            result += "  " + i + "\t|";

            for (int j = 0; j < numVertices; j++)
            {
                if (adjMatrix[i][j])
                    result += "  1 ";
                else
                    result += "  0 ";
            }
            result += "\n";
        }

        result += "\n\nVertex Values";
        result += "\n-------------\n";
        result += "Index\tValue\n\n";

        for (int i = 0; i < numVertices; i++)
        {
            result += "  " + i + "\t\t";
            result += "  " + vertices[i].toString() + "\n";
        }
        result += "\n";
        return result;
    }

    public void addEdge (int index1, int index2)
    {
        if (indexIsValid(index1) && indexIsValid(index2))
        {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    public void removeEdge (int index1, int index2)
    {
        if (indexIsValid(index1) && indexIsValid(index2))
        {
            adjMatrix[index1][index2] = false;
            adjMatrix[index2][index1] = false;
        }
    }

    public void addEdge (T vertex1, T vertex2)
    {
        addEdge (getIndex(vertex1), getIndex(vertex2));
    }

    public void removeEdge (T vertex1, T vertex2)
    {
        removeEdge (getIndex(vertex1), getIndex(vertex2));
    }

    public void addVertex (T vertex)
    {
        if (numVertices == vertices.length)
            expandCapacity();

        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++)
        {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;
    }

    public void removeVertex (int index)
    {
        if (indexIsValid(index))
        {
            numVertices--;

            for (int i = index; i < numVertices; i++)
                vertices[i] = vertices[i+1];

            for (int i = index; i < numVertices; i++)
                for (int j = 0; j <= numVertices; j++)
                    adjMatrix[i][j] = adjMatrix[i+1][j];

            for (int i = index; i < numVertices; i++)
                for (int j = 0; j < numVertices; j++)
                    adjMatrix[j][i] = adjMatrix[j][i+1];
        }
    }

    public void removeVertex (T vertex)
    {
        for (int i = 0; i < numVertices; i++)
        {
            if (vertex.equals(vertices[i]))
            {
                removeVertex(i);
                return;
            }
        }
    }

    public Iterator<T> iteratorDFS(int startIndex)
    {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        boolean[] visited = new boolean[numVertices];

        if (!indexIsValid(startIndex))
            return resultList.iterator();

        for (int i = 0; i < numVertices; i++)
            visited[i] = false;

        traversalStack.push(new Integer(startIndex));
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty())
        {
            x = traversalStack.peek();
            found = false;

            for (int i = 0; (i < numVertices) && !found; i++)
            {
                if (adjMatrix[x.intValue()][i] && !visited[i])
                {
                    traversalStack.push(new Integer(i));
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty())
                traversalStack.pop();
        }
        return resultList.iterator();
    }

    public Iterator<T> iteratorDFS(T startVertex)
    {
        return iteratorDFS(getIndex(startVertex));
    }

    public Iterator<T> iteratorBFS(int startIndex)
    {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        if (!indexIsValid(startIndex))
            return resultList.iterator();

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++)
            visited[i] = false;

        traversalQueue.enqueue(new Integer(startIndex));
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty())
        {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x.intValue()]);

            for (int i = 0; i < numVertices; i++)
            {
                if (adjMatrix[x.intValue()][i] && !visited[i])
                {
                    traversalQueue.enqueue(new Integer(i));
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    public Iterator<T> iteratorBFS(T startVertex)
    {
        return iteratorBFS(getIndex(startVertex));
    }

    protected Iterator<Integer> iteratorShortestPathIndices
    (int startIndex, int targetIndex)
    {
        int index = startIndex;
        int[] pathLength = new int[numVertices];
        int[] predecessor = new int[numVertices];
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<Integer> resultList =
                new ArrayUnorderedList<Integer>();

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex) ||
                (startIndex == targetIndex))
            return resultList.iterator();

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++)
            visited[i] = false;

        traversalQueue.enqueue(new Integer(startIndex));
        visited[startIndex] = true;
        pathLength[startIndex] = 0;
        predecessor[startIndex] = -1;

        while (!traversalQueue.isEmpty() && (index != targetIndex))
        {
            index = (traversalQueue.dequeue()).intValue();

            for (int i = 0; i < numVertices; i++)
            {
                if (adjMatrix[index][i] && !visited[i])
                {
                    pathLength[i] = pathLength[index] + 1;
                    predecessor[i] = index;
                    traversalQueue.enqueue(new Integer(i));
                    visited[i] = true;
                }
            }
        }
        if (index != targetIndex)
            return resultList.iterator();

        LinkedStack<Integer> stack = new LinkedStack<Integer>();
        index = targetIndex;
        stack.push(new Integer(index));
        do
        {
            index = predecessor[index];
            stack.push(new Integer(index));
        } while (index != startIndex);

        while (!stack.isEmpty())
            resultList.addToRear(((Integer)stack.pop()));

        return resultList.iterator();
    }

    public Iterator<T> iteratorShortestPath(int startIndex, int targetIndex)
    {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex))
            return resultList.iterator();

        Iterator<Integer> it = iteratorShortestPathIndices(startIndex,
                targetIndex);
        while (it.hasNext())
            resultList.addToRear(vertices[((Integer)it.next()).intValue()]);
        return resultList.iterator();
    }

    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex)
    {
        return iteratorShortestPath(getIndex(startVertex),
                getIndex(targetVertex));
    }

    public int shortestPathLength(int startIndex, int targetIndex)
    {
        int result = 0;
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex))
            return 0;

        int index1, index2;
        Iterator<Integer> it = iteratorShortestPathIndices(startIndex,
                targetIndex);

        if (it.hasNext())
            index1 = ((Integer)it.next()).intValue();
        else
            return 0;

        while (it.hasNext())
        {
            result++;
            it.next();
        }

        return result;
    }

    public int shortestPathLength(T startVertex, T targetVertex)
    {
        return shortestPathLength(getIndex(startVertex), getIndex(
                targetVertex));
    }

    public Graph getMinimumSpanningTree()
    {
        int x, y;
        int[] edge = new int[2];
        LinkedStack<int[]> vertexStack = new LinkedStack<int[]>();
        Graph<T> resultGraph = new Graph<T>();

        if (isEmpty() || !isConnected())
            return resultGraph;

        resultGraph.adjMatrix = new boolean[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++)
            for (int j = 0; j < numVertices; j++)
                resultGraph.adjMatrix[i][j] = false;

        resultGraph.vertices = (T[])(new Object[numVertices]);
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++)
            visited[i] = false;

        edge[0] = 0;
        resultGraph.vertices[0] = this.vertices[0];
        resultGraph.numVertices++;
        visited[0] = true;

        for (int i = 0; i < numVertices; i++)
        {
            if (!visited[i] && this.adjMatrix[0][i])
            {
                edge[1] = i;
                vertexStack.push(edge.clone());
                visited[i] = true;
            }
        }

        while ((resultGraph.size() < this.size()) && !vertexStack.isEmpty())
        {
            edge = vertexStack.pop();
            x = edge[0];
            y = edge[1];
            resultGraph.vertices[y] = this.vertices[y];
            resultGraph.numVertices++;
            resultGraph.adjMatrix[x][y] = true;
            resultGraph.adjMatrix[y][x] = true;
            visited[y] = true;

            for (int i = 0; i < numVertices; i++)
            {
                if (!visited[i] && this.adjMatrix[i][y])
                {
                    edge[0] = y;
                    edge[1] = i;
                    vertexStack.push(edge.clone());
                    visited[i] = true;
                }
            }
        }

        return resultGraph;
    }

    protected void expandCapacity()
    {
        T[] largerVertices = (T[])(new Object[vertices.length*2]);
        boolean[][] largerAdjMatrix =
                new boolean[vertices.length*2][vertices.length*2];

        for (int i = 0; i < numVertices; i++)
        {
            for (int j = 0; j < numVertices; j++)
            {
                largerAdjMatrix[i][j] = adjMatrix[i][j];
            }
            largerVertices[i] = vertices[i];
        }

        vertices = largerVertices;
        adjMatrix = largerAdjMatrix;
    }

    public int size()
    {
        return numVertices;
    }

    public boolean isEmpty()
    {
        return (numVertices == 0);
    }

    public boolean isConnected()
    {
        if (isEmpty())
            return false;

        Iterator<T> it = iteratorBFS(0);
        int count = 0;

        while (it.hasNext())
        {
            it.next();
            count++;
        }
        return (count == numVertices);
    }

    public int getIndex(T vertex)
    {
        for (int i = 0; i < numVertices; i++)
            if (vertices[i].equals(vertex))
                return i;
        return -1;
    }

    protected boolean indexIsValid(int index)
    {
        return ((index < numVertices) && (index >= 0));
    }

    public Object[] getVertices()
    {
        Object[] vertices = new Object[numVertices];
        Object vertex;

        for (int i = 0; i < numVertices; i++)
        {
            vertex = this.vertices[i];
            vertices[i] = vertex;
        }
        return vertices;
    }
}