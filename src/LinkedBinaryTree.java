import java.util.Iterator;

public class LinkedBinaryTree<T> implements BinaryTreeADT<T>
{
    protected int count;
    protected BinaryTreeNode<T> root;

    public LinkedBinaryTree()
    {
        count = 0;
        root = null;
    }

    public LinkedBinaryTree (T element)
    {
        count = 1;
        root = new BinaryTreeNode<T> (element);
    }

    public LinkedBinaryTree (T element, LinkedBinaryTree<T> leftSubtree, LinkedBinaryTree<T> rightSubtree)
    {
        root = new BinaryTreeNode<T> (element);
        count = 1;

        if (leftSubtree != null)
        {
            count = count + leftSubtree.size();
            root.left = leftSubtree.root;
        }
        else
            root.left = null;

        if (rightSubtree !=null)
        {
            count = count + rightSubtree.size();
            root.right = rightSubtree.root;
        }
        else
            root.right = null;
    }

    public void removeLeftSubtree()
    {
        if (root.left != null)
            count = count - root.left.numChildren() - 1;
        root.left = null;
    }

    public void removeRightSubtree()
    {
        if (root.right != null)
            count = count - root.right.numChildren() - 1;

        root.right = null;
    }

    public void removeAllElements()
    {
        count = 0;
        root = null;
    }

    public boolean isEmpty()
    {
        return (count == 0);
    }

    public int size()
    {
        return count;
    }

    public boolean contains (T targetElement)
    {
        T temp;
        boolean found = false;

        try
        {
            temp = find (targetElement);
            found = true;
        }
        catch (Exception ElementNotFoundException)
        {
            found = false;
        }

        return found;
    }

    public T find(T targetElement) throws ElementNotFoundException
    {
        BinaryTreeNode<T> current = findAgain( targetElement, root );

        if( current == null )
            throw new ElementNotFoundException("binary tree");

        return (current.element);
    }

    private BinaryTreeNode<T> findAgain(T targetElement,
                                        BinaryTreeNode<T> next)
    {
        if (next == null)
            return null;

        if (next.element.equals(targetElement))
            return next;

        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);

        if (temp == null)
            temp = findAgain(targetElement, next.right);

        return temp;
    }

    public String toString()
    {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preorder (root, tempList);

        return tempList.toString();
    }

    public Iterator<T> iteratorInOrder()
    {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inorder (root, tempList);

        return tempList.iterator();
    }

    protected void inorder (BinaryTreeNode<T> node,
                            ArrayUnorderedList<T> tempList)
    {
        if (node != null)
        {
            inorder (node.left, tempList);
            tempList.addToRear(node.element);
            inorder (node.right, tempList);
        }
    }

    public Iterator<T> iteratorPreOrder()
    {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preorder (root, tempList);

        return tempList.iterator();
    }

    protected void preorder (BinaryTreeNode<T> node,
                             ArrayUnorderedList<T> tempList)
    {
        if (node != null)
        {
            tempList.addToRear(node.element);
            preorder (node.left, tempList);
            preorder (node.right, tempList);
        }
    }

    public Iterator<T> iteratorPostOrder()
    {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postorder (root, tempList);

        return tempList.iterator();
    }

    protected void postorder (BinaryTreeNode<T> node,
                              ArrayUnorderedList<T> tempList)
    {
        if (node != null)
        {
            postorder (node.left, tempList);
            postorder (node.right, tempList);
            tempList.addToRear(node.element);
        }
    }

    public Iterator<T> iteratorLevelOrder()
    {
        ArrayUnorderedList<BinaryTreeNode<T>> nodes =
                new ArrayUnorderedList<BinaryTreeNode<T>>();
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        BinaryTreeNode<T> current;

        nodes.addToRear (root);

        while (! nodes.isEmpty())
        {
            current = (BinaryTreeNode<T>)(nodes.removeFirst());

            if (current != null)
            {
                tempList.addToRear(current.element);
                if (current.left!=null)
                    nodes.addToRear (current.left);
                if (current.right!=null)
                    nodes.addToRear (current.right);
            }
            else
                tempList.addToRear(null);
        }

        return tempList.iterator();
    }
}