public class LinkedStack<T> implements StackADT<T>
{
    private int count;
    private LinearNode<T> top;

    public LinkedStack()
    {
        count = 0;
        top = null;
    }

    public void push (T element)
    {
        LinearNode<T> temp = new LinearNode<T> (element);

        temp.setNext(top);
        top = temp;
        count++;
    }

    public T pop() throws EmptyStackException
    {
        if (isEmpty())
            throw new EmptyStackException();

        T result = top.getElement();
        top = top.getNext();
        count--;

        return result;
    }

    public T peek() throws EmptyStackException
    {
        if (isEmpty())
            throw new EmptyStackException();

        return top.getElement();
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
        LinearNode current = top;

        while (current != null)
        {
            result = result + (current.getElement()).toString() + "\n";
            current = current.getNext();
        }

        return result;
    }
}