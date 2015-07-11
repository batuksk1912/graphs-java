public class EmptyStackException extends RuntimeException
{
    public EmptyStackException()
    {
        super ("Unbalanced input. Please enter correctly.");
    }
}