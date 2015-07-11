import java.util.Iterator;

public interface GraphADT<T>
{
    public void addVertex (T vertex);

    public void removeVertex (T vertex);

    public void addEdge (T vertex1, T vertex2);

    public void removeEdge (T vertex1, T vertex2);

    public Iterator iteratorBFS(T startVertex);

    public Iterator iteratorDFS(T startVertex);

    public Iterator iteratorShortestPath(T startVertex, T targetVertex);

    public boolean isEmpty();

    public boolean isConnected();

    public int size();

    public String toString();
}
