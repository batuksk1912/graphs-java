public interface WeightedGraphADT<T> extends GraphADT<T>
{
    public void addEdge (T vertex1, T vertex2, double weight);

    public double shortestPathWeight(T vertex1, T vertex2);
}