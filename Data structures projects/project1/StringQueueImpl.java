import java.io.PrintStream;
import java.util.NoSuchElementException;


//import exception build empty queue exception class
class StringQueueImpl implements StringQueue{
    /*ERGASIA MEROS A: implementation of a queue usimg lists with string values */

    private int size;
    private Node head;
    private Node tail;

public StringQueueImpl(){
    this.head=null;
    this.tail=null;
    this.size=0;
}
public boolean isEmpty(){
    return (this.size==0);//returns true of false if queue is empty
}
//FIFO principle so our code is dequeuing elements from the head Node. 
//Thus achieving O(1) time complexity enqueuing and dequeuing respectively
public String get() throws NoSuchElementException{
    
    if (isEmpty())
            throw new NoSuchElementException();

        String data = head.getData();
        size--;
        if (head == tail)
            head = tail = null;
        else
            head = head.getNext();

        return data;

} 
//FIFO. we insert insert the value at back 
public void put(String item){
    Node n = new Node(item);
    
        if (isEmpty()) {
            this.head = n;
            this.tail = n;
        } else {
            this.tail.setNext(n);
            this.tail = n;
        }size++;

}
//peek() methods returns the first/oldest item inserted 
public String peek() throws NoSuchElementException{
    if(isEmpty()){
        throw new NoSuchElementException();
    }return head.getData();
}
public int size(){
    return this.size;
}

public void printQueue(PrintStream stream){
    if(size==0){stream.print("list is empty");stream.flush();}
    Node iterator= head;
    StringBuffer ret = new StringBuffer();
    while (iterator != null) {
        ret.append(iterator.data);

        if (iterator.getNext() != null)
            ret.append(" -> ");

        iterator = iterator.next;
    }

    ret.append(" <- TAIL");

    stream.print(ret.toString());
    stream.flush();
}







}