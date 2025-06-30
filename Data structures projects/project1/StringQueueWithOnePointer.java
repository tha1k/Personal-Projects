import java.io.PrintStream;
import java.util.NoSuchElementException;
class StringQueueWithOnePointer implements StringQueue{
    Node tail;
    int size;
public StringQueueWithOnePointer(){
    this.size=0;
    this.tail=null;
}
public boolean isEmpty(){
    return (this.size==0);
}
public void put(String item){
    Node n = new Node(item);
    if(isEmpty()){
       this.tail=n; }
    else{   
       
    if (this.tail.getNext()==null){
        this.tail.setNext(n);//one element
        n.setNext(tail);
        this.tail=n;}
    else{
        //the new node is pointing at the first node thus creating a circular list
        n.setNext(this.tail.getNext());
        this.tail.setNext(n);//this way the tail is pointing at the last insertion so the next will be the item to dequeue
        this.tail=n;
        }} this.size++;
}
public String get() throws NoSuchElementException{
    if(isEmpty()){
        throw new NoSuchElementException();

    }
    String element = (this.tail.getNext()).getData();
    size--;
    if(this.tail.getNext()==null){
    this.tail=null;
    return element;}
    //tail.next is the first inserted item so we will return that value
    else{this.tail.setNext(this.tail.next.next);return element;}
  
    
}
public String peek() throws NoSuchElementException{
    if(isEmpty()){
        throw new NoSuchElementException();
    }
    if(this.tail.getNext()==null){
        return this.tail.getData();
        
    }
    return (this.tail.getNext()).getData();
}//tail is pointing at the last inserted data the next node contains the first insertion which means tail.next if there is more than one insertion
public int size() {
    return this.size;
}
public void printQueue(PrintStream stream){
    StringBuilder ret = new StringBuilder();

        if(isEmpty()){
            stream.print("empty Queue");
            stream.flush();
        }
        else{
            
            
            if(tail.getNext()==null){
                ret.append(tail.getData());
            }
             Node current = this.tail.getNext();
             Node iterator = this.tail.getNext();
            do{
                ret.append(iterator.getData());
                if (iterator.getNext() != current)
                ret.append("-> ");
                iterator=iterator.getNext();
            }while(iterator!=current);
            }
            ret.append(" <- Tail");
            stream.println(ret);
            stream.flush();
        }
}
   /*  if(isEmpty()){stream.print("list is empty");stream.flush();}
    else{StringBuffer ret = new StringBuffer();
    if(this.tail.getNext()==null){//one element
    ret.append(this.tail.getData()); }else{
    Node iterator= tail.getNext();
    ret.append(iterator.getData());
    while(iterator != this.tail){
        ret.append((iterator.next).getData());

        if (iterator.getNext() != this.tail)
            ret.append(" -> ");

        iterator.setNext(iterator.getNext());
    }}

    ret.append(" <- last");

    stream.println(ret.toString());
    stream.flush();
}
}*/


