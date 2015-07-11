public class LinkedQueue<T> implements QueueADT<T>
{
    private int count;
    private LinearNode<T> front, rear;

    public LinkedQueue()
    {
        count = 0;
        front = rear = null;
    }

    public void enqueue (T element)
    {
        LinearNode<T> node = new LinearNode<T>(element);

        if (isEmpty())
            front = node;
        else
            rear.setNext (node);

        rear = node;
        count++;
    }

    public T dequeue() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException ("queue");

        T result = front.getElement();
        front = front.getNext();
        count--;

        if (isEmpty())
            rear = null;

        return result;
    }

    public T first() throws EmptyCollectionException
    {
        if (isEmpty())
            throw new EmptyCollectionException ("queue");

        return front.getElement();
    }

    public boolean isEmpty()
    {
        return (count == 0);
    }

    public int size()
    {
        return count;
    }

    public String toString()
    {
        String result = "";
        LinearNode<T> current = front;

        while (current != null)
        {
            result = result + (current.getElement()).toString() + "\n";
            current = current.getNext();
        }

        return result;
    }
}
