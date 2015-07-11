import java.util.Iterator;

public class ArrayIterator<T> implements Iterator
{
    private int count;
    private int current;
    private T[] items;

    public ArrayIterator (T[] collection, int size)
    {
        items = collection;
        count = size;
        current = 0;
    }

    public boolean hasNext()
    {
        return (current < count);
    }

    public T next()
    {
        if (! hasNext())
            throw new NoSuchElementException();

        current++;

        return items[current - 1];
    }

    public void remove() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
}