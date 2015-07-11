public class FullStackException extends RuntimeException
{
    public FullStackException()
    {
        super ("Unbalanced input. Please enter correctly.");
    }
}